package lineworks.bizdev.intern.homework.mylibs.jwt.sign;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.Signatory;
import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtRSASSA implements Signatory {

	private final String pkcsType;
	private final PrivateKey key;

	public JwtRSASSA(EncryptAlgorithm algorithm, PrivateKey key) {
		pkcsType = algorithm.getHashName();
		this.key = key;
	}

	@Override
	public byte[] generateSign(String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature rsaShaSignature = Signature.getInstance(pkcsType);
		rsaShaSignature.initSign(key);
		rsaShaSignature.update(message.getBytes());
		return rsaShaSignature.sign();
	}

}
