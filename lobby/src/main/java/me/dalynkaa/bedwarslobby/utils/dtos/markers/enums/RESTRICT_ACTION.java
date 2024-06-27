package me.dalynkaa.bedwarslobby.utils.dtos.markers.enums;

public enum RESTRICT_ACTION {
    JUMP("jump"),
    SWIM("swim"),
    SNEAK("sneak"),
    SPRINT("sprint");

    private final String action;

    RESTRICT_ACTION(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public static RESTRICT_ACTION getAction(String action) {
        for (RESTRICT_ACTION value : RESTRICT_ACTION.values()) {
            if (value.getAction().equals(action)) {
                return value;
            }
        }
        return null;
    }
}
