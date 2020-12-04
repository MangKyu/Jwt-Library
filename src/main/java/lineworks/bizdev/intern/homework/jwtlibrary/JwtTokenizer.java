package lineworks.bizdev.intern.homework.jwtlibrary;

// WORKS MOBILE 2020 하반기 Internship
// 작성자 : 김동헌

import com.google.gson.Gson;
import lineworks.bizdev.intern.homework.jwtlibrary.dto.JwtHeader;
import java.security.PrivateKey;
import java.util.Base64;

public class JwtTokenizer {

    private final boolean rsa;
    private final JwtHeader jwtHeader;
    private final String jwtHeaderBase64;

    private final Gson gson = new Gson();
    private final Base64.Encoder encoder = Base64.getEncoder();

    private JwtSignatory jwtSignatory;

    public JwtTokenizer(JwtAlgorithm algorithm, String key) throws Exception {
        if (algorithm.isRsa()) {
            throw new Exception("Wrong key type. RSA requires private-key of RSA key.");
        }

        rsa = algorithm.isRsa();
        jwtHeader = new JwtHeader(algorithm.getJwtName());
        JwtHmac jwtHmac = new JwtHmac(algorithm);
        jwtHmac.setKey(key);
        jwtSignatory = jwtHmac;

        jwtHeaderBase64 = encodeWithBase64(gson.toJson(jwtHeader));
    }

    public JwtTokenizer(JwtAlgorithm algorithm, PrivateKey key) throws Exception {
        if (!algorithm.isRsa()) {
            throw new Exception("Wrong key type. HMAC SHA-2 requires a pre-shared key.");
        }

        rsa = algorithm.isRsa();
        jwtHeader = new JwtHeader(algorithm.getJwtName());
        JwtRsaSha jwtRsaSha = new JwtRsaSha(algorithm);
        jwtRsaSha.setKey(key);
        jwtSignatory = jwtRsaSha;

        jwtHeaderBase64 = encodeWithBase64(gson.toJson(jwtHeader));
    }

    private String encodeWithBase64(String text) {
        return encodeWithBase64(text.getBytes());
    }

    private String encodeWithBase64(byte[] text) {
        byte[] encodedText = encoder.encode(text);

        return new String(encodedText)
                .replace('+','-')
                .replace('/','_')
                .replace("=", "");  //이 3행은 Base64를 Base64URL로 변환합니다.
    }

    private String generatePayloadBase64(Object object) {
        String jsonPayload = gson.toJson(object);
        return encodeWithBase64(jsonPayload);
    }

    public String generateToken(Object object) throws Exception {
        String body = jwtHeaderBase64 + "." + generatePayloadBase64(object);
        byte[] sign = jwtSignatory.generateSign(body);
        String signBase64 = encodeWithBase64(sign);

        return body + "." + signBase64;
    }

}
