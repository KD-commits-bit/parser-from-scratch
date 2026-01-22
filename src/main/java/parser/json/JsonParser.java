package parser.json;

import parser.json.node.*;

import java.util.List;

public class JsonParser {
    private final List<JsonToken> tokens;
    private int current = 0;

    public JsonParser(List<JsonToken> tokens) {
        this.tokens = tokens;
    }

    public JsonNode parse() {
        JsonToken token = peek();

        if (token.getType() == JsonTokenType.LEFT_CURLY_BRACE) {

            return parseObject();
        } else if (token.getType() == JsonTokenType.LEFT_BRACKET) {

            return parseArray();
        } else if (token.getType() == JsonTokenType.STRING) {

            return new JsonStringNode(consume().getValue());
        } else if (token.getType() == JsonTokenType.NUMBER) {

            return new JsonNumberNode(consume().getValue());
        } else if (token.getType() == JsonTokenType.BOOLEAN) {

            return new JsonBooleanNode(consume().getValue());
        }

        throw new RuntimeException("알 수 없는 토큰 타입: " + token.getType());
    }

    public JsonObjectNode parseObject() {
        JsonObjectNode node = new JsonObjectNode();

        consume();

        while (peek().getType() != JsonTokenType.RIGHT_CURLY_BRACE) {
            if (peek().getType() == JsonTokenType.STRING) {
                String key = consume().getValue();

                if (peek().getType() == JsonTokenType.COLON) {
                    consume();
                }

                node.addChildren(key, parse());
            } else if (tokens.get(current).getType() == JsonTokenType.COMMA) {
                consume();
            } else {
                throw new RuntimeException("쉼표나 키값이 필요합니다");
            }
        }

        consume();

        return node;
    }

    public JsonArrayNode parseArray() {
        JsonArrayNode node = new JsonArrayNode();

        consume();

        while (peek().getType() != JsonTokenType.RIGHT_BRACKET) {

            if (peek().getType() == JsonTokenType.COMMA) {
                consume();
            } else {
                node.addElement(parse());
            }
        }

        consume();

        return node;
    }

    private JsonToken consume() {
        return tokens.get(current++);
    }

    private JsonToken peek() {
        return tokens.get(current);
    }
}
