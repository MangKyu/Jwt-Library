package lineworks.bizdev.intern.homework.mylibs.jwt.sign;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.Signatory;
import lineworks.bizdev.intern.homework.mylibs.jwt.constants.EncryptAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtHMAC implements Signatory {

	private final String digestType;
	private final int digestBlockSize;
	private final String key;

	public JwtHMAC(EncryptAlgorithm algorithm, String key) {
		this.digestType = algorithm.getHashName();
		this.digestBlockSize = algorithm.getBlockSize();
		this.key = key;
	}

	// HMAC spec에서 outerPadding 은 반복되는 0x5c로 정의되어 있음.
	private byte[] generateInnerPadding(int length) {
		byte[] result = new byte[length];
		for (int i = 0; i < length; i++) {
			result[i] = 0x36;
		}
		return result;
	}

	// HMAC spec에서 outerPadding 은 반복되는 0x5c로 정의되어 있음.
	private byte[] generateOuterPadding(int length) {
		byte[] result = new byte[length];
		for (int i = 0; i < length; i++) {
			result[i] = 0x5c;
		}
		return result;
	}

	// byte 배열 합성곱을 위한 함수
	private byte[] xorByteArrays(byte[] text1, byte[] text2) {
		int size = text1.length;

		byte[] result = new byte[size];

		for (int i = 0; i < size; i++) {
			result[i] = (byte)(text1[i] ^ text2[i]);
		}

		return result;
	}

	// byte 배열 접합을 위한 함수
	private byte[] joinByteArrays(byte[] text1, byte[] text2) {
		int length1 = text1.length;
		int length2 = text2.length;
		int joinedLength = length1 + length2;
		byte[] result = new byte[joinedLength];

		System.arraycopy(text1, 0, result, 0, length1);
		System.arraycopy(text2, 0, result, length1, length2);

		return result;
	}

	private byte[] generateHash(byte[] text) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(this.digestType);
		md.update(text);
		return md.digest();
	}

	private byte[] padText(byte[] text, int length) {
		byte[] result = new byte[length];
		int originLength = text.length;

		System.arraycopy(text, 0, result, 0, originLength);

		return result;
	}

	private byte[] normalizeKey(byte[] key) throws NoSuchAlgorithmException {
		if (this.digestBlockSize < key.length) {
			return generateHash(key);
		}
		return padText(key, this.digestBlockSize);
	}

	public byte[] generateSign(byte[] key, byte[] message) throws NoSuchAlgorithmException {
		byte[] normalizedKey = normalizeKey(key);
		byte[] outerPad = generateOuterPadding(this.digestBlockSize);
		byte[] innerPad = generateInnerPadding(this.digestBlockSize);

		byte[] token = generateHash(
			joinByteArrays(
				xorByteArrays(normalizedKey, innerPad),
				message
			)
		);

		token = generateHash(
			joinByteArrays(
				xorByteArrays(normalizedKey, outerPad),
				token
			)
		);

		return token;
	}

	@Override
	public byte[] generateSign(String message) throws NoSuchAlgorithmException {
		return generateSign(key.getBytes(), message.getBytes());
	}

}
