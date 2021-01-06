package lineworks.bizdev.intern.homework.mylibs.jwt.component;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.xml.bind.DatatypeConverter;

import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignWith {

	private final EncryptAlgorithm encryptAlgorithm;
	private final Object key;
	private final boolean rsa;

	@Builder
	public SignWith(EncryptAlgorithm encryptAlgorithm, Object key, String keyPath) throws
		NoSuchAlgorithmException, IOException, InvalidKeySpecException {
		this.encryptAlgorithm = encryptAlgorithm;
		this.key = keyPath == null ? key : readPrivateKey(keyPath);
		rsa = encryptAlgorithm.isRsa();
	}

	private PrivateKey readPrivateKey(String keyPath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File file = new File(keyPath);
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int)file.length()];
		dis.readFully(keyBytes);
		dis.close();

		String temp = new String(keyBytes);
		String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----", "");
		privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");

		byte[] decoded = DatatypeConverter.parseBase64Binary(privKeyPEM);

		fis.close();
		dis.close();

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

}
