# JWTLibrary
JWT를 사용하기 위한 라이브러리 개발.

## 프로그램 설명
JSON으로 전송하는 데이터의 신뢰성/진위여부를 확인할 수 있게 JWT (RFC 7519) 로 서명합니다.  
현재 서명방법으로 다음 알고리즘을 사용할 수 있습니다.

- HS256 (HMAC with SHA256)
- HS384 (HMAC with SHA384)
- HS512 (HMAC with SHA512)
- RS256 (RSASSA PKCS1 with SHA256)
- RS384 (RSASSA PKCS1 with SHA384)
- RS512 (RSASSA PKCS1 with SHA512)

## 사용법
먼저 클래스 JWTTokenizer의 인스턴스를 작성합니다.

```java
JWTTokenizer jwtTokenizer = new JWTTokenizer(algorithm, key);
```

algorithm은 enum JwtAlgorithm에 정의되어 있습니다.  
key 는 HMAC 알고리즘의 경우 String타입의 대칭키, RSASSA 알고리즘의 경우에는 java.security.PrivateKey의 인스턴스를 넘겨주어야 합니다.

이제 아래 코드로 JWT로 전송하고 싶은 object를 넘기면 토큰을 얻을 수 있습니다.
```java
String token = jwtTokenizer.generateToken(object);
```

웍스모바일 API에서 요구하는 기본 ClaimSet을 payload로 사용하는 토큰은 다음 코드로 발행할 수 있습니다.
```java
String serverId = "46c4f281f81148c9b846c59262ae5888";
String token = jwtTokenizer.generateClaimSetToken(serverId);
```

## 클래스별 역할 설명

`class JWTTokenizer` : 토큰을 작성하는 클래스  
`interface JWTSignatory` : 토큰 서명에 사용되는 클래스들의 인터페이스  
`class JWTHMAC implements JWTSignatory` : HMAC 서명 로직  
`class JWTRSASSA implements JWTSignatory` : RSASSA with SHA-2 서명 로직   
`enum JWTAlgorithm` : 지원하는 알고리즘들을 기호화. JWT, java.security에서 필요로 하는 정보를 담음.  
`class JWTHeader` : 헤더 DTO  
`class JWTClaimSet` : ClaimSet DTO

## 의존성

SHA-2 해쉬계산, RSASSA with SHA-2 서명은 java.security 의 클래스를 사용하고 있습니다.  
클래스 인스턴스를 JSON으로 변환하기 위해 GSON을 사용하고 있습니다.  
lombok을 사용하고 있습니다.  

## 샘플코드 설명

JUnit로 작성된 두개의 테스트 코드(JWTTokenizerRS256Test, JWTTokenizerHS256Test)를 포함하고 있습니다.  
기존에 포함된 MainClass의 샘플코드는 삭제되었습니다.
