/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.user.service;

import com.lgcns.smartwcs.center.model.Center;
import com.lgcns.smartwcs.center.model.ids.CenterId;
import com.lgcns.smartwcs.center.service.CenterService;
import com.lgcns.smartwcs.common.client.MailClient;
import com.lgcns.smartwcs.common.exception.DataDuplicateException;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import com.lgcns.smartwcs.common.utils.AES256;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import com.lgcns.smartwcs.common.utils.SaltUtil;
import com.lgcns.smartwcs.common.utils.Validator;
import com.lgcns.smartwcs.menu.model.Menu;
import com.lgcns.smartwcs.menu.model.MenuSearchCondition;
import com.lgcns.smartwcs.menu.repository.MenuMapper;
import com.lgcns.smartwcs.port.model.PortSearchCondition;
import com.lgcns.smartwcs.port.repository.PortRepository;
import com.lgcns.smartwcs.role.model.Role;
import com.lgcns.smartwcs.role.model.RoleMenu;
import com.lgcns.smartwcs.role.model.ids.RoleId;
import com.lgcns.smartwcs.role.repository.RoleMenuMappingMapper;
import com.lgcns.smartwcs.role.repository.RoleRepository;
import com.lgcns.smartwcs.tenant.model.Tenant;
import com.lgcns.smartwcs.tenant.service.TenantService;
import com.lgcns.smartwcs.user.model.User;
import com.lgcns.smartwcs.user.model.UserDTO;
import com.lgcns.smartwcs.user.model.UserRoleHist;
import com.lgcns.smartwcs.user.model.UserSearchCondition;
import com.lgcns.smartwcs.user.repository.UserMapper;
import com.lgcns.smartwcs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.lgcns.smartwcs.common.utils.CommonConstants.*;

