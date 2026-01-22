package parser.json.node;

public class JsonBooleanNode implements JsonNode {
    private final String value;

    public JsonBooleanNode(String value) {
        this.value = value;
    }

    @Override
    public Object asJavaObject() {
        if (value.equals("true")) {
            return true;
        } else {
            return false;
        }
    }
}
