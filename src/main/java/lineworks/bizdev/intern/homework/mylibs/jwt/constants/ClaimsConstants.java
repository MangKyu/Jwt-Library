package lineworks.bizdev.intern.homework.mylibs.jwt.constants;

public final class ClaimsConstants {

	private ClaimsConstants() {
		throw new IllegalStateException("Constants Class");
	}

	public static final String CLAIMS_EXPIRED_AT = "exp";

	public static final String CLAIMS_ISSUE_AT = "iat";

	public static final String CLAIMS_ISSUER = "iss";

}
