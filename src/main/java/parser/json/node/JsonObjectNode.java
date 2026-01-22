package parser.json.node;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObjectNode implements JsonNode {
    private final Map<String, JsonNode> children = new LinkedHashMap<>();

    public void addChildren(String key, JsonNode value) {
        children.put(key, value);
    }

    @Override
    public Object asJavaObject() {
        Map<String, Object> result = new LinkedHashMap<>();

        for (Map.Entry<String, JsonNode> entry : children.entrySet()) {
            result.put(entry.getKey(), entry.getValue().asJavaObject());
        }

        return result;
    }
}
