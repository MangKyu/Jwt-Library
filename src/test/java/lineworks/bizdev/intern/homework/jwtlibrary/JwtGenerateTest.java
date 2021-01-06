package lineworks.bizdev.intern.homework.jwtlibrary;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Test;

public class JwtGenerateTest {

	@Test
	public void generateRS256Token() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException,
		SignatureException, InvalidKeyException {
		String keyPath = "target/test-classes/temp.key";
		String token1 = TokenUtils.rsa("a", keyPath, 1L).generate();
		assertTrue(TokenUtils.isValidToken(token1, keyPath));
	}

	@Test
	public void generateHS256Token() throws NoSuchAlgorithmException, IOException, SignatureException,
		InvalidKeyException, InvalidKeySpecException {
		String token1 = TokenUtils.hmac(1L).generate();
		assertTrue(TokenUtils.isValidToken(token1));
	}

}
