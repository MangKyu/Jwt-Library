package lineworks.bizdev.intern.homework.mylibs.jwt.exception;

public class InvalidTokenException extends RuntimeException {

	public InvalidTokenException(String message) {
		super("This Token is Invalid" + message);
	}

}
