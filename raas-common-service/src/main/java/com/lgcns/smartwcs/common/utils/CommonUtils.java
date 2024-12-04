package com.lgcns.smartwcs.common.utils;

import com.lgcns.smartwcs.code.model.Code;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class CommonUtils {

    public static final String NOT_FOUNT_ERROR = " 가(이) 없습니다.";
    public static final String MAX_LENGTH_ERROR1 = " 는(은) ";
    public static final String MAX_LENGTH_ERROR2 = " 자리 이하로 입력하세요.";
    public static final String MAX_LENGTH_ERROR3 = " 자리로 입력하세요.";

    public static final int MAX_PASSWORD_LENGTH = 16;

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage());
        }
    }

    public static void validateProperty(String key, String value, List<String> messageArray) {
        if (!StringUtils.hasText(value)) {
            messageArray.add(key);
        }
    }

    public static void validateProperty(String key, Integer value, List<String> messageArray) {
        if (value == null) {
            messageArray.add(key);
        }
    }

    public static void validateProperty(String key, List<?> value, List<String> messageArray) {
        if (value == null || value.isEmpty()) {
            messageArray.add(key);
        }
    }

    public static void validateParam(boolean isRequired, String key, String value, int limit, List<String> messageArray) {
        if (!StringUtils.hasText(value)) {
            if (!isRequired) {
                return;
            }
            messageArray.add(key + NOT_FOUNT_ERROR);
        } else if (value.length() > limit) {
            messageArray.add(key + MAX_LENGTH_ERROR1 + limit + MAX_LENGTH_ERROR2);
        }
    }

    public static void validateDateParam(boolean isRequired, String key, String value, int limit, List<String> messageArray) {
        if (!StringUtils.hasText(value)) {
            if (!isRequired) {
                return;
            }
            messageArray.add(key + NOT_FOUNT_ERROR);
        } else if (value.length() != limit) {
            messageArray.add(key + MAX_LENGTH_ERROR1 + limit + MAX_LENGTH_ERROR3);
        }
    }

    public static String getCurrentDate() {
        Date currentDate = Date.from(Instant.now());
        LocalDateTime localDateTime = currentDate.toInstant() // Date -> Instant
                .atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
                .toLocalDateTime();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static String generateRandomPassword() {
        int maxUpperCase = ThreadLocalRandom.current().nextInt(1, 4);
        int maxLowerCase = ThreadLocalRandom.current().nextInt(1, 5);
        int maxNumber = ThreadLocalRandom.current().nextInt(1, 3);
        int maxSpecial = ThreadLocalRandom.current().nextInt(1, 3);
        int restNumber = MAX_PASSWORD_LENGTH - (maxUpperCase + maxLowerCase + maxNumber + maxSpecial);

        String upperCaseLetters = RandomStringUtils.random(maxUpperCase, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(maxLowerCase, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(maxNumber);
        String specialChar = RandomStringUtils.random(maxSpecial, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(restNumber);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        return password;
    }

    public static String[] getStringArray(List<Code> codeList) {
        String[] titleArray = new String[codeList.size()];
        for (int i = 0; i < codeList.size(); ++i) {
            titleArray[i] = codeList.get(i).getComDtlNm();
        }

        return titleArray;
    }

    public static String replacePercentAndUnderbar(String value) {
        if (!StringUtils.hasText(value)) {
            return value;
        } else {
            return value.replace("%", "\\%").replace("_", "\\_");
        }
    }
}
