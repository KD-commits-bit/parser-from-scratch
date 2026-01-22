package parser;

import parser.json.JsonLexer;
import parser.json.JsonParser;
import parser.json.JsonToken;
import parser.json.node.JsonNode;

import java.util.List;

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