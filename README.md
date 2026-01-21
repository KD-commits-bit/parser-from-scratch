# 🚀 Java Universal Parser (Learning Project) 
> 자바의 기본 기능만으로 Lexer와 Parser를 직접 구현하며
> 데이터 포맷 파싱의 내부 동작 원리를 학습하는 프로젝트입니다.

## 📌 Project Overview 
이 프로젝트는 Jackson, Gson과 같은 외부 라이브러리를 사용하지 않고 
**문자열 포인터 이동(position 기반 처리)** 과 **재귀 하향 파싱 구조**를 통해 JSON을 시작으로 다양한 데이터 포맷을 직접 파싱하는 것을 목표로 합니다. 
단순한 사용이 아닌,
- Lexer와 Parser의 역할 분리
- 토큰 단위 설계
- 문법 구조 해석

등 **파서 내부 동작 원리 이해**에 초점을 둡니다.  


특히 Lexer 단계에서는 문자 단위 포인터 이동을 통해 입력을 순차적으로 해석하며 토큰을 생성하는 구조를 직접 구현했습니다.
## 🛠 Features (Progress) 
### JSON Parser 
- [x] Basic Tokens  
  - `{ } [ ] : ,`
- [x] String Tokenization  
  - 따옴표 기반 문자열 처리
- [x] Number Tokenization
  - 비숫자 문자(Non-digit)를 만날 때까지 읽는 방식
- [x] Boolean / Null Token
  - `true`, `false`, `null` 리터럴 인식 로직 구현
- [x] Recursive Object & Array Parsing
  - 재귀 호출(Recursive Descent)을 통한 중첩된 객체와 배열 구조 해석
- [ ] AST 기반 객체 변환
      
### Future Plan 
- [ ] XML Parser
- [ ] CSV Parser

## 💻 Usage 
```java
public class Main {
    public static void main(String[] args) {
        String jsonInput = "{" +
            "\"title\": \"Parser Project\"," +
            "\"tags\": [\"learning\", \"java\"]," +
            "\"contributors\": [" +
            "{ \"name\": \"홍길동\", \"role\": \"developer\" }," +
            "{ \"name\": \"Gemini\", \"role\": \"helper\" }" +
            "]," +
            "\"completed\": true" +
            "}";

        JsonLexer lexer = new JsonLexer(jsonInput);
        List<JsonToken> tokens = lexer.tokenize();

        try {
            JsonParser parser = new JsonParser(tokens);

            Object result = parser.parse();

            System.out.println("--- 파싱 결과 ---");
            System.out.println(result);

            if (result instanceof java.util.Map) {
                System.out.println("성공: 객체(Map)로 분석되었습니다.");
            }
        } catch (Exception e) {
            System.err.println("파싱 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

## ✅ Expected Output
```text
--- 파싱 결과 ---
{title=Parser Project, tags=[learning, java], contributors=[{name=홍길동, role=developer}, {name=Gemini, role=helper}], completed=true}
성공: 객체(Map)로 분석되었습니다.
```
### 🚀 Challenges
- **String Parsing Logic:** 문자열 내부의 문자와 닫는 따옴표(`"`)를 구분하는 조건문 설계 과정에서 발생한 논리 오류를 디버깅을 통해 해결했습니다.
- **Recursive Descent Parsing:** 중첩된 JSON 객체와 배열을 처리하기 위해 파서가 자기 자신을 다시 호출하는 '재귀' 구조를 설계했습니다. 이 과정에서 토큰 포인터(`current`)가 정확한 위치를 유지하도록 소비(Consume) 로직을 정교화하는 데 집중했습니다.
