package parser.json.node;

import java.util.ArrayList;
import java.util.List;

public class JsonArrayNode implements JsonNode {
    private final List<JsonNode> elements = new ArrayList<>();

    public void addElement(JsonNode node) {
        elements.add(node);
    }

    @Override
    public Object asJavaObject() {
        List<Object> result = new ArrayList<>();

        for (JsonNode node : elements) {
            result.add(node.asJavaObject());
        }

        return result;
    }
}
