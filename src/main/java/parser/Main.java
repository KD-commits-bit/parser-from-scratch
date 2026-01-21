package parser;

import parser.json.JsonLexer;
import parser.json.JsonParser;
import parser.json.JsonToken;

import java.util.List;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
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