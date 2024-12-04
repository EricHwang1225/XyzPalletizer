package com.lgcns.smartwcs.common.utils;

import com.lgcns.smartwcs.common.exception.CommonRuntimeException;
import org.springframework.util.StringUtils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class Aes256Converter implements AttributeConverter<String, String> {
    private final AES256 aes256;

    public Aes256Converter() {
        this.aes256 = new AES256();
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (!StringUtils.hasText(attribute)) {
            return attribute;
        }
        try {
            return aes256.encrypt(attribute);
        } catch (Exception e) {
            throw new CommonRuntimeException("failed to encrypt data");
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return aes256.decrypt(dbData);
        } catch (Exception e) {
            return dbData;
        }
    }
}
