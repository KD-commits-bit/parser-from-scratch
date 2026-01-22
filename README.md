# 🚀 Java Universal Parser (Learning Project)

> 자바의 기본 기능만으로 Lexer와 Parser를 직접 구현하며  
> **다양한 데이터 포맷(JSON, XML, CSV)을 파싱할 수 있는 확장 가능한 파서 구조를 설계·학습하는 프로젝트**입니다.

---

## 📌 Project Overview

이 프로젝트는 Jackson, Gson과 같은 JSON 파싱 라이브러리를 **의도적으로 사용하지 않고**,  
Java의 기본 기능만으로 문자열 입력이 객체 구조로 변환되는 전 과정을 직접 구현하는 것을 목표로 합니다.

본 프로젝트의 핵심은 특정 포맷(JSON)에 종속된 구현이 아니라,

- 문자열이 어떻게 **의미 있는 토큰(Token)** 으로 분해되는지
- 토큰이 어떻게 **문법 구조(AST 유사 구조)** 로 해석되는지
- 중첩된 데이터 구조를 파서가 어떤 방식으로 처리하는지

를 **범용적인 파서 아키텍처 관점에서 이해**하는 데 있습니다.

이를 위해 다음과 같은 설계 원칙을 적용했습니다.

- **Lexer / Parser 책임 분리**
- **토큰 단위 설계(Tokenization)**
- **재귀 하향 파싱(Recursive Descent Parsing)**

현재는 JSON 파서를 **기준 구현체(Reference Implementation)** 로 개발하고 있으며,  
동일한 구조를 활용해 XML, CSV 등 다른 데이터 포맷으로 확장하는 것을 목표로 합니다.

---

## 📌 Why This Project? (왜 직접 만들었는가)

### 1. 라이브러리 추상화 너머의 이해
Jackson, Gson은 매우 편리하지만  
`String → Object` 변환 과정이 내부적으로 어떻게 이루어지는지 파악하기 어렵다고 판단했습니다.

→ **라이브러리가 추상화한 영역을 직접 구현함으로써**,  
Lexing, Parsing, Recursion, Pointer Control 등 파서의 핵심 개념을 코드 레벨에서 체득하는 것이 목표입니다.

---

### 2. 확장 가능한 파서 구조 설계
JSON은 단순해 보이지만,

- 객체(Object)와 배열(Array)의 **재귀적 중첩 구조**
- 문자열, 숫자, 리터럴(true / false / null)의 **상태 기반 처리**

등 파서 설계의 핵심 개념이 모두 포함된 포맷입니다.

→ JSON을 시작점으로 삼아 **공통 Lexer / Parser 구조를 검증하고**,  
이를 기반으로 XML, CSV 등 다른 데이터 포맷으로 확장 가능한 구조를 설계하고자 했습니다.

---

## 🛠 Parser Architecture (공통 구조)
> 본 파서는 문법 오류 발생 시 현재 토큰 위치를 기준으로 한 예외 메시지를 반환하도록 설계되었습니다.

본 프로젝트는 포맷별 구현과 무관하게 다음과 같은 공통 데이터 처리 파이프라인을 따릅니다.

1. **Lexical Analysis**: 입력 문자열을 포인터 기반으로 탐색하여 Token Stream 생성
2. **Syntactic Analysis**: 토큰 스트림을 읽어 문법적 구조를 가진 AST Node 생성
3. **AST Traversal**: 완성된 트리를 순회하며 최종적인 Java Native 객체로 변환

---

## 🛠 JSON Parser Implementation (Reference)

### 1. Lexical Analysis (Tokenizing)

