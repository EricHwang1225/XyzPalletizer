package com.lgcns.smartwcs.login.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
/**
 * <PRE>
 * 로그인 테스트를 위한 aop
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@Slf4j
@Aspect     // AOP 사용
@Component  // Bean 으로 등록
public class LoginAspect {

    // POST 매칭할경우
    // @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    // GET 매칭할경우
    // @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    /* controller 패키지에 포함된 public 메서드와 매칭 */
    @Pointcut("within(com.lgcns.smartwcs.login.controller.*)")
    public void onRequest() {
        // this is empty
    }

    /* Pointcut 과 매칭되는 메서드의 실행 전, 후에 실행
     *  @Around advice 는 꼭 proceed()가 필요하다. */
    @Around("onRequest()")
    public Object logAction(ProceedingJoinPoint joinPoint) throws Throwable{
        log.debug("logAction");
        Class<?> clazz = joinPoint.getTarget().getClass();
        Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
            return result;
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info(getRequestUrl(joinPoint, clazz));
            log.info("parameters" + params(joinPoint));
        }
        return result;
    }

    private String getRequestUrl(JoinPoint joinPoint, Class<?> clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
        String baseUrl = requestMapping.value()[0];

        String url = Stream.of( GetMapping.class, PutMapping.class, PostMapping.class,
                        PatchMapping.class, DeleteMapping.class, RequestMapping.class)
                .filter(mappingClass -> method.isAnnotationPresent(mappingClass))
                .map(mappingClass -> getUrl(method, mappingClass, baseUrl))
                .findFirst().orElse(null);
        return url;
    }

    /* httpMETHOD + requestURI 를 반환 */
    private String getUrl(Method method, Class<? extends Annotation> annotationClass, String baseUrl){
        Annotation annotation = method.getAnnotation(annotationClass);
        String[] value;
        String httpMethod = null;
        try {
            value = (String[])annotationClass.getMethod("value").invoke(annotation);
            httpMethod = (annotationClass.getSimpleName().replace("Mapping", "")).toUpperCase();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return String.format("%s %s%s", httpMethod, baseUrl, value.length > 0 ? value[0] : "") ;
    }

    /* printing request parameter or request body */
    private Map<String, Object> params(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(parameterNames[i], args[i]);
        }
        return params;
    }
}