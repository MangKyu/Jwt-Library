package lineworks.bizdev.intern.homework.jwtlibrary;

// WORKS MOBILE 2020 하반기 Internship
// 작성자 : 김동헌

public interface JWTSignatory {
    public byte[] generateSign(String message) throws Exception;
}
