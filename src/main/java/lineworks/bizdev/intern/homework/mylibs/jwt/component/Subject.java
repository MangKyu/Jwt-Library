package lineworks.bizdev.intern.homework.mylibs.jwt.component;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class Subject {

	private final String iss;
	private final Long iat;

	@Builder
	public Subject(String iss) {
		this.iss = iss;
		this.iat = System.currentTimeMillis() / 1000;
	}

}
