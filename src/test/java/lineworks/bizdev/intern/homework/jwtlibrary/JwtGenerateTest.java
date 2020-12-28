package lineworks.bizdev.intern.homework.jwtlibrary;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.SignWith;
import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lineworks.bizdev.intern.homework.mylibs.jwt.result.GeneratedJwts;
import lineworks.bizdev.intern.homework.mylibs.jwt.utils.JwtGenerator;
import sun.misc.BASE64Decoder;

public class JwtGenerateTest {

	private static final String SECRET_KEY = "ASADASDASD";

	@Test
	public void generateRS256Token() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException,
		SignatureException, InvalidKeyException, URISyntaxException {

		String token1 = generateRS256JwtTokenByCustomLib();
		String token2 = generateRS256JwtTokenByExternalLib();
		Assert.assertEquals(token2, token1);
	}

	@Test
	public void generateHS256Token() throws URISyntaxException, NoSuchAlgorithmException, IOException, SignatureException,
		InvalidKeyException, InvalidKeySpecException {

		String token1 = generateHS256JwtByCustomLib();
		String token2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI2NjQ0MmNhZTgxMjE0YzI4YWZjNWEwYTBkOTE5N2I5ZSIsImV4cCI6MTYwODk5OTcxNSwiaWF0IjoxNjA4OTk3OTE1fQ.Hwgc0oxmScR-6IagcJ-fnFtcDJQXSqoN88IBTHrlmaM";
		Assert.assertEquals(token2, token1);
	}

	private static String generateHS256JwtByCustomLib() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException,
		IOException, URISyntaxException {
		GeneratedJwts jwts = GeneratedJwts.builder()
			.claims(createClaims())
			.signWith(SignWith.builder().encryptAlgorithm(EncryptAlgorithm.HS256).key(SECRET_KEY).build()).build();
		return JwtGenerator.generate(jwts);
	}

	private String generateRS256JwtTokenByCustomLib() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException,
		IOException, URISyntaxException {
		GeneratedJwts jwts = GeneratedJwts.builder()
			.claims(createClaims())
			.signWith(SignWith.builder().encryptAlgorithm(EncryptAlgorithm.RS256).keyPath("temp.key").build()).build();
		return JwtGenerator.generate(jwts);
	}

	private String generateRS256JwtTokenByExternalLib() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
		JwtBuilder builder = Jwts.builder()
			.setHeader(createHeader())
			.setClaims(createClaims())
			.signWith(SignatureAlgorithm.RS256, readPrivateKey());
		return builder.compact();
	}

	private static Map<String, Object> createHeader() {
		Map<String, Object> header = new LinkedHashMap<>();

		header.put("alg", "RS256");
		header.put("typ", "JWT");

		return header;
	}

	private static Map<String, Object> createClaims() {
		// 비공개 클레임으로 사용자의 이메일을 설정, 세션처럼 정보를 넣고 빼서 쓸 수 있다.
		Map<String, Object> claims = new HashMap<>();
		claims.put("iss", "66442cae81214c28afc5a0a0d9197b9e");
		claims.put("exp", 1608999715);
		claims.put("iat", 1608997915);
		return claims;
	}

	private PrivateKey readPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File file = new File("src/test/resources/temp.key");
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int)file.length()];
		dis.readFully(keyBytes);
		dis.close();

		String temp = new String(keyBytes);
		String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----", "");
		privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");

		BASE64Decoder b64 = new BASE64Decoder();
		byte[] decoded = b64.decodeBuffer(privKeyPEM);

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

}
