package lineworks.bizdev.intern.homework.mylibs.jwt.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncryptAlgorithm {

	HS256("HS256", "SHA-256", 64, false),
	HS384("HS384", "SHA-384", 128, false),
	HS512("HS512", "SHA-512", 128, false),
	RS256("RS256", "SHA256withRSA", 64, true),
	RS384("RS384", "SHA384withRSA", 128, true),
	RS512("RS512", "SHA512withRSA", 128, true);

	private final String jwtName; // JWT 표준에 따라 헤더에 포함되는 알고리즘 명칭.
	private final String hashName; // MessageDigest 에게 넘겨주기 위한 인자값.
	private final int blockSize; // 해쉬 함수가 사용하는 블럭 크기. Byte 단위로 나타낼 것.
	private final boolean rsa; // RSA 방식인지 나타내는 불린.

}
