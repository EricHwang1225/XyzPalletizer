package com.lgcns.smartwcs.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {
    public static final String SYSTEM_ERROR = "시스템 에러가 발생하였습니다.";
    public static final String INITIAL_PASSWORD = "";
    public static final String INITIAL_SALT = "";

    private static final String COMPANY_CODE_VALUE = "coCd";
    private static final String CENTER_CODE_VALUE = "cntrCd";
    private static final String EQUIPMENT_ID_VALUE = "eqpId";
    private static final String PORT_ID_VALUE = "portId";
    private static final String THERE_ARE_NO_VALUE = "가 없습니다.";
    private static final String INBD_TYPE_CODE_LIST_VALUE = "inbdTypeCdList";
    private static final String INBD_UID_KEY_VALUE = "inbdUidKey";
    private static final String INBD_UID_SUB_NO_VALUE = "inbdUidSubNo";
    private static final String SUCCESS_VALUE = "SUCCESS";
    private static final String BIN_ID_VALUE = "binId";

    private static final String AS_MODE_VALUE = "asMode";
    private static final String STTK_UID_KEY_VALUE = "sttkUidKey";
    private static final String RESULT_CODE_VALUE = "resultCode";
    private static final String RESULT_MESSAGE_VALUE = "resultMessage";
    private static final String STATUS_CODE_VALUE = "statusCd";
    private static final String STATUS_VALUE = "status";
    private static final String EXTERN_INBD_NO_VALUE = "ExternInbdNo: ";
    private static final String EXTERN_INBD_SUB_NO_VALUE = "(ExternInbdSubNo: ";
    private static final String EXTERN_OBND_NO_VALUE = "ExternObndNo: ";
    private static final String EXTERN_OBND_SUB_NO_VALUE = "(ExternObndSubNo: ";
    private static final String EXTERN_STTK_NO_VALUE = "ExternSttkNo(";
    private static final String EXTERN_STTK_SUB_NO_VALUE = "ExternSttkSubNo";
    private static final String DATETIME_FORMAT_VALUE = "yyyyMMddHHmmss";

    private static final String REG_DT_VALUE = "regDt";
    private static final String REG_ID_VALUE = "regId";
    private static final String REGDT_ERROR_VALUE = "regDt 는 14자리로 입력하세요";
    private static final String REGID_ERROR_VALUE = "regId 은 30자리 이하로 입력하세요";
    private static final String SUPER_TENANT_VALUE = "LGWCS";
    private static final String THERE_IS_NO_USER_ID_VALUE = "userId 값이 없습니다.";
    private static final String MENU_ID_VALUE = "menuId";
    private static final String TOTAL_QUANTITY_VALUE = "totalQty";
    private static final String OBND_DETAIL_VALUE = "obndDetail";
    private static final String DETAIL_COUNT_VALUE = "detailCnt";
    private static final String STTK_DETAIL_VALUE = "sttkDetail";
    private static final String TENANT_ID_VALUE = "Tenant ID";
    private static final String SORT_SEQ_VALUE = "sortSeq";
    private static final String USE_YN_VALUE = "useYn";
    private static final String CENTER_CODE_KR_VALUE = "센터 코드";
    private static final String EXIST_VALUE = "가 존재합니다.";
    private static final String CANT_FIND_VALUE = "를 찾을 수 없습니다.";
    private static final String XLSX_VALUE = ".xlsx";
    private static final String ATTACHMENT_FILE_NAME_VALUE = "attachment; filename=";
    private static final String MODEL_LIST_VALUE = "modelList";
    private static final String CONTENT_DISPOSITION_VALUE = "Content-disposition";
    private static final String USER_LIST_VALUE = "userList";
    private static final String INIT_VALUE = "비밀번호 초기화";
    private static final String CONTENT_VALUE = "content";
    private static final String TOTAL_ELEMENTS_VALUE = "totalElements";
    private static final String TOTAL_PAGES_VALUE = "totalPages";
    private static final String NUMBER_VALUE = "number";
    private static final String USER_ID_VALUE = "userId";
    private static final String USER_VALUE = "사용자: ";

    private static final String WRONG_LOGIN_INFO_VALUE = "WRONG_LOGIN_INFO";

    public static final String UTF8 = "UTF-8";

    private static final String RANDOM_PASSWORD_VALUE = "randomPassword: {}";

    private static final String DEFAULT_TENANT_ID_VALUE = "X0000";

    public enum MessageEnum {
        COMPANY_CODE(COMPANY_CODE_VALUE),
        CENTER_CODE(CENTER_CODE_VALUE),
        EQUIPMENT_ID(EQUIPMENT_ID_VALUE),
        PORT_ID(PORT_ID_VALUE),
        THERE_ARE_NO(THERE_ARE_NO_VALUE),
        INBD_TYPE_CODE_LIST(INBD_TYPE_CODE_LIST_VALUE),
        INBD_UID_KEY(INBD_UID_KEY_VALUE),
        INBD_UID_SUB_NO(INBD_UID_SUB_NO_VALUE),
        SUCCESS(SUCCESS_VALUE),
        BIN_ID(BIN_ID_VALUE),
        AS_MODE(AS_MODE_VALUE),
        STTK_UID_KEY(STTK_UID_KEY_VALUE),
        RESULT_CODE(RESULT_CODE_VALUE),
        RESULT_MESSAGE(RESULT_MESSAGE_VALUE),
        STATUS_CODE(STATUS_CODE_VALUE),
        STATUS(STATUS_VALUE),
        EXTERN_INBD_NO(EXTERN_INBD_NO_VALUE),
        EXTERN_INBD_SUB_NO(EXTERN_INBD_SUB_NO_VALUE),
        EXTERN_OBND_NO(EXTERN_OBND_NO_VALUE),
        EXTERN_OBND_SUB_NO(EXTERN_OBND_SUB_NO_VALUE),
        EXTERN_STTK_NO(EXTERN_STTK_NO_VALUE),
        EXTERN_STTK_SUB_NO(EXTERN_STTK_SUB_NO_VALUE),
        DATETIME_FORMAT(DATETIME_FORMAT_VALUE),
        REG_DT(REG_DT_VALUE),
        REG_ID(REG_ID_VALUE),
        REGDT_ERROR(REGDT_ERROR_VALUE),
        REGID_ERROR(REGID_ERROR_VALUE),
        SUPER_TENANT(SUPER_TENANT_VALUE),
        THERE_IS_NO_USER_ID(THERE_IS_NO_USER_ID_VALUE),
        MENU_ID(MENU_ID_VALUE),
        TOTAL_QUANTITY(TOTAL_QUANTITY_VALUE),
        OBND_DETAIL(OBND_DETAIL_VALUE),
        DETAIL_COUNT(DETAIL_COUNT_VALUE),
        STTK_DETAIL(STTK_DETAIL_VALUE),
        TENANT_ID(TENANT_ID_VALUE),
        SORT_SEQ(SORT_SEQ_VALUE),
        USE_YN(USE_YN_VALUE),
        CENTER_CODE_KR(CENTER_CODE_KR_VALUE),
        EXIST(EXIST_VALUE),
        CANT_FIND(CANT_FIND_VALUE),
        XLSX(XLSX_VALUE),
        ATTACHMENT_FILE_NAME(ATTACHMENT_FILE_NAME_VALUE),
        MODEL_LIST(MODEL_LIST_VALUE),
        CONTENT_DISPOSITION(CONTENT_DISPOSITION_VALUE),
        USER_LIST(USER_LIST_VALUE),
        PASSWORD_INIT(INIT_VALUE),
        CONTENT(CONTENT_VALUE),
        TOTAL_ELEMENTS(TOTAL_ELEMENTS_VALUE),
        TOTAL_PAGES(TOTAL_PAGES_VALUE),
        NUMBER(NUMBER_VALUE),
        USER_ID(USER_ID_VALUE),
        USER(USER_VALUE),
        RANDOM_PASSWORD(RANDOM_PASSWORD_VALUE),

        DEFAULT_TENANT_ID(DEFAULT_TENANT_ID_VALUE),

        WRONG_LOGIN_INFO(WRONG_LOGIN_INFO_VALUE);


        private String value;

        MessageEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
