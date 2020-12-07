package lineworks.bizdev.intern.homework.jwtsample;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lineworks.bizdev.intern.homework.jwtlibrary.JWTAlgorithm;
import lineworks.bizdev.intern.homework.jwtlibrary.JWTTokenizer;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class MainClass {

    public static void main(String[] args) throws Exception{
        MainClass mc = new MainClass();

        File file = mc.getFileFromResource("cert.key");

        String privateKeyPemString = new String(Files.readAllBytes(file.toPath()));
        System.out.println(privateKeyPemString);

        privateKeyPemString = privateKeyPemString.replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replace("\n", "");

        System.out.println(privateKeyPemString);

        System.out.println(privateKeyPemString);

        byte[] decodedPrivateKeyPem = Base64.decode(privateKeyPemString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivateKeyPem);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey prKey = kf.generatePrivate(keySpec);

        JWTTokenizer jwtTokenizer = new JWTTokenizer(JWTAlgorithm.RS512, prKey);
        SampleDto thisIsSample = new SampleDto();

        String result = jwtTokenizer.generateToken(thisIsSample);
        System.out.println(result);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);

        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }
}