- **O(n) 탐색**: 단 한 번의 순회로 모든 토큰을 추출합니다.
- **상태 기반 분기**: 구조 토큰({, [, :, ,)과 값 토큰(STRING, NUMBER, BOOLEAN, NULL)을 구분합니다.

---

### 2. Recursive Descent Parsing

- **재귀 하향 파싱**: JSON의 무한 중첩 구조를 처리하기 위해 파서가 자기 자신을 호출하는 구조를 채택했습니다.

- **AST Node 설계**: 데이터를 즉시 변환하지 않고 JsonNode 인터페이스를 거쳐 구조화함으로써 관심사를 분리했습니다.

  - JsonObjectNode, JsonArrayNode: 컨테이너 역할 및 자식 노드 관리 
  - JsonValueNode: 실제 데이터 타입 변환(Double, Long, Boolean 등) 담당

---

## 🛠 Features (Progress)

### Implemented Parser

#### JSON (Reference Implementation)
- [x] Basic Tokens
    - `{ } [ ] : ,`
- [x] String Tokenization
    - 따옴표 기반 문자열 처리
- [x] Number Tokenization
    - 비숫자 문자(Non-digit)를 만날 때까지 포인터 이동
- [x] Boolean / Null Token
    - `true`, `false`, `null` 리터럴 인식
- [x] Recursive Object & Array Parsing
    - 재귀 하향 파싱을 통한 중첩 구조 처리
- [x] AST 기반 객체 변환
    - `asJavaObject()` 메서드를 통한 AST 노드 전역 순회 및 최종 결과물(Map/List) 생성

---

### Planned Parsers
- [ ] XML Parser
- [ ] CSV Parser

---

## 💻 Usage Example (JSON)

위 예제는 입력된 JSON 문자열이 단순히 Map으로 캐스팅되는 것이 아니라   
**[Lexer → Parser → AST 생성 → Java Object 변환]** 의 정밀한 파이프라인을 거치는 과정을 보여줍니다.  
특히 최종 출력에서 확인되듯이, 문자열이었던 "1.0"과 "true"가 각각 Java의 **Double**과 Boolean 타입으로 정확히 복원되는 것이  
본 프로젝트의 핵심인 **'타입 정교화(Type Refinement)'** 의 결과입니다.

```java
public class Main {
    public static void main(String[] args) {
        // 1. 입력 데이터 (Input)
        String jsonInput = "{" +
            "\"title\": \"Parser Project\"," +
            "\"tags\": [\"learning\", \"java\"]," +
            "\"contributors\": [" +
            "{ \"name\": \"홍길동\", \"role\": \"developer\" }," +
            "{ \"name\": \"Assistant\", \"role\": \"helper\" }" +
            "]," +
            "\"completed\": true," +
            "\"version\": 1.0" + // 숫자 타입 테스트용 추가
            "}";

        System.out.println("=== [Step 1] Raw Input ===");
        System.out.println(jsonInput);

        try {
            // 2. 어휘 분석 (Lexing)
            JsonLexer lexer = new JsonLexer(jsonInput);
            List<JsonToken> tokens = lexer.tokenize();

            // 3. 구문 분석 및 AST 생성 (Parsing)
            JsonParser parser = new JsonParser(tokens);
            JsonNode astRoot = parser.parse();

            System.out.println("\n=== [Step 2] AST Generated ===");
            System.out.println("Root Node Type: " + astRoot.getClass().getSimpleName());

            // 4. 최종 객체 변환 (Transformation)
            // AST 트리를 순회하며 Java Native Object(Map, List 등)로 변환합니다.
            Object result = astRoot.asJavaObject();

            System.out.println("\n=== [Step 3] Final Output (Java Object) ===");
            System.out.println(result);

            if (result instanceof java.util.Map) {
                System.out.println("\n✅ 성공: JSON 데이터가 Java Map 구조로 정상 변환되었습니다.");

                // 데이터 타입 검증 예시 (피드백 반영)
                java.util.Map<?, ?> resultMap = (java.util.Map<?, ?>) result;
                System.out.println("검증 - 'completed' 타입: " + resultMap.get("completed").getClass().getSimpleName());
                System.out.println("검증 - 'version' 타입: " + resultMap.get("version").getClass().getSimpleName());
            }

        } catch (Exception e) {
            System.err.println("\n❌ 파싱 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```
## ✅ Expected Output
```text
=== [Step 1] Raw Input ===
{"title": "Parser Project","tags": ["learning", "java"],"contributors": [{ "name": "홍길동", "role": "developer" },{ "name": "Gemini", "role": "helper" }],"completed": true,"version": 1.0}

=== [Step 2] AST Generated ===
Root Node Type: JsonObjectNode

=== [Step 3] Final Output (Java Object) ===
{title=Parser Project, tags=[learning, java], contributors=[{name=홍길동, role=developer}, {name=Gemini, role=helper}], completed=true, version=1.0}

✅ 성공: JSON 데이터가 Java Map 구조로 정상 변환되었습니다.
검증 - 'completed' 타입: Boolean
검증 - 'version' 타입: Double
```

### 🚀 Challenges & Learnings
- **String Parsing Logic**  
  문자열 내부의 문자와 닫는 따옴표(")를 구분하는 조건문 설계 과정에서 발생한 논리 오류를 디버깅을 통해 해결했습니다.
- **Recursive Descent Parsing**  
  중첩된 JSON 객체와 배열을 처리하기 위해 파서가 자기 자신을 다시 호출하는 '재귀' 구조를 설계했습니다.  
  이 과정에서 토큰 포인터(current)가 정확한 위치를 유지하도록 소비(Consume) 로직을 정교화하는 데 집중했습니다.
- **Separation of Concerns**  
  처음에는 파서 내부에 변환 로직이 섞여 있었으나 AST(추상 구문 트리) 구조를 도입하여 '구조 해석'과 '데이터 변환'의 책임을 분리했습니다.  
  이를 통해 더 객체지향적이고 확장성 있는 코드를 작성할 수 있었습니다.