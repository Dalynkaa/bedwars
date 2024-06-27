package me.dalynkaa.bedwarslobby.utils.dtos.markers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.dalynkaa.bedwarslobby.utils.BoundingBox;
import org.bukkit.util.Vector;

import static me.dalynkaa.bedwarslobby.utils.dtos.markers.MarkerData.getVector;

public class ActionMarkerData {

    private final String id;
    private final String name;
    private final String action;
    private final Vector force;
    private final Vector min;
    private final Vector max;


    public ActionMarkerData(String id, String name, String action, Vector force, Vector min, Vector max) {
        this.id = id;
        this.name = name;
        this.action = action;
        this.force = force;
        this.min = min;
        this.max = max;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public Vector getForce() {
        return force;
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
        return "MarkerData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", min=" + min +
                ", max=" + max +
                '}';
    }

    public static ActionMarkerData parse(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if (jsonObject.get("id") == null || jsonObject.get("name") == null || jsonObject.get("min") == null || jsonObject.get("max") == null) {
            return null;
        }
        String id = jsonObject.get("id").toString().replace("\"", "");
        String name = jsonObject.get("name").toString().replace("\"", "");
        String action = jsonObject.get("action").toString().replace("\"", "");
        JsonElement rawForceElement = jsonObject.get("force");
        JsonElement rawMinElement = jsonObject.get("min");
        JsonElement rawMaxElement = jsonObject.get("max");
        if (rawForceElement == null || rawMinElement == null || rawMaxElement == null) {
            return null;
        }
        String[] rawForce = rawForceElement.toString().replace("[", "").replace("]", "").split(","); // [x, y, z]
        String[] rawMin = rawMinElement.toString().replace("[", "").replace("]", "").split(","); // [x, y, z]
        String[] rawMax = rawMaxElement.toString().replace("[", "").replace("]", "").split(","); // [x, y, z]
        if (rawMin.length != 3 || rawMax.length != 3 || rawForce.length != 3) {
            return null;
        }
        Vector min = getVector(rawMin);
        Vector max = getVector(rawMax);
        Vector force = getVector(rawForce);

        return new ActionMarkerData(id, name, action, force, min, max);

    }
}
