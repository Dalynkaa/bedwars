package me.dalynkaa.bedwarslobby.utils.dtos.markers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.dalynkaa.bedwarslobby.utils.BoundingBox;
import me.dalynkaa.bedwarslobby.utils.dtos.markers.enums.RESTRICT_ACTION;
import org.bukkit.util.Vector;

import static me.dalynkaa.bedwarslobby.utils.dtos.markers.MarkerData.getVector;

public class ActionRestriktMarkerData {

    private final String id;
    private final String name;
    private final RESTRICT_ACTION action;
    private final Vector min;
    private final Vector max;


    public ActionRestriktMarkerData(String id, String name, RESTRICT_ACTION action, Vector min, Vector max) {
        this.id = id;
        this.name = name;
        this.action = action;
        this.min = min;
        this.max = max;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public RESTRICT_ACTION getAction() {
        return action;
    }


    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }

    public BoundingBox getBoundingBox() {
        return BoundingBox.of(min, max);
    }

    @Override
    public String toString() {
        return "ActionRestriktMarkerData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", action=" + action +
                ", min=" + min +
                ", max=" + max +
                '}';
    }

    public static ActionRestriktMarkerData parse(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if (jsonObject.get("id") == null || jsonObject.get("name") == null || jsonObject.get("min") == null || jsonObject.get("max") == null) {
            return null;
        }
        String id = jsonObject.get("id").toString().replace("\"", "");
        String name = jsonObject.get("name").toString().replace("\"", "");
        String action = jsonObject.get("action").toString().replace("\"", "");
        RESTRICT_ACTION restrictAction = RESTRICT_ACTION.getAction(action);
        if (restrictAction == null) {
            return null;
        }
        JsonElement rawMinElement = jsonObject.get("min");
        JsonElement rawMaxElement = jsonObject.get("max");
        if (rawMinElement == null || rawMaxElement == null) {
            return null;
        }
        String[] rawMin = rawMinElement.toString().replace("[", "").replace("]", "").split(","); // [x, y, z]
        String[] rawMax = rawMaxElement.toString().replace("[", "").replace("]", "").split(","); // [x, y, z]
        if (rawMin.length != 3 || rawMax.length != 3) {
            return null;
        }
        Vector min = getVector(rawMin);
        Vector max = getVector(rawMax);

        return new ActionRestriktMarkerData(id, name, restrictAction, min, max);

    }
}
