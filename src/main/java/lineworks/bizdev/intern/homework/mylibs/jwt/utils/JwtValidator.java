package lineworks.bizdev.intern.homework.mylibs.jwt.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.Date;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.SignWith;
import lineworks.bizdev.intern.homework.mylibs.jwt.sign.JwtBase;
import lineworks.bizdev.intern.homework.mylibs.jwt.sign.JwtHMAC;
import lineworks.bizdev.intern.homework.mylibs.jwt.sign.JwtRSASSA;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class JwtValidator {

	private JwtValidator() {
		throw new IllegalStateException("Utility Class");
	}

	public static boolean isValidToken(String token, SignWith signWith) {
		try {
			String generated = generateToken(token, signWith);
			if (!token.equals(generated)) {
				log.error("This Token is Invalid" + token);
				return false;
			}

			if (isExpired(token)) {
				log.error("This Token is Expired: " + token);
				return false;
			}
			return true;
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			log.error(e.getMessage());
		}

		return false;
	}

	private static JwtBase getJwtBase(SignWith signWith) {
		return signWith.getEncryptAlgorithm().isRsa()
			? new JwtRSASSA(signWith.getEncryptAlgorithm(), signWith.getKey())
			: new JwtHMAC(signWith.getEncryptAlgorithm(), signWith.getKey());
	}

	private static String generateToken(String token, SignWith signWith) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		String input = token.substring(0, token.lastIndexOf('.'));
		JwtBase base = getJwtBase(signWith);
		return JwtGenerator.generate(input, base);
	}

	private static boolean isExpired(String token) {
		Date expiredAt = DataConverter.convertToExpiredAt(token);
		Date now = Date.from(Calendar.getInstance().toInstant());
		return now.after(expiredAt);
	}

}
