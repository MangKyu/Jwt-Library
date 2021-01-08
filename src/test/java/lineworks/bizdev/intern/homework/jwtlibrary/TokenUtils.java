package lineworks.bizdev.intern.homework.jwtlibrary;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.SignWith;
import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lineworks.bizdev.intern.homework.mylibs.jwt.result.GeneratedJwts;
import lineworks.bizdev.intern.homework.mylibs.jwt.result.Jwts;
import lineworks.bizdev.intern.homework.mylibs.jwt.utils.JwtGenerator;

public final class TokenUtils {

	private static final String secretKey = "!@test!@";

	public static Jwts hmac(Long id) {
		return GeneratedJwts.builder()
			.claims(createClaims(id))
			.expiredAt(DateUtils.expireDateForOneHour())
			.signWith(SignWith.builder().encryptAlgorithm(EncryptAlgorithm.HS256).key(JwtGenerator.generateSecretKey(secretKey)).build()).build();
	}

	public static Jwts rsa(String serverId, String keyPath, Long id) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return GeneratedJwts.builder()
			.subject(serverId)
			.claims(createClaims(id))
			.expiredAt(DateUtils.expireDateForOneMonth())
			.signWith(SignWith.builder().encryptAlgorithm(EncryptAlgorithm.RS256).key(JwtGenerator.generatePrivateKey(keyPath)).build()).build();
	}

	public static Map<String, Object> createClaims(Long sid) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sid", sid);
		return claims;
	}

}
