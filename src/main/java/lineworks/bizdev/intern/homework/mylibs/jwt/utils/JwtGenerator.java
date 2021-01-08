package lineworks.bizdev.intern.homework.mylibs.jwt.utils;

import static lineworks.bizdev.intern.homework.mylibs.jwt.constants.ClaimsConstants.*;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.Subject;
import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lineworks.bizdev.intern.homework.mylibs.jwt.result.Jwts;
import lineworks.bizdev.intern.homework.mylibs.jwt.sign.Signatory;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class JwtGenerator {

	private JwtGenerator() {
		throw new IllegalStateException("Utility Class");
	}

	public static String generate(Jwts jwts) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		StringBuilder sb = new StringBuilder();

		// init - Subject와 ExpiredAt을 Claims에 추가
		init(jwts.getSubject(), jwts.getExpiredAt(), jwts.getClaims());

		sb.append(DataConverter.convertHeader(jwts.getHeader())).append(".")
			.append(DataConverter.convertClaims(jwts.getClaims()));
		String signature = signInBody(sb.toString(), jwts.getSignatory());
		sb.append(".").append(signature);
		return sb.toString();
	}

	public static String generate(String input, Signatory signatory) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		String signature = signInBody(input, signatory);
		return input + "." + signature;
	}

	public static Key generatePrivateKey(String keyPath) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String keyPem = DataConverter.readKeyFromFile(keyPath);
		if (keyPem == null) {
			throw new NullPointerException("Key Not found in: " + keyPath);
		}

		byte[] decoded = DatatypeConverter.parseBase64Binary(keyPem);

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	public static Key generateSecretKey(String secretKey) {
		return new SecretKeySpec(secretKey.getBytes(), EncryptAlgorithm.HS256.getHashName());
	}

	private static void init(Subject subject, Date expiredAt, Map<String, Object> claims) {
		if (subject.getIssuer() != null) {
			claims.put(CLAIMS_ISSUER, subject.getIssuer());
		}
		claims.put(CLAIMS_ISSUE_AT, subject.getIssueAt());
		claims.put(CLAIMS_EXPIRED_AT, expiredAt.getTime() / 1000L);
	}

	private static String signInBody(String body, Signatory signatory) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		byte[] signed = signatory.sign(body);
		return DataConverter.encodeWithBase64(signed);
	}

}