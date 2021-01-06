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
import lombok.extern.log4j.Log4j2;

@Getter
@RequiredArgsConstructor
@Log4j2
public class SignWith {

	private final EncryptAlgorithm encryptAlgorithm;
	private final Object key;
	private final boolean rsa;

	@Builder
	public SignWith(EncryptAlgorithm encryptAlgorithm, Object key, String keyPath) throws
		NoSuchAlgorithmException, InvalidKeySpecException {
		this.encryptAlgorithm = encryptAlgorithm;
		this.key = keyPath == null ? key : readPrivateKey(keyPath);
		rsa = encryptAlgorithm.isRsa();
	}

	private PrivateKey readPrivateKey(String keyPath) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PrivateKey key = null;
		FileInputStream fis = null;
		DataInputStream dis = null;

		try {
			File file = new File(keyPath);
			fis = new FileInputStream(file);
			dis = new DataInputStream(fis);
			byte[] keyBytes = new byte[(int)file.length()];
			dis.readFully(keyBytes);

			String temp = new String(keyBytes);
			String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----", "");
			privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");

			byte[] decoded = DatatypeConverter.parseBase64Binary(privKeyPEM);

			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			key = kf.generatePrivate(spec);
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (dis != null) {
					dis.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return key;
	}

}
