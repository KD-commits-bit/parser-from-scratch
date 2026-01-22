package parser.json.node;

public class JsonNumberNode implements JsonNode {
    private final String rawValue;

    public JsonNumberNode(String rawValue) {
        this.rawValue = rawValue;
    }

    @Override
    public Object asJavaObject() {
        try {
            if (rawValue.contains(".") || rawValue.contains("e") || rawValue.contains("E")) {
                return Double.parseDouble(rawValue);
            } else {
                return Long.parseLong(rawValue);
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format: " + rawValue);
        }
    }
}
