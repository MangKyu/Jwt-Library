package lineworks.bizdev.intern.homework.mylibs.jwt.utils;

import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.Header;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.SignWith;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.Signatory;
import lineworks.bizdev.intern.homework.mylibs.jwt.constants.TokenConstants;
import lineworks.bizdev.intern.homework.mylibs.jwt.exception.InvalidTokenException;
import lineworks.bizdev.intern.homework.mylibs.jwt.exception.TokenExpiredException;
import lineworks.bizdev.intern.homework.mylibs.jwt.result.Jwts;
import lineworks.bizdev.intern.homework.mylibs.jwt.result.ParsedJwts;

public final class JwtParser {

	private static final Gson gson = new Gson();

	private JwtParser() {
		throw new IllegalStateException("Utility Class");
	}

	public static Jwts parseToken(String token, SignWith signWith) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		String[] strings = token.split("\\.");
		Map<String, Object> claims = getClaims(strings[1]);

		Jwts parsedJwts = ParsedJwts.builder()
			.header(getHeader(strings[0]))
			.claims(claims)
			.expiredAt(getExpiredAt(claims))
			.signWith(signWith).build();

		checkValidity(parsedJwts, token);
		return parsedJwts;
	}

	public static boolean isValidToken(String token, String body, Signatory signatory) throws NoSuchAlgorithmException, InvalidKeyException,
		SignatureException {

		String generatedToken = JwtGenerator.generate(body, signatory);
		if (!generatedToken.equals(token)) {
			throw new InvalidTokenException(generatedToken);
		}
		Map<String, Object> claims = getClaims(generatedToken.split("\\.")[1]);
		Date expiredAt = getExpiredAt(claims);
		Date now = Date.from(Calendar.getInstance().toInstant());

		if (now.after(expiredAt)) {
			throw new TokenExpiredException("Token Expired");
		}
		return true;
	}

	private static void checkValidity(Jwts jwts, String token) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		String generatedToken = JwtGenerator.generate(jwts);
		if (!generatedToken.equals(token)) {
			throw new InvalidTokenException(generatedToken);
		}
	}

	public static Header getHeader(String input) {
		String decodedText = decodeWithBase64(input);
		return gson.fromJson(decodedText, Header.class);
	}

	public static Map<String, Object> getClaims(String input) {
		String decodedText = decodeWithBase64(input);
		Type type = new TypeToken<HashMap<String, Object>>() {
		}.getType();
		return gson.fromJson(decodedText, type);
	}

	private static Date getExpiredAt(Map<String, Object> map) {
		long time = getExpiredTime(map);
		return new Date(time * 1000);
	}

	private static long getExpiredTime(Map<String, Object> map) {
		return ((Double)map.get(TokenConstants.CLAIMS_EXPIRED_AT)).longValue();
	}

	private static String decodeWithBase64(String text) {
		byte[] decodedText = Base64.getDecoder().decode(text);
		return new String(decodedText);
	}

}
