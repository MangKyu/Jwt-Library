package lineworks.bizdev.intern.homework.mylibs.jwt.component;

import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lombok.Getter;

@Getter
public class Header {

	private final String alg;
	private final String typ;

	public Header(EncryptAlgorithm encryptAlgorithm) {
		this.alg = encryptAlgorithm.getJwtName();
		this.typ = "JWT";
	}

}