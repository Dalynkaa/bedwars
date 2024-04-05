package me.dalynkaa.spbedwars.huds.utils.hudConstans;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.Map;

public enum StatusCharsNew {
    FLAG("R", Fonts.BED_STATUS_NEW, 8),
    SKULL("B", Fonts.BED_STATUS_NEW, 8),
    PLAYER("G", Fonts.BED_STATUS_NEW, 8),

    BG_1("1", Fonts.BED_STATUS_NEW, 1),
    BG_2("2", Fonts.BED_STATUS_NEW, 2),
    BG_4("3", Fonts.BED_STATUS_NEW, 4),
    BG_8("4", Fonts.BED_STATUS_NEW, 8),
    BG_16("5", Fonts.BED_STATUS_NEW, 16),
    BG_32("6", Fonts.BED_STATUS_NEW, 32),
    BG_64("7", Fonts.BED_STATUS_NEW, 64),
    BG_128("8", Fonts.BED_STATUS_NEW, 128),
    BG_START("s", Fonts.BED_STATUS_NEW, 4),
    BG_END("e", Fonts.BED_STATUS_NEW, 4),

    CHAR_0("1", Fonts.HUD, 8),
    CHAR_1("2", Fonts.HUD, 8),
    CHAR_2("3", Fonts.HUD, 8),
    CHAR_3("4", Fonts.HUD, 8),
    CHAR_4("5", Fonts.HUD, 8),
    CHAR_5("6", Fonts.HUD, 8),
    CHAR_6("7", Fonts.HUD, 8),
    CHAR_7("8", Fonts.HUD, 8),
    CHAR_8("9", Fonts.HUD, 8),
    CHAR_9("0", Fonts.HUD, 8),
    CHAR_DOTS(":", Fonts.HUD, 8),

    CHAR_V("v", Fonts.HUD, 8),
    CHAR_S("s", Fonts.HUD, 8),

    SPACE(" ", Fonts.SPACE, 1);

    private final String character;
    private final Fonts font;
    private final Integer width;

    StatusCharsNew(String character, Fonts font, Integer width) {
        this.character = character;
        this.font = font;
        this.width = width;
    }

    public String getCharacter() {
        return character;
    }

    public Fonts getFont() {
        return font;
    }

    public Integer getWidht() {
        return width;
    }

    public static Integer parceNumber(TextComponent.Builder string, Integer number) {
        Map<String, StatusCharsNew> map = Map.of("0", StatusCharsNew.CHAR_0, "1", StatusCharsNew.CHAR_1, "2", StatusCharsNew.CHAR_2, "3", StatusCharsNew.CHAR_3, "4", StatusCharsNew.CHAR_4, "5", StatusCharsNew.CHAR_5, "6", StatusCharsNew.CHAR_6, "7", StatusCharsNew.CHAR_7, "8", StatusCharsNew.CHAR_8, "9", StatusCharsNew.CHAR_9);
        String value = number.toString();
        Integer length = 0;
        for (int i = 0; i < value.length(); i++) {
            StatusCharsNew statusChars = map.get(String.valueOf(value.charAt(i)));
            string.append(Component.text(statusChars.getCharacter()).style((style) -> {
                style.font(statusChars.getFont().getKey());
            }));
            length += statusChars.getWidht();
        }
        return length;
    }

    public static Integer parceNumber(TextComponent.Builder string, String number) {
        Map<String, StatusCharsNew> map = Map.of("0", StatusCharsNew.CHAR_0, "1", StatusCharsNew.CHAR_1, "2", StatusCharsNew.CHAR_2, "3", StatusCharsNew.CHAR_3, "4", StatusCharsNew.CHAR_4, "5", StatusCharsNew.CHAR_5, "6", StatusCharsNew.CHAR_6, "7", StatusCharsNew.CHAR_7, "8", StatusCharsNew.CHAR_8, "9", StatusCharsNew.CHAR_9);
        Integer length = 0;
        for (int i = 0; i < number.length(); i++) {
            StatusCharsNew statusChars = map.get(String.valueOf(number.charAt(i)));
            string.append(Component.text(statusChars.getCharacter()).style((style) -> {
                style.font(statusChars.getFont().getKey());
            }));
            length += statusChars.getWidht();
        }
        return length;
    }
}
