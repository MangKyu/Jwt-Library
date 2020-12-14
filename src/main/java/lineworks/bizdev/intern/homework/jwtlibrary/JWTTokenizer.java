package lineworks.bizdev.intern.homework.jwtlibrary;

// WORKS MOBILE 2020 하반기 Internship
// 작성자 : 김동헌

import com.google.gson.Gson;
import lineworks.bizdev.intern.homework.jwtlibrary.dto.JWTClaimSet;
import lineworks.bizdev.intern.homework.jwtlibrary.dto.JWTHeader;
import java.security.PrivateKey;
import java.util.Base64;

public class JWTTokenizer {

    private final boolean rsa;
    private final JWTHeader jwtHeader;
    private final String jwtHeaderBase64;

    private final Gson gson = new Gson();
    private final Base64.Encoder encoder = Base64.getEncoder();

    private JWTSignatory jwtSignatory;

    public JWTTokenizer(JWTAlgorithm algorithm, String key) throws Exception {
        if (algorithm.isRsa()) {
            throw new Exception("Wrong key type. RSA requires private-key of RSA key.");
        }

        rsa = algorithm.isRsa();
        jwtHeader = new JWTHeader(algorithm.getJwtName());
        JWTHMAC jwtHmac = new JWTHMAC(algorithm);
        jwtHmac.setKey(key);
        jwtSignatory = jwtHmac;

        jwtHeaderBase64 = encodeWithBase64(gson.toJson(jwtHeader));
    }

    public JWTTokenizer(JWTAlgorithm algorithm, PrivateKey key) throws Exception {
        if (!algorithm.isRsa()) {
            throw new Exception("Wrong key type. HMAC SHA-2 requires a pre-shared key.");
        }

        rsa = algorithm.isRsa();
        jwtHeader = new JWTHeader(algorithm.getJwtName());
        JWTRSASSA JWTRSASSA = new JWTRSASSA(algorithm);
        JWTRSASSA.setKey(key);
        jwtSignatory = JWTRSASSA;

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

    public String generateClaimSetToken(String serverId) throws Exception {
        JWTClaimSet claimSet = new JWTClaimSet(serverId);

        return generateToken(claimSet);
    }

}
