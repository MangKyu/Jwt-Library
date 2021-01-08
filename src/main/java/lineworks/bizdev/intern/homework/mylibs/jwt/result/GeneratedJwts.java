package lineworks.bizdev.intern.homework.mylibs.jwt.result;

import java.util.Date;
import java.util.Map;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.SignWith;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GeneratedJwts extends Jwts {

	@Builder
	public GeneratedJwts(String subject, Map<String, Object> claims, Date expiredAt, SignWith signWith) {
		super(subject, claims, expiredAt, signWith);
	}

}
