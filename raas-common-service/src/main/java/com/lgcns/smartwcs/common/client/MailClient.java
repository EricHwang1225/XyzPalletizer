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
        stringBuilder.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "    <head>\n" +
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "        <title>Singlex RaaS issued a temporary password.</title>\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "      </head>\n" +
                "      <body>\n" +
                "            <!-- 회색 배경 -->\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgColor=\"#F4F5F7\" style=\"padding: 20px 16px 82px; color: #191919; font-family: 'Noto Sans KR', sans-serif;\">\n" +
                "                <tbody style=\"display: block; margin: 0 auto;\">\n" +
                "                <tr width=\"100%\" style=\"display: block;\">\n" +
                "                    <td width=\"100%\" style=\"display: block;\">\n" +
                "                    <!-- 본문 -->\n" +
                "                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgColor=\"#FFFFFF\" style=\"width:600px; display: inline-block; padding: 32px; text-align: left; border-top: 3px solid #222; border-collapse: collapse;\">\n" +
                "                        <tbody style=\"display: block;\">\n" +
                "                        <tr>\n" +
                "                            <td style=\"padding-bottom: 40px; font-size: 20px; font-weight: bold;\">\n");
        stringBuilder.append(String.format("%s,", name));
        stringBuilder.append("                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"padding-bottom:30px;\">Your account has been created or reset. Please change your password using the temporary password.</td>\n" +
                "                        </tr>\n" +
                "                        <tr width=\"100%\" style=\"display: block; margin-bottom: 40px;\">\n" +
                "                            <td width=\"100%\" style=\"display: block;\">\n" +
                "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgColor=\"#F8F9FA\" style=\"border-radius: 4px; width:540px;\">\n" +
                "                                    <tbody>\n" +
                "                                        <tr width=\"100%;\">\n" +
                "                                            <td style=\"font-weight: bold; border-bottom:1px solid #ddd; background-color:#eee;\">ID</td>\n");
        stringBuilder.append(String.format("<td style=\"border-bottom:1px solid #ddd; border-left:1px solid #ddd;\">%s</td>", ID));
        stringBuilder.append("               </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"font-weight: bold; border-bottom:1px solid #ddd; background-color:#eee;\">Temporary Password</td>\n");
        stringBuilder.append(String.format("<td style=\"border-bottom:1px solid #ddd; border-left:1px solid #ddd;\">%s</td>", randomPassword));
        stringBuilder.append("                </tr>\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"font-weight: bold; background-color:#eee;\">Site</td>\n" +
                "                                            <td style=\" border-left:1px solid #ddd;\"><a href=\"raas.singlex.com\" target=_blank>Singlex RaaS</a></td>\n" +
                "                                        </tr>\n" +
                "                                    </tbody>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"padding-bottom: 24px;\">For questions or problems, please contact <br />the RaaS Service (<a href=\"mailto:raas@lgcns.com\">raas@lgcns.com</a>).</td>\n" +
                "                        </tr>\n" +
                "                        <tr width=\"100%\" style=\"display:block; padding-top: 24px; border-top: 1px solid #e9e9e9;\">\n" +
                "                            <td style=\"position: relative;\">\n" +
                "                                Best regards,<br /> RaaS Support\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "      </body>\n" +
                "</html>\n" +
                "\n");
        String html_body = stringBuilder.toString();

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
