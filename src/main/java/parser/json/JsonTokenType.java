package parser.json;

public enum JsonTokenType {
    LEFT_CURLY_BRACE,
    RIGHT_CURLY_BRACE,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    COLON,
    COMMA,

    STRING,
    NUMBER,
    BOOLEAN,
    NULL,

    EOF
}
