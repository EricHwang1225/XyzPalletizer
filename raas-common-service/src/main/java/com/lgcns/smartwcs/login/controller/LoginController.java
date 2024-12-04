/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.login.controller;


import com.lgcns.smartwcs.center.model.ids.CenterId;
import com.lgcns.smartwcs.center.service.CenterService;
import com.lgcns.smartwcs.code.model.Code;
import com.lgcns.smartwcs.code.model.CodeSearchCondition;
import com.lgcns.smartwcs.code.service.CodeService;
import com.lgcns.smartwcs.common.exception.DataNotFoundException;
import com.lgcns.smartwcs.common.utils.SaltUtil;
import com.lgcns.smartwcs.eqp.model.Eqp;
import com.lgcns.smartwcs.eqp.model.ids.EqpuipmentId;
import com.lgcns.smartwcs.eqp.repository.EqpRepository;
import com.lgcns.smartwcs.eqp.service.EqpService;
import com.lgcns.smartwcs.login.model.Login;
import com.lgcns.smartwcs.login.model.LoginAccess;
import com.lgcns.smartwcs.login.model.Token;
import com.lgcns.smartwcs.login.model.UpdatePasswordResponse;
import com.lgcns.smartwcs.login.service.LoginRequestService;
import com.lgcns.smartwcs.login.service.TokenService;
import com.lgcns.smartwcs.port.model.Port;
import com.lgcns.smartwcs.port.model.ids.PortPkId;
import com.lgcns.smartwcs.port.repository.PortRepository;
import com.lgcns.smartwcs.tenant.service.TenantService;
import com.lgcns.smartwcs.user.model.User;
import com.lgcns.smartwcs.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.lgcns.smartwcs.common.utils.CommonConstants.MessageEnum;

