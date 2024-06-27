package me.dalynkaa.bedwarslobby.utils.dtos.markers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.tr7zw.nbtapi.NBT;
import me.dalynkaa.bedwarslobby.utils.BoundingBox;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Marker;
import org.bukkit.util.Vector;

import java.util.UUID;

public class MarkerData {

    private String id;
    private String name;
    private Vector min;
    private Vector max;
    private String rawMarkerData;


    public MarkerData(String id, String name, Vector min, Vector max, String rawMarkerData) {
        this.id = id;
        this.name = name;
        this.min = min;
        this.max = max;
        this.rawMarkerData = rawMarkerData;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
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

    public static MarkerData parse(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if (jsonObject.get("id") == null || jsonObject.get("name") == null || jsonObject.get("min") == null || jsonObject.get("max") == null) {
            return null;
        }
        String id = jsonObject.get("id").toString().replace("\"", "");
        String name = jsonObject.get("name").toString().replace("\"", "");
        String[] rawMin = jsonObject.get("min").toString().replace("[", "").replace("]", "").split(","); // [x, y, z]
        String[] rawMax = jsonObject.get("max").toString().replace("[", "").replace("]", "").split(","); // [x, y, z]
        if (rawMin.length != 3 || rawMax.length != 3) {
            return null;
        }
        Vector min = getVector(rawMin);
        Vector max = getVector(rawMax);

        return new MarkerData(id, name, min, max, json);

    }

    public static Vector getVector(String[] rawMin) {
        return new Vector(Double.parseDouble(rawMin[0].substring(0, rawMin[0].length() - 2).replace("\"", "")), Double.parseDouble(rawMin[1].substring(0, rawMin[1].length() - 2).replace("\"", "")), Double.parseDouble(rawMin[2].substring(0, rawMin[2].length() - 2).replace("\"", "")));
    }

    public ActionMarkerData getActionMarkerData() {
        return ActionMarkerData.parse(rawMarkerData);
    }

    public ActionRestriktMarkerData getActionRestriktMarkerData() {
        return ActionRestriktMarkerData.parse(rawMarkerData);
    }

    public static String getRawMarkerData(String id, World world) {
        if (world.getEntity(UUID.fromString(id)) instanceof Marker marker) {
            return NBT.get(marker, nbt -> (String) nbt.getCompound("data").toString());
        }
        return null;
    }

    public static MarkerData getById(String id) {
        World world = Bukkit.getWorlds().get(0);
        if (world.getEntity(UUID.fromString(id)) instanceof Marker marker) {
            String readableNBT = NBT.get(marker, nbt -> (String) nbt.getCompound("data").toString());
            return MarkerData.parse(readableNBT);
        }
        return null;
    }

    public static ActionMarkerData getActionMarkerData(String id, World world) {
        if (world.getEntity(UUID.fromString(id)) instanceof Marker marker) {
            String readableNBT = NBT.get(marker, nbt -> (String) nbt.getCompound("data").toString());
            return ActionMarkerData.parse(readableNBT);
        }
        return null;
    }

    public static ActionRestriktMarkerData getActionRestriktMarkerData(String id, World world) {
        if (world.getEntity(UUID.fromString(id)) instanceof Marker marker) {
            String readableNBT = NBT.get(marker, nbt -> (String) nbt.getCompound("data").toString());
            return ActionRestriktMarkerData.parse(readableNBT);
        }
        return null;
    }
}
