package lineworks.bizdev.intern.homework.mylibs.jwt.result;

import java.util.Date;
import java.util.Map;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.Header;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.SignWith;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.Subject;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GeneratedJwts extends Jwts {

	@Builder
	public GeneratedJwts(Subject subject, Map<String, Object> claims, Date expiredAt, SignWith signWith) {
		super(subject, Header.builder().encryptAlgorithm(signWith.getEncryptAlgorithm()).build(), claims, expiredAt, signWith);
	}

}