/**
 * <PRE>
 * 로그인을 위한 컨트롤러 클래스.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j
@RestController

@RequestMapping(value = "/api/common")
@RequiredArgsConstructor
public class LoginController {

    private final MessageSourceAccessor accessor;
    private final TokenService tokenService;
    private final LoginRequestService loginRequestService;
    private final UserService userService;
    private final CenterService centerService;
    private final EqpService eqpService;
    private final TenantService tenantService;
    private final CodeService codeService;
    private final SaltUtil saltUtil;
    private final EqpRepository eqpRepository;
    private final PortRepository portRepository;

    /**
     * 로그인을 처리한다.
     *
     * @param loginRequest Login 모델 객체
     * @return Login    json형태로 반환한다.
     * @throws IllegalArgumentException 필수 파라미터값이 없을 경우 발생.
     */
    /*
    테스트용 패스워드 'pwd'를 base64 변환하여 사용. cGFzc3dvcmQ=
    userId : YCT
    password : 변환전 password -> 변환후 cGFzc3dvcmQ=

     */
    @PostMapping(value = "/login", produces = {"application/json"})
    @Operation(summary = "로그인 처리", description = "로그인을 처리하고 토큰을 생성한다.")
    public Login login(@RequestBody Login loginRequest)
            throws IllegalArgumentException, DataNotFoundException {
        log.debug("##### login #####");
        Login loginModel = checkUserIdAndPassword(loginRequest);

        String userId = loginModel.getUserId();

        try {
            //cGFzc3dvcmQ=
            String decodedPassword = new String(Base64.getDecoder().decode(loginRequest.getPwd()));

            // 사용자의 롤 메뉴를 가져옴.
            User userModel = userService.get(userId);

            String coCd = userModel.getCoCd();
            String cntrCd = userModel.getCntrCd();

            if (!checkLoginValid(loginModel, userModel, loginRequest.getLangCd())) {
                return loginModel;
            }

            if (saltUtil.validatePassword(decodedPassword, userModel.getSalt(), userModel.getPwd())) {

                // ID Password가 맞으면 기존 token 삭제
                tokenService.expireTotalToken(userModel.getUserId());

                Token tokenModel = tokenService.createToken(userId); // Token 생성!
                loginModel.setCoCd(coCd);
                loginModel.setCntrCd(cntrCd);
                loginModel.setUserNm(userModel.getUserNm());
                loginModel.setRoleCd(userModel.getRoleCd());
                loginModel.setJwTokenString(tokenModel.getJwTokenString());

                // 성공하여 업데이트 하지 않음
                loginModel.setFailedUpdateCnt(0);

                loginModel.setUserLvl(userModel.getUserLvl());

                loginModel.setSuccessYn("Y");

                String tenantColor = tenantService.get(coCd).getBkgdColor();
                String tenantIcon = getTenantIcon(coCd);

                loginModel.setTenantIcon(tenantIcon);
                loginModel.setTenantColor(tenantColor);

                // Tanant 가 없으면 (Use Yn = N) 사용 불가한 Tenant라고 Error Return 그리고 Level 3||4에서 Center (Use Yn = N)  사용 체크 해서 사용 불가한 Center 라고 Error Return
                if (!tenantService.get(coCd).getUseYn().equals("Y")) {
                    loginModel.setSuccessYn("N");
                    loginModel.setFailedMessage(accessor.getMessage("validation.unavailable.tenant"));
                    return loginModel;
                }

                if (getUserLevel(userModel)) {

                    CenterId centerId = CenterId.builder().coCd(coCd).cntrCd(cntrCd).build();
                    if (!centerService.get(centerId).getUseYn().equals("Y")) {
                        loginModel.setSuccessYn("N");
                        loginModel.setFailedMessage(accessor.getMessage("validation.unavailable.center"));
                        return loginModel;
                    }

                    //Eqp 및 Port 셋팅
                    setEqpAndPort(coCd, cntrCd, loginRequest, loginModel);

                    loginRequest.setCoCd(coCd);
                    loginRequest.setCntrCd(cntrCd);
                    loginModel.setRoleMenuList(loginRequestService.getUserRoleMenuLvl3(loginRequest));
                    loginModel.setCntrNm(centerService.get(centerId).getCntrNm());

                    loginModel.setKioskYn(userModel.getKioskYn());

                } else if (userModel.getUserLvl() == 1) {
                    loginModel.setRoleMenuList(loginRequestService.getUserRoleMenuLvl1(loginRequest));
                }

                //로그인 완료 후 상태 저장
                loginRequestService.updateLoginComplete(userModel);


                // Login 성공 후 AccessLog 저장
                LoginAccess loginAccess = new LoginAccess();
                loginAccess.setCoCd(coCd);
                loginAccess.setCntrCd(cntrCd);
                loginAccess.setUserId(userId);
                loginAccess.setUserNm(loginRequest.getUserNm());
                loginAccess.setAccessTypeCd("LOGIN");

                loginRequestService.insertLoginAccessLog(loginAccess);

            } else {
                //로그인 update Count 증가
                loginRequestService.updateLoginFailCnt(userModel);

                setLoginFailedMessage(loginModel, MessageEnum.WRONG_LOGIN_INFO.getValue(), loginRequest.getLangCd());

            }
        } catch (DataNotFoundException de) {
            log.error(de.getMessage());
            setLoginFailedMessage(loginModel, de.getMessage(), loginRequest.getLangCd());
        } catch (Exception e) {
            log.error(e.getMessage());
            setLoginFailedMessage(loginModel, "Server Error", loginRequest.getLangCd());
        }
        log.debug("login result " + loginModel.getJwTokenString());
        return loginModel;
    }

    private void setEqpAndPort(String coCd, String cntrCd, Login loginRequest, Login loginModel) throws DataNotFoundException {
        if (loginRequest.getEqpId() != null) {

            EqpuipmentId eqpuipmentId = EqpuipmentId.builder()
                    .coCd(coCd)
                    .cntrCd(cntrCd)
                    .eqpId(loginRequest.getEqpId())
                    .build();

            getEqpName(loginModel, eqpuipmentId);

            PortPkId portPkId = PortPkId.builder()
                    .coCd(eqpuipmentId.getCoCd())
                    .cntrCd(eqpuipmentId.getCntrCd())
                    .eqpId(eqpuipmentId.getEqpId())
                    .portId(loginRequest.getPortId())
                    .build();

            setPortId(loginModel, portPkId);
        }
    }

    private boolean getUserLevel(User userModel) {
        if (userModel.getUserLvl() == 3 || userModel.getUserLvl() == 4) {
            return true;
        }

        return false;
    }

    private Login checkUserIdAndPassword(Login loginRequest) throws IllegalArgumentException {
        Login loginModel = new Login();
        if (loginRequest.getUserId() != null && !loginRequest.getUserId().isEmpty()) {
            loginModel.setUserId(loginRequest.getUserId());
        } else {
            throw new IllegalArgumentException(MessageEnum.THERE_IS_NO_USER_ID.getValue());
        }
        if (!StringUtils.hasText(loginRequest.getPwd())) {
            throw new IllegalArgumentException("password 값이 없습니다.");
        }

        return loginModel;
    }

    private String getTenantIcon(String coCd) throws DataNotFoundException {

        String tenantIcon = tenantService.get(coCd).getTenantIcon();
        //Tenant Icon이 없으면 LGWCS Icon으로 대체
        if (!StringUtils.hasText(tenantIcon)) {
            tenantIcon = tenantService.get(MessageEnum.SUPER_TENANT.getValue()).getTenantIcon();
        }

        return tenantIcon;
    }

    private boolean checkLoginValid(Login loginModel, User userModel, String langCd) {
        if (userModel.getPwdInitYn().equals("Y")) {
            loginModel.setPwdInitYn("Y");
            setLoginFailedMessage(loginModel, "PWD_INIT", langCd);
            return false;
        }

        if (userModel.getLoginFailCnt() >= 5) {
            setLoginFailedMessage(loginModel, "LOGIN_FAILED", langCd);
            return false;
        }

        if (userModel.getPwdChgDt() == null) {
            setLoginFailedMessage(loginModel, "REQUEST_PWD_INIT", langCd);
            return false;
        }

        if (userModel.getPwdChgDt().plusDays(90).isBefore(LocalDateTime.now())) {
            loginModel.setPwdInitYn("Y");
            setLoginFailedMessage(loginModel, "PERIOD_PASSED", langCd);
            return false;
        }
        return true;
    }

    private void getEqpName(Login loginModel, EqpuipmentId eqpuipmentId) throws DataNotFoundException {
        if (eqpRepository.existsById(eqpuipmentId)) {
            Eqp eqp = eqpService.get(eqpuipmentId);
            loginModel.setEqpId(eqpuipmentId.getEqpId());
            loginModel.setEqpNm(eqp.getEqpNm());
            loginModel.setEqpTypeCd(eqp.getEqpTypeCd());
            loginModel.setToteRegex(eqp.getToteRegex());
            loginModel.setToteUnavblMin(eqp.getToteUnavblMin());
            loginModel.setAppNm(eqp.getAppNm());
        }
    }

    private void setPortId(Login loginModel, PortPkId portPkId) throws DataNotFoundException {
        final Optional<Port> byId = portRepository.findById(portPkId);

        if (byId.isPresent() && StringUtils.hasText(byId.get().getUserId()) && byId.get().getUserId().equals(loginModel.getUserId())) {
            loginModel.setPortId(portPkId.getPortId());
        }
    }

    private void setLoginFailedMessage(Login loginModel, String errMessage, String langCd) {
        loginModel.setSuccessYn("N");
        CodeSearchCondition condition = CodeSearchCondition.builder()
                .coCd(MessageEnum.SUPER_TENANT.getValue())
                .comHdrCd("ERR_MSG")
                .comDtlCd(errMessage)
                .hdrFlag("N")
                .useYn("Y")
                .langCd(langCd)
                .build();

        List<Code> codes = codeService.getTranslatedCodeList(condition);

        if (!codes.isEmpty()) {
            loginModel.setFailedMessage(codes.get(0).getComDtlNm());
        } else {
            if (StringUtils.hasText(errMessage)) {
                loginModel.setFailedMessage(new String(new StringBuilder().append("No Error Message.: ").append(errMessage)));
            } else {
                loginModel.setFailedMessage("No Error Message.");
            }
        }
    }

    @PostMapping(value = "/getUserRoleMenuLvl2", produces = {"application/json"})
    @Operation(summary = "사용자 Level2의 메뉴리스트", description = "사용자 Level2의 메뉴리스트를 가져온다")
    public Login getUserRoleMenuLvl2(@RequestBody Login loginRequest)
            throws IllegalArgumentException {


        Login loginModel = new Login();
        loginModel.setRoleMenuList(loginRequestService.getUserRoleMenuLvl2(loginRequest));

        return loginModel;
    }

    /**
     * 테스트용 임의 URL 처리에 사용.
     *
     * @param id  사용자ID
     * @param pwd 비밀번호
     * @return Login 모델을 반환함.
     */
    @PostMapping(value = "/other", produces = {"application/json"})
    @Operation(summary = "필터 테스트용 URL", description = "예외처리 되지 않은  URL 테스트용.")
    public Login other(
            @RequestParam(value = "id", defaultValue = "id") String id,
            @RequestParam(value = "pwd", defaultValue = "A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=") String pwd) {
        return new Login();
    }

    /**
     * 로그인 작업 실패시 로그인 실패 횟수를 업데이트.
     *
     * @param loginRequest Login 모델 객체
     * @return Integer 업데이트된 대상의 수 ( 0보다 클때 성공. )
     * @throws IllegalArgumentException 필수 파라미터값이 없을 경우 발생.
     */
    @PostMapping(value = "/login/updateLoginFailCnt", produces = {"application/json"})
    @Operation(summary = "로그인 실패 횟수 업데이트", description = "로그인 실패 횟수를 1 증가시킨다.")
    public Integer updateLoginFailCnt(@RequestBody Login loginRequest)
            throws IllegalArgumentException, DataNotFoundException {

        if (loginRequest.getUserId() == null || loginRequest.getUserId().isEmpty()) {
            throw new IllegalArgumentException(MessageEnum.THERE_IS_NO_USER_ID.getValue());
        }

        User userModel = userService.get(loginRequest.getUserId());

        return loginRequestService.updateLoginFailCnt(userModel);
    }

    /**
     * 비밀번호 설정 화면상에서 패스워드 변경 작업 완료시
     *
     * @param loginRequest Login 모델 객체
     * @return Integer 업데이트된 대상의 수 ( 0보다 클때 성공. )
     * @throws IllegalArgumentException 필수 파라미터값이 없을 경우 발생.
     */
    @PostMapping(value = "/login/updatePasswordChangeComplete", produces = {"application/json"})
    @Operation(summary = "비밀번호 변경후 상태 초기화", description = "비밀번호 변경후 로그인 실패 기록 등을 초기화")
    public UpdatePasswordResponse updatePasswordChangeComplete(@RequestBody Login loginRequest)
            throws IllegalArgumentException {

        try {
            if (loginRequest.getUserId() == null || loginRequest.getUserId().isEmpty()) {
                throw new IllegalArgumentException(MessageEnum.THERE_IS_NO_USER_ID.getValue());
            }
            if (loginRequest.getPwd() == null || loginRequest.getPwd().isEmpty()) {
                return UpdatePasswordResponse.builder()
                        .isUpdated(false)
                        .updateFailMessage(getFailedMessage("NO_NEW_PWD", loginRequest.getLangCd()))
                        .build();
            }
            if (loginRequest.getPrePwd() == null || loginRequest.getPrePwd().isEmpty()) {
                return UpdatePasswordResponse.builder()
                        .isUpdated(false)
                        .updateFailMessage(getFailedMessage("NO_CURRENT_PWD", loginRequest.getLangCd()))
                        .build();
            }


            User userModel = userService.get(loginRequest.getUserId());

            //password 변경 대상자인지 Check
            if (userModel.getPwdInitYn().equals("Y") || userModel.getPwdChgDt() == null || userModel.getPwdChgDt().plusDays(90).isBefore(LocalDateTime.now())) {

                String decodedPrePassword = new String(Base64.getDecoder().decode(loginRequest.getPrePwd()));
                String decodedPassword = new String(Base64.getDecoder().decode(loginRequest.getPwd()));

                if (decodedPrePassword.equals(decodedPassword)) {
                    return UpdatePasswordResponse.builder()
                            .isUpdated(false)
                            .updateFailMessage(getFailedMessage("CANT_SAME_PWD", loginRequest.getLangCd()))
                            .build();
                }

                if (!saltUtil.validatePassword(decodedPrePassword, userModel.getSalt(), userModel.getPwd())) {
                    return UpdatePasswordResponse.builder()
                            .isUpdated(false)
                            .updateFailMessage(getFailedMessage("WRONG_CURRENT_PWD", loginRequest.getLangCd()))
                            .build();
                }

                checkPasswordRegex(decodedPassword);

                String salt = saltUtil.generateSalt();
                userModel.setSalt(salt);
                userModel.setPwd(saltUtil.encryptPassword(decodedPassword, salt));

                loginRequestService.updatePasswordChangeComplete(userModel);

                return UpdatePasswordResponse.builder().isUpdated(true).build();
            } else {
                return UpdatePasswordResponse.builder()
                        .isUpdated(false)
                        .updateFailMessage("CANT_CHG_PWD")
                        .build();
            }
        } catch (IllegalArgumentException ie) {
            return UpdatePasswordResponse.builder()
                    .isUpdated(false)
                    .updateFailMessage(accessor.getMessage("errors.system"))
                    .build();
        } catch (Exception e) {
            return UpdatePasswordResponse.builder()
                    .isUpdated(false)
                    .updateFailMessage(accessor.getMessage("errors.system"))
                    .build();
        }
    }

    private String getFailedMessage(String errMessage, String langCd) {

        CodeSearchCondition condition = CodeSearchCondition.builder()
                .coCd(MessageEnum.SUPER_TENANT.getValue())
                .comHdrCd("ERR_MSG")
                .comDtlCd(errMessage)
                .hdrFlag("N")
                .useYn("Y")
                .langCd(langCd)
                .build();

        List<Code> codes = codeService.getTranslatedCodeList(condition);

        if (!codes.isEmpty()) {
            return codes.get(0).getComDtlNm();
        } else {
            if (StringUtils.hasText(errMessage)) {
                return new String(new StringBuilder().append("No Error Message.: ").append(errMessage));
            } else {
                return "No Error Message.";
            }
        }
    }

    private void checkPasswordRegex(String decodedPassword) throws IllegalArgumentException {

        if (decodedPassword.length() < 8) {
            throw new IllegalArgumentException("암호는 8자 이상이어야 합니다.");
        }
        if (decodedPassword.length() > 16) {
            throw new IllegalArgumentException("암호는 16자 이하여야 합니다.");
        }
        if (!decodedPassword.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("최소한 하나의 숫자를 제공해야 합니다.");
        }
        if (!decodedPassword.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("하나 이상의 대문자를 제공합니다.");
        }

        if (!decodedPassword.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("하나 이상의 소문자를 제공합니다.");
        }

        if (!decodedPassword.matches(".*[!@#$%^&*].*")) {
            throw new IllegalArgumentException("하나 이상의 특수문자를 제공합니다.");
        }

    }

    /**
     * 로그인 작업 완료 후 로그인 상태를 저장한다.
     *
     * @param loginRequest Login 모델 객체
     * @return Integer 업데이트된 대상의 수 ( 0보다 클때 성공. )
     * @throws IllegalArgumentException 필수 파라미터값이 없을 경우 발생.
     */
    @PostMapping(value = "/login/updateLoginComplete", produces = {"application/json"})
    @Operation(summary = "로그인 완료 후 상태 저장", description = "로그인 완료 후 상태를 기록한다")
    public Integer updateLoginComplete(@RequestBody Login loginRequest)
            throws IllegalArgumentException, DataNotFoundException {


        if (loginRequest.getUserId() == null || loginRequest.getUserId().isEmpty()) {
            throw new IllegalArgumentException(MessageEnum.THERE_IS_NO_USER_ID.getValue());
        }

        User userModel = userService.get(loginRequest.getUserId());

        return loginRequestService.updateLoginComplete(userModel);
    }


    /**
     * 로그아웃 작업 완료 후 로그인 상태를 저장한다.
     *
     * @param loginRequest Login 모델 객체
     * @return Integer 업데이트된 대상의 수 ( 0보다 클때 성공. )
     * @throws IllegalArgumentException 필수 파라미터값이 없을 경우 발생.
     */
    @PostMapping(value = "/login/updateLogoutComplete", produces = {"application/json"})
    @Operation(summary = "로그아웃 완료 후 상태 저장", description = "로그아웃 완료 후 상태를 기록한다")
    public Integer updateLogoutComplete(@RequestBody Login loginRequest)
            throws IllegalArgumentException {

        User userModel = new User();

        if (loginRequest.getUserId() != null && !loginRequest.getUserId().isEmpty()) {
            userModel.setUserId(loginRequest.getUserId());
        } else {
            throw new IllegalArgumentException(MessageEnum.THERE_IS_NO_USER_ID.getValue());
        }
        if (loginRequest.getCoCd() != null && !loginRequest.getCoCd().isEmpty()) {
            userModel.setCoCd(loginRequest.getCoCd());
        } else {
            throw new IllegalArgumentException("coCd 값이 없습니다.");
        }
        if (loginRequest.getCntrCd() != null && !loginRequest.getCntrCd().isEmpty()) {
            userModel.setCntrCd(loginRequest.getCntrCd());
        } else {
            throw new IllegalArgumentException("cntrCd 값이 없습니다.");
        }
//        로그아웃 후 인증 Token에서 삭제
        tokenService.expireTotalToken(userModel.getUserId());

        // LogOut 성공 후 AccessLog 저장
        LoginAccess loginAccess = new LoginAccess();
        loginAccess.setUserId(loginRequest.getUserId());
        loginAccess.setUserNm(loginRequest.getUserNm());
        loginAccess.setCoCd(loginRequest.getCoCd());
        loginAccess.setCntrCd(loginRequest.getCntrCd());

        loginAccess.setAccessTypeCd("LOGOUT");

        loginRequestService.insertLoginAccessLog(loginAccess);

        return loginRequestService.updateLogoutComplete(userModel);
    }


}