/**
 * <PRE>
 * 사용자 서비스 객체.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PortRepository portRepository;
    private final MenuMapper menuMapper;
    private final UserMapper userMapper;
    private final RoleMenuMappingMapper roleMenuMappingMapper;
    private final SaltUtil saltUtil;
    private final Validator validator;
    private final MessageSourceAccessor accessor;

    private final MailClient mailClient;
    private final TenantService tenantService;
    private final CenterService centerService;
    private final AES256 aes256;

    public Map<String, Object> getUserPagedList(UserSearchCondition condition) {
        condition.setUserLvl(4);
        return getPagedList(condition);
    }

    public List<UserDTO> getUserUnPagedList(UserSearchCondition condition) {
        condition.setUserLvl(4);
        return userMapper.getUserList(condition);
    }

    /**
     * 특정 키의 사용자 정보 조회
     *
     * @param userId 사용자의 복합 키
     * @return 조회된 사용자 객체
     * @throws DataNotFoundException 사용자를 찾을 수 없을때 발생
     */
    public User get(String userId) throws DataNotFoundException {
        List<User> userList = userRepository.findAllbyUserId(userId);

        //Login에서 사용하기 위해서 에러 코드를 넣었음. 다른 곳에서 사용하려면 변경해야함
        if (userList.isEmpty()) {
            throw new DataNotFoundException("WRONG_LOGIN_INFO");
        } else if (userList.size() > 1) {
            throw new DataNotFoundException("MULTIPLE_USER_ID");
        }

        User user = userList.get(0);
        if ((user.getUseYn().equals("N")) || !(user.getUserId().equals(userId))) {
            throw new DataNotFoundException("WRONG_LOGIN_INFO");
        }

        return user;
    }

    /**
     * 사용자를 추가한다.
     *
     * @param user 사용자 정보
     * @return 추가된 사용자 정보
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public User create(User user) throws DataDuplicateException, NoSuchAlgorithmException, InvalidRequestException, DataNotFoundException {
        this.validate(user);
        this.validateUser(user);

        LocalDateTime now = LocalDateTime.now();
        setUser(user, 4, now);

        return saveUser(user);
    }

    private String encryptedRandomPassword(String randomPassword, String salt) {

        return saltUtil.encryptPassword(randomPassword, salt);
    }

    /**
     * 사용자를 수정한다.
     *
     * @param user 수정할 사용자 정보
     * @return 수정된 사용자 객체
     * @throws DataNotFoundException
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public User update(User user) throws DataNotFoundException, NoSuchAlgorithmException, InvalidRequestException {
        this.validate(user);

        final Optional<User> byId = userRepository.findById(user.getUserId());
        if (!byId.isPresent()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(MessageEnum.USER_ID.getValue(), user.getUserId() + " " + accessor.getMessage("validation.notFound"));
            throw new DataNotFoundException(messageArray);
        }

        User prevUser = byId.get();

        // KioskYn 이 Y 인데 N으로 바뀔 때 Port 관리에 맵핑이 되어 있으면 바꾸지 못하게 막아야 한다.
        if ("N".equals(user.getKioskYn()) && prevUser.getKioskYn().equals("Y")) {
            PortSearchCondition port = PortSearchCondition.builder()
                    .coCd(user.getCoCd())
                    .cntrCd(user.getCntrCd())
                    .userId(user.getUserId())
                    .build();

            if (!portRepository.findAllBySearch(port).isEmpty()) {
                HashMap<String, String> messageArray = new HashMap<>();
                messageArray.put(MessageEnum.USER_ID.getValue(), user.getUserId() + " " + String.format(accessor.getMessage("validation.portMapping"), portRepository.findAllBySearch(port).get(0).getEqpId(), portRepository.findAllBySearch(port).get(0).getPortId()));
                throw new InvalidRequestException(messageArray);
            }
        }

        //RoleCd 체크해서 Role 변경사항 업데이트
        updateRoleHist(prevUser, user);


        // 변경가능한 항목만 변경
        prevUser.setUserNm(user.getUserNm());
        prevUser.setUseYn(user.getUseYn());
        prevUser.setPwdInitYn(user.getPwdInitYn());
        prevUser.setUserEqpIdMap(user.getUserEqpIdMap());
        prevUser.setRoleCd(user.getRoleCd());
        prevUser.setKioskYn(user.getKioskYn());

        // randomPassword, salt 키 생성
        if ("Y".equals(user.getPwdInitYn())) {

            String salt = saltUtil.generateSalt();
            String randomPassword = CommonUtils.generateRandomPassword();
            log.info(MessageEnum.RANDOM_PASSWORD.getValue(), randomPassword);

            prevUser.setPwd(encryptedRandomPassword(randomPassword, salt));
            prevUser.setSalt(salt);

            User savedUser = userRepository.save(prevUser);

            String decryptedEmail;

            if (user.getUserLvl() == 2) {
                Tenant tenant = tenantService.get(user.getCoCd());

                decryptedEmail = tenant.getTenantEmail();
            } else {
                CenterId centerId = CenterId.builder()
                        .coCd(user.getCoCd())
                        .cntrCd(user.getCntrCd())
                        .build();

                Center center = centerService.get(centerId);

                decryptedEmail = center.getCntrEmail();
            }

            //tntId는 현재는 무조건 MessageEnum.DEFAULT_TENANT_ID.getValue() 임
            mailClient.sendPassword(MessageEnum.DEFAULT_TENANT_ID.getValue(), user.getUserNm(), user.getUserId(), decryptedEmail, randomPassword);

            return savedUser;
        }

        return userRepository.save(prevUser);
    }

    private void updateRoleHist(User prevUser, User user) {
        if (StringUtils.hasText(prevUser.getRoleCd()) && StringUtils.hasText(user.getRoleCd()) && !prevUser.getRoleCd().equals(user.getRoleCd())) {
            // User Role Hist에 업데이트
            UserRoleHist userRoleHist = UserRoleHist.builder()
                    .coCd(prevUser.getCoCd())
                    .cntrCd(prevUser.getCntrCd())
                    .userId(prevUser.getUserId())
                    .useYn(user.getUseYn())
                    .preRoleCd(prevUser.getRoleCd())
                    .roleCd(user.getRoleCd())
                    .regId(user.getRegId())
                    .build();

            saveUserRoleHist(userRoleHist);
        } else if (StringUtils.hasText(user.getRoleCd()) && !StringUtils.hasText(prevUser.getRoleCd())) {
            UserRoleHist userRoleHist = UserRoleHist.builder()
                    .coCd(prevUser.getCoCd())
                    .cntrCd(prevUser.getCntrCd())
                    .userId(prevUser.getUserId())
                    .useYn(user.getUseYn())
                    .preRoleCd("INIT")
                    .roleCd(user.getRoleCd())
                    .regId(user.getRegId())
                    .build();

            saveUserRoleHist(userRoleHist);
        } else if (!prevUser.getUseYn().equals(user.getUseYn())) {
            UserRoleHist userRoleHist = UserRoleHist.builder()
                    .coCd(user.getCoCd())
                    .cntrCd(user.getCntrCd())
                    .userId(user.getUserId())
                    .useYn(user.getUseYn())
                    .preRoleCd(user.getUseYn().equals("Y") ? "ENABLE" : "DISABLE")
                    .roleCd(prevUser.getUserLvl() == 2 ? "TENANT_ADMIN" : user.getRoleCd())
                    .regId(user.getRegId())
                    .build();

            saveUserRoleHist(userRoleHist);
        }
    }

    private void saveUserRoleHist(UserRoleHist userRoleHist) {

        userMapper.saveUserRoleHist(userRoleHist);
    }


    public List<UserDTO> getCntrUserUnpagedList(UserSearchCondition condition) {
        condition.setUserLvl(3);
        return userMapper.getUserList(condition);
    }


    /**
     * 사용자를 추가한다.
     *
     * @param user 사용자 정보
     * @return 추가된 사용자 정보
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public User tenantCntrUserCreate(User user)
            throws DataDuplicateException, NoSuchAlgorithmException, InvalidRequestException, DataNotFoundException {
        this.validate(user);
        this.validateUser(user);

        LocalDateTime now = LocalDateTime.now();
        setUser(user, 3, now);

        //1. Admin Role 생성
        RoleId roleId = RoleId.builder().coCd(user.getCoCd())
                .cntrCd(user.getCntrCd()).roleCd("ADM").build();

        String roleCd = "ADM";
        if (!roleRepository.existsById(roleId)) {
            Role role = Role.builder()
                    .coCd(user.getCoCd())
                    .cntrCd(user.getCntrCd())
                    .roleCd(roleCd)
                    .roleNm("Admin")
                    .useYn("Y")
                    .regDt(now)
                    .updDt(now)
                    .regId(user.getRegId())
                    .updId(user.getRegId())
                    .build();

            roleRepository.save(role);
        }

        //2. Role - Menu Mapping
        //2 -1 . Role Menu 화면의 MenuID 찾기
        MenuSearchCondition condition = MenuSearchCondition.builder()
                .menuUrl("/roleMenu").build();
        List<Menu> menuList = menuMapper.getMenuIdByMenuUrl(condition);
        if (menuList.isEmpty()) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(MessageEnum.USER_ID.getValue(), accessor.getMessage("validation.role"));
            throw new DataDuplicateException(messageArray);
        }

        for (Menu menu : menuList) {
            RoleMenu roleMenu = RoleMenu.builder()
                    .coCd(user.getCoCd())
                    .cntrCd(user.getCntrCd())
                    .roleCd(roleCd)
                    .menuId(menu.getMenuId())
                    .roleMenuCd("A")
                    .regId(user.getRegId())
                    .regDt(now)
                    .build();

            roleMenuMappingMapper.saveRoleMenu(roleMenu);
        }

        //3. User에 Role Mapping
        user.setRoleCd(roleCd);

        return saveUser(user);
    }

    private User saveUser(User user) throws NoSuchAlgorithmException, DataNotFoundException {
        String salt = saltUtil.generateSalt();
        String randomPassword = CommonUtils.generateRandomPassword();
        log.info(MessageEnum.RANDOM_PASSWORD.getValue(), randomPassword);

        user.setPwd(encryptedRandomPassword(randomPassword, salt));
        user.setSalt(salt);

        UserRoleHist userRoleHist = UserRoleHist.builder()
                .coCd(user.getCoCd())
                .cntrCd(user.getCntrCd())
                .userId(user.getUserId())
                .useYn(user.getUseYn())
                .preRoleCd("INIT")
                .roleCd(user.getRoleCd())
                .regId(user.getRegId())
                .build();

        saveUserRoleHist(userRoleHist);

        User savedUser = userRepository.save(user);

        CenterId centerId = CenterId.builder()
                .coCd(user.getCoCd())
                .cntrCd(user.getCntrCd())
                .build();

        Center center = centerService.get(centerId);

        String decryptedEmail = center.getCntrEmail();

        //tntId는 현재는 무조건 MessageEnum.DEFAULT_TENANT_ID.getValue() 임
        mailClient.sendPassword(MessageEnum.DEFAULT_TENANT_ID.getValue(), user.getUserNm(), user.getUserId(), decryptedEmail, randomPassword);

        return savedUser;
    }

    public List<UserDTO> getTenantUserList(UserSearchCondition condition) {
        condition.setCntrCd(condition.getCoCd());
        condition.setUserLvl(2);
        return userMapper.getUserList(condition);
    }

    /**
     * 사용자를 추가한다.
     *
     * @param user 사용자 정보
     * @return 추가된 사용자 정보
     */
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public User tenantUserCreate(User user)
            throws DataDuplicateException, NoSuchAlgorithmException, InvalidRequestException, DataNotFoundException {
        this.validate(user);

        LocalDateTime now = LocalDateTime.now();
        setUser(user, 2, now);

        String randomPassword = CommonUtils.generateRandomPassword();
        log.info(MessageEnum.RANDOM_PASSWORD.getValue(), randomPassword);

        String salt = saltUtil.generateSalt();
        user.setPwd(encryptedRandomPassword(randomPassword, salt));
        user.setSalt(salt);

        UserRoleHist userRoleHist = UserRoleHist.builder()
                .coCd(user.getCoCd())
                .cntrCd(user.getCntrCd())
                .userId(user.getUserId())
                .useYn(user.getUseYn())
                .preRoleCd("INIT")
                .roleCd("TENANT_ADMIN")
                .regId(user.getRegId())
                .build();

        saveUserRoleHist(userRoleHist);

        User savedUser = userRepository.save(user);

        Tenant tenant = tenantService.get(user.getCoCd());

        String decryptedEmail = tenant.getTenantEmail();

        //tntId는 현재는 무조건 MessageEnum.DEFAULT_TENANT_ID.getValue() 임
        mailClient.sendPassword(MessageEnum.DEFAULT_TENANT_ID.getValue(), user.getUserNm(), user.getUserId(), decryptedEmail, randomPassword);

        return savedUser;
    }

    private Map<String, Object> getPagedList(UserSearchCondition condition) {
        Map<String, Object> resultMap = new HashMap<>();

        if (condition.getPage() == null)
            condition.setPage(0);

        if (condition.getPage() != null && condition.getSize() != null) {
            condition.setPage(condition.getSize() * condition.getPage());
        }

        Integer number = condition.getPage();
        Integer totalElements = 0;
        Integer size = 0;
        Integer totalPages = 0;

        List<UserDTO> content = userMapper.getUserList(condition);

        if (!content.isEmpty()) {

            totalElements = userMapper.getUserTotalCnt(condition) - 1;
            size = condition.getSize();
            totalPages = totalElements / size + 1;
        }

        resultMap.put(MessageEnum.CONTENT.getValue(), content);
        resultMap.put(MessageEnum.TOTAL_ELEMENTS.getValue(), totalElements);  // 총 count
        resultMap.put("size", size);   // size
        resultMap.put(MessageEnum.TOTAL_PAGES.getValue(), totalPages);   // 총 page 수
        resultMap.put(MessageEnum.NUMBER.getValue(), number);  // page 번호 0부터 시작

        return resultMap;
    }

    private void setUser(User user, Integer userLvl, LocalDateTime now) throws DataDuplicateException {
        user.setPwd(INITIAL_PASSWORD);
        user.setSalt(INITIAL_SALT);

        if (userRepository.existsById(user.getUserId())) {
            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put(MessageEnum.USER_ID.getValue(), MessageEnum.USER.getValue() + user.getUserId() + MessageEnum.EXIST.getValue());
            throw new DataDuplicateException(messageArray);
        }

        // 기본값 지정
        user.setLoginFailCnt(0);
        user.setLoginYn("N");
        user.setUserLvl(userLvl);

        if (userLvl < 4) {
            user.setKioskYn(user.getKioskYn() == null ? "N" : user.getKioskYn());
        }

        user.setRegDt(now);
        user.setUpdDt(now);
        user.setUpdId(user.getRegId());
    }

    private void validate(User user) throws InvalidRequestException {
        HashMap<String, String> messageArray = new HashMap<>();

        validator.validateAndThrow(true, "coCd", user.getCoCd(), "column.coCd", 30, messageArray);
        validator.validateAndThrow(true, "cntrCd", user.getCntrCd(), "column.cntrCd", 30, messageArray);
        validator.validateAndThrow(true, "userId", user.getUserId(), "column.userId", 30, messageArray);
        validator.validateAndThrow(true, "userNm", user.getUserNm(), "column.userNm", 30, messageArray);
        validator.validateAndThrow(false, "roleCd", user.getRoleCd(), "column.roleCd", 30, messageArray);
        validator.validateAndThrow(false, "userEqpIdMap", user.getUserEqpIdMap(), "column.eqpId", 30, messageArray);
        validator.validateAndThrow(true, "useYn", user.getUseYn(), "column.useYn", 1, messageArray);
        validator.validateAndThrow(true, "regId", user.getRegId(), "column.regId", 30, messageArray);

        validator.validateAndThrow(false, "userLvl", user.getUserLvl(), "column.userLvl", 9, messageArray);
    }

    private void validateUser(User user) throws InvalidRequestException {

        if (!user.getUserId().startsWith(user.getCoCd())) {

            HashMap<String, String> messageArray = new HashMap<>();
            messageArray.put("userId", accessor.getMessage("validation.userId.form") + user.getCoCd() + accessor.getMessage("validation.userId.fill"));
            throw new InvalidRequestException(messageArray);
        }
    }
}
