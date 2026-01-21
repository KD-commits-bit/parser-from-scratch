package parser.json;

import java.util.ArrayList;
import java.util.List;

public class JsonLexer {
    private final String input;
    private int position = 0;

    public JsonLexer(String input) {
        this.input = input;
    }

    public List<JsonToken> tokenize() {
        List<JsonToken> tokens = new ArrayList<>();

        while (position < input.length()) {
            char currentChar = input.charAt(position);

            if (Character.isWhitespace(currentChar)) {

                position++;
            } else if (currentChar == '{') {
                tokens.add(new JsonToken(JsonTokenType.LEFT_CURLY_BRACE, "{"));

                position++;
            } else if (Character.isDigit(currentChar)) {

                tokens.add(readNumberToken());
            } else if (currentChar == '"') {

                tokens.add(readStringToken());
            } else if (currentChar == '}') {
                tokens.add(new JsonToken(JsonTokenType.RIGHT_CURLY_BRACE, "}"));

                position++;
            } else if (currentChar == '[') {
                tokens.add(new JsonToken(JsonTokenType.LEFT_BRACKET, "["));

                position++;
            } else if (currentChar == ']') {
                tokens.add(new JsonToken(JsonTokenType.RIGHT_BRACKET, "]"));

                position++;
            } else if (currentChar == ':') {
                tokens.add(new JsonToken(JsonTokenType.COLON, ":"));

                position++;
            } else if (currentChar == ',') {
                tokens.add(new JsonToken(JsonTokenType.COMMA, ","));

                position++;
            } else if (currentChar == 't') {

                tokens.add(readLiteralToken("true", JsonTokenType.BOOLEAN));
            } else if (currentChar == 'f') {

                tokens.add(readLiteralToken("false", JsonTokenType.BOOLEAN));
            } else if (currentChar == 'n') {

                tokens.add(readLiteralToken("null", JsonTokenType.NULL));
            } else {
                throw new RuntimeException("Unexpected character: " + currentChar);
            }
        }
        tokens.add(new JsonToken(JsonTokenType.EOF, ""));

        return tokens;
    }

    private JsonToken readLiteralToken(String literal, JsonTokenType type) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < literal.length(); i++) {
            if (position < input.length() && input.charAt(position) == literal.charAt(i)) {
                sb.append(input.charAt(position));
                position++;
            } else {
                throw new RuntimeException("Invalid literal: expected " + literal);
            }
        }

        return new JsonToken(type, sb.toString());
    }

    private JsonToken readStringToken() {
        StringBuilder sb = new StringBuilder();

        position++;

        while (position < input.length() && input.charAt(position) != '"') {
            sb.append(input.charAt(position));

            position++;
        }

        if (position >= input.length()) {
            throw new RuntimeException("Unterminated string literal");
        }

        position++;

        return new JsonToken(JsonTokenType.STRING, sb.toString());
    }

    private JsonToken readNumberToken() {
        StringBuilder sb = new StringBuilder();

        while (position < input.length()) {
            char c = input.charAt(position);

            if (Character.isDigit(c) || c == '.') {
                sb.append(c);

                position++;
            } else {
                break;
            }
        }

        return new JsonToken(JsonTokenType.NUMBER, sb.toString());
    }
}
