package lineworks.bizdev.intern.homework.mylibs.jwt.utils;

import static lineworks.bizdev.intern.homework.mylibs.jwt.constants.TokenConstants.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import com.google.gson.Gson;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.Header;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.Signatory;
import lineworks.bizdev.intern.homework.mylibs.jwt.result.Jwts;

public final class JwtGenerator {

	private static final Gson gson = new Gson();

	public static String generate(Jwts jwts) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		addExpiredAt(jwts.getClaims(), jwts.getExpiredAt());
		StringBuilder sb = new StringBuilder();
		sb.append(getEncodedJsonHeader(jwts.getHeader())).append(".")
			.append(getEncodedJsonClaims(jwts.getClaims()));
		String jsonSign = getEncodedJsonSign(jwts.getSignatory(), sb.toString());
		sb.append(".").append(jsonSign);
		return sb.toString();
	}

	public static String generate(String body, Signatory signatory) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		StringBuilder sb = new StringBuilder(body);
		String jsonSign = getEncodedJsonSign(signatory, body);
		sb.append(".").append(jsonSign);
		return sb.toString();
	}

	private static String getEncodedJsonSign(Signatory signatory, String body) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		byte[] sign = signatory.generateSign(body);
		return encodeWithBase64(sign);
	}

	private static String getEncodedJsonHeader(Header header) {
		String jsonHeader = gson.toJson(header);
		return encodeWithBase64(jsonHeader);
	}

	private static String encodeWithBase64(String text) {
		return encodeWithBase64(text.getBytes());
	}

	private static String encodeWithBase64(byte[] text) {
		byte[] encodedText = Base64.getEncoder().encode(text);

		return new String(encodedText)
			.replace('+', '-')
			.replace('/', '_')
			.replace("=", "");  //이 3행은 Base64를 Base64URL로 변환합니다.
	}

	private static String getEncodedJsonClaims(Object object) {
		String jsonPayload = gson.toJson(object);
		return encodeWithBase64(jsonPayload);
	}

	private static void addExpiredAt(Map<String, Object> claims, Date expiredAt) {
		claims.put(CLAIMS_EXPIRED_AT, expiredAt.getTime() / 1000);
	}

}