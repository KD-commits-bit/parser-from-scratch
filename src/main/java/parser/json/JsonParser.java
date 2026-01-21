package parser.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {
    private final List<JsonToken> tokens;
    private int current = 0;

    public JsonParser(List<JsonToken> tokens) {
        this.tokens = tokens;
    }

    public Object parse() {
        JsonToken token = peek();

        if (token.getType() == JsonTokenType.LEFT_CURLY_BRACE) {

            return parseObject();
        } else if (token.getType() == JsonTokenType.LEFT_BRACKET) {

            return parseArray();
        } else if (token.getType() == JsonTokenType.STRING) {

            return consume().getValue();
        } else if (token.getType() == JsonTokenType.NUMBER) {

            return consume().getValue();
        } else if (token.getType() == JsonTokenType.BOOLEAN) {

            return consume().getValue();
        }

        throw new RuntimeException("알 수 없는 토큰 타입: " + token.getType());
    }

    public Map<String, Object> parseObject() {
        Map<String, Object> map = new LinkedHashMap<>();

        consume();

        while (peek().getType() != JsonTokenType.RIGHT_CURLY_BRACE) {
            if (peek().getType() == JsonTokenType.STRING) {
                String key = consume().getValue();

                if (peek().getType() == JsonTokenType.COLON) {
                    consume();
                }

                map.put(key, parse());
            } else if (tokens.get(current).getType() == JsonTokenType.COMMA) {
                consume();
            } else {
                consume();
            }
        }

        consume();

        return map;
    }

    public List<Object> parseArray() {
        List<Object> list = new ArrayList<>();

        consume();

        while (peek().getType() != JsonTokenType.RIGHT_BRACKET) {
            if (peek().getType() == JsonTokenType.STRING) {
                list.add(parse());
            } else if (peek().getType() == JsonTokenType.COMMA) {
                consume();
            } else {
                list.add(parse());
            }
        }

        consume();

        return list;
    }

    private JsonToken consume() {
        return tokens.get(current++);
    }

    private JsonToken peek() {
        return tokens.get(current);
    }
}
