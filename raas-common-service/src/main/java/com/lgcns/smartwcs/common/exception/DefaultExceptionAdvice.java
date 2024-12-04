package com.lgcns.smartwcs.common.exception;

import com.lgcns.smartwcs.common.model.CommonJsonResponse;
import com.lgcns.smartwcs.common.model.InterfaceJsonResponse;
import com.lgcns.smartwcs.common.utils.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class DefaultExceptionAdvice {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<CommonJsonResponse> handleException(InvalidRequestException e) {
        log.error("advice InvalidRequestException: {}", e.getFieldMessage());

        CommonJsonResponse commonJsonResponse = CommonJsonResponse.builder()
                .resultCode(HttpServletResponse.SC_BAD_REQUEST)
                .validationResult(e.getFieldMessage())
                .build();

        return new ResponseEntity<>(commonJsonResponse, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidParametersException.class)
    public ResponseEntity<CommonJsonResponse> handleException(InvalidParametersException e) {
        String message = e.getMessageArray().toString() + CommonConstants.MessageEnum.THERE_ARE_NO.getValue();
        log.error("advice InvalidParametersException: {}", message);

        CommonJsonResponse commonJsonResponse = CommonJsonResponse.builder()
                .resultCode(400)
                .resultMessage(message)
                .build();

        return new ResponseEntity<>(commonJsonResponse, HttpStatus.OK);
    }

    @ExceptionHandler(DataDuplicateException.class)
    public ResponseEntity<CommonJsonResponse> handleException(DataDuplicateException e) {
        log.error("advice DataDuplicateException: {}", e.getFieldMessage());

        CommonJsonResponse commonJsonResponse = CommonJsonResponse.builder()
                .resultCode(600)
                .validationResult(e.getFieldMessage())
                .build();

        return new ResponseEntity<>(commonJsonResponse, HttpStatus.OK);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<CommonJsonResponse> handleException(DataNotFoundException e) {
        log.error("advice DataNotFoundException: {}", e.getFieldMessage());

        CommonJsonResponse commonJsonResponse = CommonJsonResponse.builder()
                .resultCode(610)
                .validationResult(e.getFieldMessage())
                .build();

        return new ResponseEntity<>(commonJsonResponse, HttpStatus.OK);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<CommonJsonResponse> handleException(SQLException e) {
        exceptionLogging(e);

        CommonJsonResponse commonJsonResponse = CommonJsonResponse.builder()
                .resultCode(500)
                .resultMessage(CommonConstants.SYSTEM_ERROR)
                .build();

        return new ResponseEntity<>(commonJsonResponse, HttpStatus.OK);
    }

    @ExceptionHandler(InterfaceException.class)
    public ResponseEntity<InterfaceJsonResponse> handleException(InterfaceException e) {
        String message;

        if (StringUtils.hasText(e.getErrorMessage())) {
            message = e.getErrorMessage();
        } else {
            message = e.getMessageArray().toString() + CommonConstants.MessageEnum.THERE_ARE_NO.getValue();
        }

        log.error("advice InterfaceException: trackingId {}, {}", e.getTrackingId(), message);

        InterfaceJsonResponse interfaceJsonResponse = InterfaceJsonResponse.builder()
                .trackingId(e.getTrackingId())
                .resultCode(9)
                .resultMessage(message)
                .build();

        return new ResponseEntity<>(interfaceJsonResponse, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonJsonResponse> handleException(Exception e) {
        exceptionLogging(e);

        CommonJsonResponse commonJsonResponse = CommonJsonResponse.builder()
                .resultCode(500)
                .resultMessage(CommonConstants.SYSTEM_ERROR)
                .build();

        return new ResponseEntity<>(commonJsonResponse, HttpStatus.OK);
    }

    private void exceptionLogging(Exception exception) {
        log.error("The controller advicer exception message is {} ", exception.getMessage());
    }
}
