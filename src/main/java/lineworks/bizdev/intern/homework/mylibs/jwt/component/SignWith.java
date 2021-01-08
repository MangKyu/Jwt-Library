package lineworks.bizdev.intern.homework.mylibs.jwt.component;

import java.security.Key;

import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignWith {

	private final EncryptAlgorithm encryptAlgorithm;
	private final Key key;

	@Builder
	public SignWith(EncryptAlgorithm encryptAlgorithm, Key key) {
		this.encryptAlgorithm = encryptAlgorithm;
		this.key = key;
	}

}
