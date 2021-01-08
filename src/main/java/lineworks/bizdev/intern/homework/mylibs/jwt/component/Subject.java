package lineworks.bizdev.intern.homework.mylibs.jwt.component;

import lombok.Getter;

@Getter
public class Subject {

	private final String issuer;
	private final Long issueAt;

	public Subject(String issuer) {
		this.issuer = issuer;
		this.issueAt = System.currentTimeMillis() / 1000L;
	}

}
