package parser.json;

public class JsonToken {
    private final JsonTokenType type; // 토큰의 종류 (예: LEFT_BRACE, STRING)
    private final String value;       // 실제 문자열 값 (예: "{", "name", "123")

    public JsonToken(JsonTokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public JsonTokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    // 디버깅을 위해 출력을 예쁘게 만들어주는 메서드
    @Override
    public String toString() {
        return String.format("Token(%s, '%s')", type, value);
    }
}
