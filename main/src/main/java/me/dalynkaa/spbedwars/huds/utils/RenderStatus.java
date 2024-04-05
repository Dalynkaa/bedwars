package me.dalynkaa.spbedwars.huds.utils;

import net.kyori.adventure.text.Component;

public class RenderStatus {
    private final RenderStatusType status;
    private final Component message;
    private final Integer hudSize;

    public RenderStatus(RenderStatusType status, Component message, Integer hudSize) {
        this.status = status;
        this.message = message;
        this.hudSize = hudSize;
    }

    public RenderStatus(RenderStatusType status, Component message) {
        this.status = status;
        this.message = message;
        this.hudSize = 0;
    }

    public RenderStatusType getStatus() {
        return status;
    }

    public Component getMessage() {
        return message;
    }

    public Integer getBgSize() {
        return hudSize;
    }
}

