# backend

SmartWCS BackEnd Rest API Service

## Rest API Swagger UI
http://localhost:8080/swagger-ui.html

## Docker 환경 실행
현재 OS에 따라 환경이 다 다르게 작동하므로 docker로 mssql 설치 후 create-database.sql 의 내용을 적용해야 함
```bash
docker-compose -f docker-compose.yml up -d --build
./gradlew runDev
```


## Reference 

### Spring 관련

#### 공식 사이트

- https://spring.io/projects/spring-boot

- https://spring.io/projects/spring-data-jpa

- https://spring.io/projects/spring-security

- https://spring.io/projects/spring-session

- https://spring.io/projects/spring-batch

- https://start.spring.io/ - SpringBoot 프로젝트 만들기


### JPA

#### 공식 사이트

- https://querydsl.com/static/querydsl/4.4.0/reference/html_single/  ​- 4.4.0 영

- https://querydsl.com/static/querydsl/4.0.1/reference/ko-KR/html_single/  ​- 4.0.1 한



#### 참고 사이트

- https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa

- https://jdm.kr/blog/143

- https://www.baeldung.com/spring-jpa-like-queries

- http://honeymon.io/tech/2020/07/09/gradle-annotation-processor-with-querydsl.html  ​- QueryDSL APT 설

- https://jehuipark.github.io/java/my-sql-binary-reference



### QueryDSL

#### 공식사이트

- https://querydsl.com/


#### 참고 사이트

- https://velog.io/@kangsan/%EC%82%AC%EB%82%B4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-Dynamic-Query-%EC%A0%81%EC%9A%A9%EA%B8%B0
- https://shinsunyoung.tistory.com/56
- https://blog.naver.com/developer501/222465706058
- 


### MyBatis

#### 공식 사이트

- https://mybatis.org
- https://mybatis.org/mybatis-3/ko/
- https://mybatis.org/spring/ko/mappers.html
- http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/

#### 참고 사이트

- https://blog.naver.com/jigmini/222302304970
- https://blog.naver.com/6116949/222217218982
- https://sinna94.tistory.com/entry/MyBatis-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%A5%BC-%EC%82%BD%EC%9E%85%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95%EB%93%A4?category=696828  - parameterType
- https://mybatis.org/mybatis-3/ko/dynamic-sql.html  - Dynamic Query


### Swagger2

#### 공식 사이트

- https://swagger.io/

#### 참고 사이트

- https://kim-jong-hyun.tistory.com/49
- https://sharplee7.tistory.com/48
- https://wellbell.tistory.com/19 - Pageable 관련


### Lombok

- https://projectlombok.org



### Sample Site

- https://www.baeldung.com


### LogBack

#### 공식 사이트
- http://logback.qos.ch/

#### 참고 사이트
- https://goddaehee.tistory.com/206

### Spring Boot Schedule

#### 참고 사이트
- http://jmlim.github.io/spring/2018/11/27/spring-boot-schedule/


### RestTemplate

#### 참고사이트
- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html : API
- https://a1010100z.tistory.com/125
- https://stackoverflow.com/questions/29392422/how-can-i-tell-resttemplate-to-post-with-utf-8-encoding
- https://www.baeldung.com/rest-template
- https://www.wrapuppro.com/programing/view/iLVLziCsP4icNB6