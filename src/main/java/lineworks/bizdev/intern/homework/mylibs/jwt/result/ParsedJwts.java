package lineworks.bizdev.intern.homework.mylibs.jwt.result;

import java.util.Date;
import java.util.Map;

import lineworks.bizdev.intern.homework.mylibs.jwt.component.Header;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.SignWith;
import lineworks.bizdev.intern.homework.mylibs.jwt.component.Subject;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ParsedJwts extends Jwts {

	@Builder
	public ParsedJwts(Subject subject, Header header, Map<String, Object> claims, Date expiredAt, SignWith signWith) {
		super(subject, header, claims, expiredAt, signWith);
	}

}
