package com.lgcns.smartwcs.common.utils;

import com.lgcns.smartwcs.common.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class Validator {

    private static final String LANG_NOT_FOUND = "validation.notFound";
    private static final String LANG_MAX_LENGTH = "validation.maxLength";
    private static final String LANG_SUBJECT = "validation.subject";
    private static final String REGEX_VALUE = "a-zA-Z0-9";
    private final MessageSourceAccessor accessor;

    public void validateCdAndThrow(String key, String value, String keyName, Map<String, String> messageArray)
            throws InvalidRequestException {
        if ("LGWCS".equals(value)) {
            messageArray.put(key, accessor.getMessage(keyName) + " " + accessor.getMessage(LANG_NOT_FOUND));
        }
        if (messageArray.size() > 0) {
            throw new InvalidRequestException(messageArray);
        }
    }

    public void validateAndThrow(boolean isRequired, String key, String value, String keyName,
                                 int limit, Map<String, String> messageArray) throws InvalidRequestException {
        String regexPattern = String.format("^[%s+-_.]+@[%s-]+\\.[%s-.]+$", REGEX_VALUE, REGEX_VALUE, REGEX_VALUE);

        if (!StringUtils.hasText(value)) { // 필수 필드인지 체크
            if (!isRequired) {
                return;
            }
            messageArray.put(key, accessor.getMessage(keyName) + " " + accessor.getMessage(LANG_NOT_FOUND));
        } else if (key.toLowerCase().indexOf("email") >= 0 && !Pattern.compile(regexPattern)
                .matcher(value)
                .matches()) { // 이메일 유효성 체크
            messageArray.put(key, accessor.getMessage(keyName) + " " + accessor.getMessage("validation.email"));
        } else if ("uri".equals(key.toLowerCase()) && !value.startsWith("/")) {
            messageArray.put(key, accessor.getMessage(keyName) + " " + accessor.getMessage("validation.uri"));
        } else if (limit >= 0 && value.length() > limit) { // 자릿수 체크, 만일 limit가 음수이면 체크 안함
            messageArray.put(key, accessor.getMessage(keyName) + " " + accessor.getMessage(LANG_SUBJECT) + " " + limit + " " + accessor.getMessage(LANG_MAX_LENGTH));
        }

        if (messageArray.size() > 0) {
            throw new InvalidRequestException(messageArray);
        }
    }

    public void validateAndThrow(boolean isRequired, String key, Integer value, String keyName,
                                 int limit, Map<String, String> messageArray) throws InvalidRequestException {
        if (value == null) {
            if (!isRequired) {
                return;
            }
            messageArray.put(key, accessor.getMessage(keyName) + " " + accessor.getMessage(LANG_NOT_FOUND));
        } else if (value >= Math.pow(10, limit)) {
            messageArray.put(key, accessor.getMessage(keyName) + " " + accessor.getMessage(LANG_SUBJECT) + " " + limit + " " + accessor.getMessage(LANG_MAX_LENGTH));
        }

        if (messageArray.size() > 0) {
            throw new InvalidRequestException(messageArray);
        }
    }

    public void validateAndThrow(boolean isRequired, String key, BigDecimal value, String keyName,
                                 int limit, Map<String, String> messageArray) throws InvalidRequestException {
        BigDecimal bigDecimal = BigDecimal.valueOf(10);
        if (value == null) {
            if (!isRequired) {
                return;
            }
            messageArray.put(key, accessor.getMessage(keyName) + " " + accessor.getMessage(LANG_NOT_FOUND));
        } else if (value.compareTo(bigDecimal.pow(limit)) > 0) {
            messageArray.put(key, accessor.getMessage(keyName) + " " + accessor.getMessage(LANG_SUBJECT) + " " + limit + " " + accessor.getMessage(LANG_MAX_LENGTH));
        }

        if (messageArray.size() > 0) {
            throw new InvalidRequestException(messageArray);
        }
    }
}
