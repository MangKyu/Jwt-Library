package lineworks.bizdev.intern.homework.jwtlibrary.dto;

// WORKS MOBILE 2020 하반기 Internship
// 작성자 : 김동헌

public class JWTClaimSet {
    private final String iss;
    private final Long iat;
    private final Long exp;

    public JWTClaimSet(String iss) {
        this(iss,
                System.currentTimeMillis()/1000,
                System.currentTimeMillis()/1000 + 1800);
    }

    public JWTClaimSet(String iss, Long iat, Long exp) {
        this.iss = iss;
        this.iat = iat;
        this.exp = exp;
    }
}
