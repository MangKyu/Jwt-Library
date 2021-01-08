package lineworks.bizdev.intern.homework.mylibs.jwt.sign;

import java.security.Key;

import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;

public abstract class JwtBase implements Signatory {

	final String type;
	final int blockSize;
	final Key key;

	public JwtBase(EncryptAlgorithm encryptAlgorithm, Key key) {
		this.type = encryptAlgorithm.getHashName();
		this.blockSize = encryptAlgorithm.getBlockSize();
		this.key = key;
	}

}
