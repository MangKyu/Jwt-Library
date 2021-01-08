package lineworks.bizdev.intern.homework.jwtlibrary;

import static org.junit.Assert.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Test;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.SignWith;
import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lineworks.bizdev.intern.homework.mylibs.jwt.utils.JwtGenerator;
import lineworks.bizdev.intern.homework.mylibs.jwt.utils.JwtValidator;

public class JwtGenerateTest {

	@Test
	public void RS256Success() throws NoSuchAlgorithmException, InvalidKeySpecException,
		SignatureException, InvalidKeyException {
		String keyPath = "target/test-classes/temp.key";
		String token1 = TokenUtils.rsa("a", keyPath, 0L).generate();
		assertTrue(JwtValidator.isValidToken(token1, SignWith.builder()
			.encryptAlgorithm(EncryptAlgorithm.RS256).key(JwtGenerator.generatePrivateKey(keyPath))
			.build()));
	}

	@Test
	public void HS256Success() throws NoSuchAlgorithmException, SignatureException,
		InvalidKeyException {
		String token1 = TokenUtils.hmac(0L).generate();
		assertTrue(JwtValidator.isValidToken(token1, SignWith.builder()
			.encryptAlgorithm(EncryptAlgorithm.HS256).key(JwtGenerator.generateSecretKey("!@test!@"))
			.build()));
	}

	@Test
	public void RS256Fail() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String keyPath = "target/test-classes/temp.key";
		String token1 = "wrong.Token.asasaps";
		assertFalse(JwtValidator.isValidToken(token1, SignWith.builder()
			.encryptAlgorithm(EncryptAlgorithm.RS256).key(JwtGenerator.generatePrivateKey(keyPath))
			.build()));
	}

	@Test
	public void HS256Fail() {
		String token1 = "wrong.Token.asasaps";
		assertFalse(JwtValidator.isValidToken(token1, SignWith.builder()
			.encryptAlgorithm(EncryptAlgorithm.HS256).key(JwtGenerator.generateSecretKey("!@test!@"))
			.build()));
	}

}
