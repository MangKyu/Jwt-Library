package lineworks.bizdev.intern.homework.mylibs.jwt.exception;

public class TokenExpiredException extends RuntimeException {

	public TokenExpiredException(String message) {
		super("This Token is Expired: " + message);
	}

}
