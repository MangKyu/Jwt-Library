package lineworks.bizdev.intern.homework.mylibs.jwt.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.Header;
import lineworks.bizdev.intern.homework.mylibs.jwt.constants.ClaimsConstants;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class DataConverter {

	private static final Gson gson = new Gson();

	private DataConverter() {
		throw new IllegalStateException("Utility Class");
	}

	public static String convertHeader(Header header) {
		String jsonHeader = gson.toJson(header);
		return encodeWithBase64(jsonHeader);
	}

	public static String convertClaims(Map<String, Object> claims) {
		String jsonPayload = gson.toJson(claims);
		return encodeWithBase64(jsonPayload);
	}

	public static Date convertToExpiredAt(String token) {
		Map<String, Object> claims = convertToClaims(token.split("\\.")[1]);
		long time = ((Double)claims.get(ClaimsConstants.CLAIMS_EXPIRED_AT)).longValue();
		return new Date(time * 1000);
	}

	public static String encodeWithBase64(byte[] text) {
		byte[] encodedText = Base64.getEncoder().encode(text);

		return new String(encodedText)
			.replace('+', '-')
			.replace('/', '_')
			.replace("=", "");  //이 3행은 Base64를 Base64URL로 변환합니다.
	}

	public static String readKeyFromFile(String keyPath) {
		File file = new File(keyPath);
		String keyPem = null;
		try (
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis)
		) {
			byte[] keyBytes = new byte[(int)file.length()];
			dis.readFully(keyBytes);

			keyPem = keyBytesToKey(keyBytes);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return keyPem;
	}

	private static String encodeWithBase64(String text) {
		return encodeWithBase64(text.getBytes());
	}

	private static String decodeWithBase64(String text) {
		byte[] decodedText = Base64.getDecoder().decode(text);
		return new String(decodedText);
	}

	private static String keyBytesToKey(byte[] keyBytes) {
		String originKey = new String(keyBytes);
		return originKey.replace("-----BEGIN PRIVATE KEY-----", "")
			.replace("-----END PRIVATE KEY-----", "");
	}

	public static Map<String, Object> convertToClaims(String body) {
		String decodedText = decodeWithBase64(body);
		Type type = new TypeToken<HashMap<String, Object>>() {
		}.getType();
		return gson.fromJson(decodedText, type);
	}

}
