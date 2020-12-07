package lineworks.bizdev.intern.homework.jwtlibrary;

// WORKS MOBILE 2020 하반기 Internship
// 작성자 : 김동헌

import lombok.Setter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Setter
public class JWTHMAC implements JWTSignatory {

    private final String digestType;
    private final int digestBlockSize;
    private String key = null;

    JWTHMAC(JWTAlgorithm algorithm) {
        this.digestType = algorithm.getHashName();
        this.digestBlockSize = algorithm.getBlockSize();
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

    private String bytesToHex(byte[] text) {
        StringBuilder stringBuilder = new StringBuilder();
        for(byte t: text) {
            stringBuilder.append(String.format("%02x", t&0xff));
        }
        return stringBuilder.toString();
    }

    private byte[] normalizeKey(byte[] key) throws Exception{
        if (this.digestBlockSize < key.length) {
            return generateHash(key);
        }
        return padText(key, this.digestBlockSize);
    }

    public byte[] generateSign(byte[] key, byte[] message) throws Exception {
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
    public byte[] generateSign(String message) throws Exception {
        return generateSign(key.getBytes(), message.getBytes());
    }
}
