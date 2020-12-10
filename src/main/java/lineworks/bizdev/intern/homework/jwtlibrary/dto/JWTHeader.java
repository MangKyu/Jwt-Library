package lineworks.bizdev.intern.homework.jwtlibrary.dto;

// WORKS MOBILE 2020 하반기 Internship
// 작성자 : 김동헌

public class JWTHeader {
    private String typ;
    private String alg;

    public JWTHeader(String algorithm) {
        typ = "JWT";
        alg = algorithm;
    }
}
