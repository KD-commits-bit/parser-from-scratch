package parser.json.node;

public interface JsonNode {
    /**
     * AST 노드를 실제 Java 객체(Map, List, String, Integer 등)로 변환
     */
    Object asJavaObject();
}
