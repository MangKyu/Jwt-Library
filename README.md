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

## 패키지 구분
- Component: Header, Subject 등 JWT를 구성하는 컴포넌트 패키지
- Constants: 암호화 알고리즘, Claims 등을 위한 상수 패키지
- Result: 컴포넌트들이 모여 이루어진 결과체를 담는 패키지
- Sign: 서명을 위한 클래스들을 정의하는 패키지
- Utils: 결과체로 실제 String 토큰을 생성, 인코딩/디코딩, 검증 등을 처리하는 유틸리티 패키지

## 의존성

SHA-2 해쉬계산, RSASSA with SHA-2 서명은 java.security 의 클래스를 사용하고 있습니다.  
클래스 인스턴스를 JSON으로 변환하기 위해 GSON을 사용하고 있습니다.  
lombok을 사용하고 있습니다.  

## 샘플코드 설명

JUnit로 작성된 두개의 테스트 코드(JWTTokenizerRS256Test, JWTTokenizerHS256Test)를 포함하고 있습니다.  
기존에 포함된 MainClass의 샘플코드는 삭제되었습니다.
