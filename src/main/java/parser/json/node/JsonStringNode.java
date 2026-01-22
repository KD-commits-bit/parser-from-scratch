package parser.json.node;

public class JsonStringNode implements JsonNode {
    private final String value;

    public JsonStringNode(String value) {
        this.value = value;
    }

    @Override
    public Object asJavaObject() {
        return value;
    }
}
