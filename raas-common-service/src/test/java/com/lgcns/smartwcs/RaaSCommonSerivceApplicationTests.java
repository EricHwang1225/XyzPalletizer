package com.lgcns.smartwcs;

import com.lgcns.smartwcs.common.client.MailClient;
import com.lgcns.smartwcs.common.utils.AES256;
import com.lgcns.smartwcs.common.utils.CommonUtils;
import com.lgcns.smartwcs.common.utils.SaltUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class RaaSCommonSerivceApplicationTests {
    @Autowired
    private SaltUtil saltUtil;
    @Autowired
    private AES256 aes256;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Test
    void test1() {
        String password = "!Q2w3e4r5t"; // !Q2w3e4r5t
        String salt = "ded0367d94652f5e";
        String encryptedPassword = saltUtil.encryptPassword(password, salt);

        //  !Q2w3e4r5t
        boolean result = saltUtil.validatePassword(password, salt, encryptedPassword);
        Assert.assertTrue(result);
    }

    @Test
    void aes256Test() throws Exception {
        String textEmail = "aaa@gmail.com";
        String encodedEmail = aes256.encrypt(textEmail);
        String decodedEmail = aes256.decrypt(encodedEmail);
        System.out.println(textEmail);
        System.out.println(encodedEmail);

        Assert.assertEquals(textEmail, decodedEmail);

    }

    @Test
    void randomPasswordTest() {
        String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()%–[{}]:;',.?/*~$^+=<>]).{8,16}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

        for (int i = 0; i < 10; ++i) {
            String password = CommonUtils.generateRandomPassword();
            System.out.println(password);

            Matcher matcher = pattern.matcher(password);
            Assert.assertTrue(matcher.matches());
        }
    }

    @Test
    void passwordTest() throws Exception {
        String password = CommonUtils.generateRandomPassword();
        String salt = saltUtil.generateSalt();
        String encryptedPassword = saltUtil.encryptPassword(password, salt);

        //  !Q2w3e4r5t
        boolean result = saltUtil.validatePassword(password, salt, encryptedPassword);
        Assert.assertTrue(result);
    }

}
