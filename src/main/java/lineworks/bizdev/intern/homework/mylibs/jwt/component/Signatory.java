package lineworks.bizdev.intern.homework.mylibs.jwt.component;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface Signatory {

	byte[] generateSign(String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;

}
