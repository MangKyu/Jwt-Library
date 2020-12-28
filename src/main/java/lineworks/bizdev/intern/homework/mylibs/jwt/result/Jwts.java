package lineworks.bizdev.intern.homework.mylibs.jwt.result;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.Date;
import java.util.Map;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.Header;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.SignWith;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.Signatory;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.Subject;
import lineworks.bizdev.intern.homework.mylibs.jwt.sign.JwtHMAC;
import lineworks.bizdev.intern.homework.mylibs.jwt.sign.JwtRSASSA;
import lineworks.bizdev.intern.homework.mylibs.jwt.utils.JwtGenerator;
import lombok.Getter;

@Getter
public abstract class Jwts {

	private final Header header;
	private final Subject subject;
	private final Map<String, Object> claims;
	private final Date expiredAt;
	private final Signatory signatory;

	public Jwts(Subject subject, Header header, Map<String, Object> claims, Date expiredAt, SignWith signWith) {
		this.subject = subject;
		this.header = header;
		this.claims = claims;
		this.expiredAt = expiredAt;
		this.signatory = signWith.isRsa()
			? new JwtRSASSA(signWith.getEncryptAlgorithm(), (PrivateKey)signWith.getKey())
			: new JwtHMAC(signWith.getEncryptAlgorithm(), (String)signWith.getKey());
	}

	public String generate() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		return JwtGenerator.generate(this);
	}

}
