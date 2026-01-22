package parser.json.node;

public class JsonNullNode implements JsonNode {
    @Override
    public Object asJavaObject() {
        return null;
    }
}
