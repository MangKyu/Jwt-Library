package lineworks.bizdev.intern.homework.jwtlibrary.dto;

// WORKS MOBILE 2020 하반기 Internship
// 작성자 : 김동헌

public class JWTHeader {
    private String alg;
    private String typ;

    public JWTHeader(String algorithm) {
        alg = algorithm;
        typ = "JWT";
    }
}
