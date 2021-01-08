package lineworks.bizdev.intern.homework.mylibs.jwt.sign;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;

public final class JwtRSASSA extends JwtBase {

	public JwtRSASSA(EncryptAlgorithm algorithm, Key key) {
		super(algorithm, key);
	}

	@Override
	public byte[] sign(String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature rsaShaSignature = Signature.getInstance(this.type);
		rsaShaSignature.initSign((PrivateKey)this.key);
		rsaShaSignature.update(message.getBytes());
		return rsaShaSignature.sign();
	}

}
