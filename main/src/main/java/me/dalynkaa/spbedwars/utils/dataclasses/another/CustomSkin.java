package me.dalynkaa.spbedwars.utils.dataclasses.another;

import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.config.SkinConfig;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;
import org.mineskin.MineskinClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@SerializableAs("CustomSkin")
public class CustomSkin implements ConfigurationSerializable {
    private String name;
    private String value;
    private String signature;

    public CustomSkin(String name, String value, String signature) {
        this.value = value;
        this.signature = signature;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

    public static CustomSkin getSkin(String name) {
        if (!SkinConfig.hasSkinConfig(name)) {
            return null;
        }
        SkinConfig skinConfig = SkinConfig.getSkinConfig(name);
        return (CustomSkin) skinConfig.getCustomConfig().get("skin", CustomSkin.class);
    }

    public static CustomSkin getSkin(String name, CustomSkin defaultSkin) {
        if (!SkinConfig.hasSkinConfig(name)) {
            return defaultSkin;
        }
        SkinConfig skinConfig = SkinConfig.getSkinConfig(name);
        return (CustomSkin) skinConfig.getCustomConfig().get("skin", CustomSkin.class);
    }

    public void save() {
        SkinConfig skinConfig = SkinConfig.getSkinConfig(this.name);
        skinConfig.getCustomConfig().set("skin", this);
        try {
            skinConfig.saveCustomConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CustomSkin getByUrl(String url, String name) {
        MineskinClient client = new MineskinClient("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36 Edg/118.0.2088.41");
        AtomicReference<CustomSkin> customSkin = new AtomicReference<>();
        client.generateUrl(url).thenAccept(skin -> {
            Logger.debug("Skin generated");
            Logger.debug("Skin value: " + skin.data.texture.value);
            Logger.debug("Skin signature: " + skin.data.texture.signature);
            CustomSkin customSkin1 = new CustomSkin(name, skin.data.texture.value, skin.data.texture.signature);
            customSkin1.save();
            customSkin.set(customSkin1);
        }).exceptionally(throwable -> {
            Logger.error("Error while getting skin from url: " + url);
            Logger.error(throwable.getMessage());
            getByUrl(url, name);
            return null;
        });
        while (customSkin.get() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return customSkin.get();
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", getName());
        map.put("signature", getSignature());
        map.put("value", getValue());
        return map;
    }

    public static CustomSkin deserialize(Map<String, Object> map) {
        String name1 = (String) map.get("name");
        String signature1 = (String) map.get("signature");
        String value1 = (String) map.get("value");
        return new CustomSkin(name1, value1, signature1);
    }

}
