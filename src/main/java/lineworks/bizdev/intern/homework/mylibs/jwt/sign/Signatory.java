package lineworks.bizdev.intern.homework.mylibs.jwt.sign;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface Signatory {

	byte[] sign(String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;

}
