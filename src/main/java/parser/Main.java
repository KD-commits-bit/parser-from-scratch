package parser;

import parser.json.JsonLexer;
import parser.json.JsonToken;

import java.util.List;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    public static void main(String[] args) {
        String jsonInput = "{ \"age\": 25 }";

        JsonLexer lexer = new JsonLexer(jsonInput);

        try {
            List<JsonToken> tokens = lexer.tokenize();

            System.out.println("--- 토큰화 결과 ---");
            for (JsonToken token : tokens) {
                System.out.println(token);
            }
        } catch (Exception e) {
            System.err.println("오류 발생: " + e.getMessage());
        }
    }
}