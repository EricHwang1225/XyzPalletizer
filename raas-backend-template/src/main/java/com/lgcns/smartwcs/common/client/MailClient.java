package com.lgcns.smartwcs.common.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailClient {
    private final RestTemplate restTemplate;

    private final String requestUrl = "https://api.singlex.com/scs/mail/api/v1/ses/send";
    private final String RAAS_MAIL = "raas@lgcns.com";

    public ResponseEntity<String> sendPassword(String tntId, String name, String ID, String to, String randomPassword) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body><table cellpadding=\"10\" style=\"max-width:600pt; background-color:#B6D0E7;")
                .append(" font-family: Arial, Helvetica, Sans-Serif; font-size: 11pt;\"><tr><td colspan=\"2\">Dear %1$s,</p>")
                .append("</td></tr><tr><td width=\"20pt\"></td><td style=\"background-color: #FFFFFF;\"><p>Your account has ")
                .append("been created or reset. Please change your password using \n")
                .append("the temporary password.<p/><table><tr><td>ID:</td><td><b>%2$s")
                .append("</b></td></tr><tr><td>Temporary Password:</td><td><b>%3$s</b></td></tr><tr><td>Site:</td><td>")
                .append("<a href=\"raas.singlex.com\">Singlex RaaS</a></td></tr></table>\n")
                .append("<p>For questions or problems, please contact the RaaS Service(email ")
                .append("<a href=\"mailto:raas@lgcns.com\">raas@lgcns.com</a>).</p></td></tr><tr><td colspan=\"2\">")
                .append("Best regards,</td></tr><tr><td colspan=\"2\">RaaS Support</td></tr></table></body></html> ");
        String html_body = String.format(stringBuilder.toString(), name, ID, randomPassword);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("appId", "raas");
        params.add("tntId", tntId);
        params.add("servType", "mail");
        params.add("subject", "Singlex RaaS issued a temporary password.");
        params.add("from", RAAS_MAIL);
        params.add("to", to);
        params.add("bcc", RAAS_MAIL);
        return getStringResponseEntity(html_body, params);
    }

    public ResponseEntity<String> sendDataCheck(String logTitle, String logText) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("appId", "RaaS");
        params.add("tntId", "X0000");
        params.add("servType", "mail");
        params.add("subject", logTitle);
        params.add("from", RAAS_MAIL);
        params.add("to", "andrew.park@lgcns.com");
        return getStringResponseEntity(logText, params);
    }

    private ResponseEntity<String> getStringResponseEntity(String logText, MultiValueMap<String, Object> params) {
        params.add("html_body", logText);

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-apikey", "U3nGyZlRukal90vyk3GV3dGdfvNWZuuF");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return responseEntity;
    }
}
