package lineworks.bizdev.intern.homework.jwtlibrary;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.SignWith;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.Signatory;
import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lineworks.bizdev.intern.homework.mylibs.jwt.result.GeneratedJwts;
import lineworks.bizdev.intern.homework.mylibs.jwt.result.Jwts;
import lineworks.bizdev.intern.homework.mylibs.jwt.sign.JwtHMAC;
import lineworks.bizdev.intern.homework.mylibs.jwt.sign.JwtRSASSA;
import lineworks.bizdev.intern.homework.mylibs.jwt.utils.JwtParser;

public final class TokenUtils {

	private static final String secretKey = "!@test!@";

	public static Jwts hmac(Long id) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
		return GeneratedJwts.builder().expiredAt(DateUtils.expireDateForOneHour()).claims(createClaims(id))
			.signWith(SignWith.builder().encryptAlgorithm(EncryptAlgorithm.HS256).key(secretKey).build()).build();
	}

	public static Jwts rsa(String serverId, String keyPath, Long id) throws
		NoSuchAlgorithmException,
		IOException,
		InvalidKeySpecException {
		return GeneratedJwts.builder().expiredAt(DateUtils.expireDateForOneMonth()).claims(createClaims(serverId, id))
			.signWith(SignWith.builder().encryptAlgorithm(EncryptAlgorithm.RS256).keyPath(keyPath).build()).build();
	}

	public static boolean isValidToken(String token) {
		return isValidToken(token, null);
	}

	public static boolean isValidToken(String token, String keyPath) {
		boolean isValid = false;
		EncryptAlgorithm algorithm = keyPath == null ? EncryptAlgorithm.HS256 : EncryptAlgorithm.RS256;

		try {
			String body = token.substring(0, token.lastIndexOf("."));
			Signatory signatory = generateSignatory(algorithm, keyPath);
			isValid = JwtParser.isValidToken(token, body, signatory);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return isValid;
	}

	public static Map<String, Object> createClaims(Long sid) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("iat", System.currentTimeMillis() / 1000);
		claims.put("sid", sid);
		return claims;
	}

	private static Map<String, Object> createClaims(String serverId, Long staffId) {
		Map<String, Object> claims = createClaims(staffId);
		claims.put("iss", serverId);
		return claims;
	}

	private static Signatory generateSignatory(EncryptAlgorithm algorithm, String keyPath) throws InvalidKeySpecException,
		NoSuchAlgorithmException, IOException {
		SignWith signWith = SignWith.builder().encryptAlgorithm(algorithm).keyPath(keyPath).key(secretKey).build();
		return signWith.isRsa()
			? new JwtRSASSA(signWith.getEncryptAlgorithm(), (PrivateKey)signWith.getKey())
			: new JwtHMAC(signWith.getEncryptAlgorithm(), (String)signWith.getKey());
	}

	private static String encode(byte[] bytes) {
		byte[] encodedText = Base64.getEncoder().encode(bytes);
		return new String(encodedText)
			.replace('+', '-')
			.replace('/', '_')
			.replace("=", "");  //이 3행은 Base64를 Base64URL로 변환합니다.
	}

	private static String decode(String text) {
		byte[] decodedText = Base64.getDecoder().decode(text);
		return new String(decodedText);
	}

}
