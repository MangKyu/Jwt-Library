package lineworks.bizdev.intern.homework.jwtlibrary;

// WORKS MOBILE 2020 하반기 Internship
// 작성자 : 김동헌

import lombok.Setter;

import java.security.PrivateKey;
import java.security.Signature;

@Setter
public class JwtRsaSha implements JwtSignatory {

    private final String pkcsType;
    private PrivateKey key;

    JwtRsaSha(JwtAlgorithm jwtAlgorithm) {
        pkcsType = jwtAlgorithm.getHashName();
    }

    @Override
    public byte[] generateSign(String message) throws Exception{
        Signature rsaShaSignature = Signature.getInstance(pkcsType);
        rsaShaSignature.initSign(key);
        rsaShaSignature.update(message.getBytes());

        return rsaShaSignature.sign();
    }
}
