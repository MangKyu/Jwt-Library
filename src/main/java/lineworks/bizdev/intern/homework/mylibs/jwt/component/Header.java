package lineworks.bizdev.intern.homework.mylibs.jwt.component;

import static lineworks.bizdev.intern.homework.mylibs.jwt.constants.TokenConstants.*;

import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Header {

	private final String alg;
	private final String typ;

	@Builder
	public Header(EncryptAlgorithm encryptAlgorithm) {
		alg = encryptAlgorithm.getJwtName();
		typ = JWT_TOKEN_TYPE;
	}

}