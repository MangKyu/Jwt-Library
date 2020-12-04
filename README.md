# JwtLibrary
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
먼저 클래스 JwtTokenizer의 인스턴스를 작성합니다.

```java
JwtTokenizer jwtTokenizer = new JwtTokenizer(algorithm, key);
```

algorithm은 enum JwtAlgorithm에 정의되어 있습니다.  
key 는 HMAC 알고리즘의 경우 String타입의 대칭키, RSASSA 알고리즘의 경우에는 java.security.PrivateKey의 인스턴스를 넘겨주어야 합니다.

이제 아래 코드로 JWT로 전송하고 싶은 object를 넘기면 토큰을 얻을 수 있습니다.
```java
String token = jwtTokenizer.generateToken(object);
```

## 클래스별 역할 설명

class JwtTokenizer : 토큰을 작성하는 클래스  
interface JwtSignatory : 토큰 서명에 사용되는 클래스들의 인터페이스  
class JwtHmac implements JwtSignatory : HMAC 서명 로직
class JwtRsaSha implements JwtSignatory : RSASSA with SHA-2 서명 로직  
enum JwtAlgorithm : 지원하는 알고리즘들을 기호화. JWT, java.security에서 필요로 하는 정보를 담음.

## 의존성

SHA-2 해쉬계산, RSASSA with SHA-2 서명은 java.security 의 클래스를 사용하고 있습니다.
클래스 인스턴스를 JSON으로 변환하기 위해 GSON을 사용하고 있습니다.
lombok를 사용하고 있습니다.

## 샘플코드 설명

jwtsample/MainClass.main 은 resource/cert.key 의 내용을 암호화키로 삼아 RS512로 토큰을 발행하는 예제입니다.
cert.key 에는 `-----BEGIN RSA PRIVATE KEY-----` 로 시작하는 PKCS8 규격의 개인키가 담겨 있어야 합니다. 
작동을 재현하기 쉽도록 src/main/resource 에 자체 서명된 개인키와 인증서 쌍을 함께 넣어두었습니다.
