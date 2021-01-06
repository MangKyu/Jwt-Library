package lineworks.bizdev.intern.homework.mylibs.jwt.constants;

public final class TokenConstants {

	private TokenConstants() {
		throw new IllegalStateException("Constants Class");
	}

	public static final String JWT_TOKEN_TYPE = "JWT";

	public static final String CLAIMS_EXPIRED_AT = "exp";

}
