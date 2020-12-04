package lineworks.bizdev.intern.homework.jwtlibrary.dto;

// WORKS MOBILE 2020 하반기 Internship
// 작성자 : 김동헌

public class JwtHeader {
    private String typ;
    private String alg;

    public JwtHeader(String algorithm) {
        typ = "JWT";
        alg = algorithm;
    }
}
