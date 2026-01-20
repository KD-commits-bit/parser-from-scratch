package parser.json;

public enum JsonTokenType {
    // 구조적 기호
    LEFT_CURLY_BRACE,    // {
    RIGHT_CURLY_BRACE,   // }
    LEFT_BRACKET,  // [
    RIGHT_BRACKET, // ]
    COLON,         // :
    COMMA,         // ,

    // 데이터 타입
    STRING,
    NUMBER,
    BOOLEAN,
    NULL,

    EOF // 파일 끝
}
