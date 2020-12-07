package lineworks.bizdev.intern.homework.jwtlibrary;

import org.junit.Test;
import java.util.Base64;
import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import static org.junit.Assert.assertEquals;

public class JWTTokenizerTests {

    @Test
    public void JWTTokenizerRS256Test() throws Exception{

        File file = new File("src/test/resources/cert.key");

        String privateKeyPemString = new String(Files.readAllBytes(file.toPath()));

        privateKeyPemString = privateKeyPemString
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replace("\n", "")
                .replace("\r", "");


        byte[] decodedPrivateKeyPem = Base64.getDecoder().decode(privateKeyPemString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivateKeyPem);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey prKey = kf.generatePrivate(keySpec);

        JWTTokenizer jwtTokenizer = new JWTTokenizer(JWTAlgorithm.RS256, prKey);
        SampleDto thisIsSample = new SampleDto();

        String result = jwtTokenizer.generateToken(thisIsSample);

        assertEquals(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJoZWxsbyI6IndhIiwid29ybGQiOiJzYW5zIiwicG9pbnQiOjF9.JI-l5xj4Gi1kP3EOv7VMgXCtCxmLrMeG3ZmMEapd-NHqsqJng5_5MSdZjKYw8FWgzdmocW8BLFQE6hjBRIwEEYnt1uTTxhGTu-y20lKSW2ebUlfybLYrtLzPUlvdCYjegJ9GILR7YvbuXolB9x9g-9HWykK6f-dINHx-w04Bc2gd6s4duJi83M2b7U7ZM8WhX3R9_pWL4M4op75Y-p4CqsBSKbl59pymB60ILJXGMRxCFqgzt524RFWWARcuDvy8E76YLWCfxEYxNWc8wSYafbBZEDUoy_h_-ye_w1FACZe6WeEH_4K5hnGVgaW4Df3PjTOHcsWdnz-PRicHvy5__QLl5EcEx5bRluEfXh5SBfxSXy-_D6NDV-rQC2zTKLdMG2py6dGI2eEsPJOYbugO0J30YXXO1KzXl9ScN1-h3czrUna_Xkf6bFTxEYXmMTzQjw45PSdkmbYsgw3JZJstGCzcAm67GS-67W-PHHmlRnqOuAahcS_LOtqTf4MlTsXTgTH8ISnM80gIjmT_4PNtbQMWhP_wAZSx0AXxAGjDZ-4P8c6UQbck6e3UlnGJ0JyaZ163WXBFnG6p4UC9ZJLRQgnr8MZvpo_TN7jwR0ZeCLxx-gt2OwCfnlOn-S6DJE_-2wYDcawo8H9z6fswS3mShq_CAl6OhO5fHfFLfUN7bB0",
                result);

    }

    @Test
    public void JWTTokenizerHS256Test() throws Exception{

        JWTTokenizer jwtTokenizer = new JWTTokenizer(JWTAlgorithm.HS256, "Hello JWT#123");
        SampleDto thisIsSample = new SampleDto();

        String result = jwtTokenizer.generateToken(thisIsSample);

        System.out.println(result);
        assertEquals(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJoZWxsbyI6IndhIiwid29ybGQiOiJzYW5zIiwicG9pbnQiOjF9.2AwLVt_c_qBeazjHwrmsqNcjXqWNHSwfBqwmFcs4dnM",
                result);

    }

}
