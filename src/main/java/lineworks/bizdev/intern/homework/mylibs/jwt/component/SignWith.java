package lineworks.bizdev.intern.homework.mylibs.jwt.component;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sun.misc.BASE64Decoder;

@Getter
@RequiredArgsConstructor
public class SignWith {

	private final EncryptAlgorithm encryptAlgorithm;
	private final Object key;
	private final boolean rsa;
	private final String keyPath;

	@Builder
	public SignWith(EncryptAlgorithm encryptAlgorithm, Object key, String keyPath) throws
		NoSuchAlgorithmException, IOException, InvalidKeySpecException, URISyntaxException {
		this.encryptAlgorithm = encryptAlgorithm;
		this.keyPath = key != null ? keyPath : "/docs/temp.key";
		this.key = key != null ? key : readPrivateKey(keyPath);
		rsa = encryptAlgorithm.isRsa();
	}

	private PrivateKey readPrivateKey(String keyPath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, URISyntaxException {
		URL url = getClass().getClassLoader().getResource(keyPath);
		File file = new File(url.toURI());
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
