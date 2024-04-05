package me.dalynkaa.spbedwars.huds.utils.hudConstans;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.Map;

public enum StatusChars {
    BED_RED("R", Fonts.BED_STATUS, 8),
    BED_BLUE("B", Fonts.BED_STATUS, 8),
    BED_GREEN("G", Fonts.BED_STATUS, 8),
    BED_YELLOW("Y", Fonts.BED_STATUS, 8),
    YES_CHECK("T", Fonts.BED_STATUS, 8),
    NO_CROSS("F", Fonts.BED_STATUS, 8),

    BG_1("1", Fonts.BED_STATUS, 1),
    BG_2("2", Fonts.BED_STATUS, 2),
    BG_4("3", Fonts.BED_STATUS, 4),
    BG_8("4", Fonts.BED_STATUS, 8),
    BG_16("5", Fonts.BED_STATUS, 16),
    BG_32("6", Fonts.BED_STATUS, 32),
    BG_64("7", Fonts.BED_STATUS, 64),
    BG_128("8", Fonts.BED_STATUS, 128),
    BG_START("s", Fonts.BED_STATUS, 4),
    BG_END("e", Fonts.BED_STATUS, 4),

    CHAR_0(")", Fonts.BED_STATUS, 16),
    CHAR_1("!", Fonts.BED_STATUS, 16),
    CHAR_2("@", Fonts.BED_STATUS, 16),
    CHAR_3("#", Fonts.BED_STATUS, 16),
    CHAR_4("$", Fonts.BED_STATUS, 16),
    CHAR_5("%", Fonts.BED_STATUS, 16),
    CHAR_6("^", Fonts.BED_STATUS, 16),
    CHAR_7("&", Fonts.BED_STATUS, 16),
    CHAR_8("*", Fonts.BED_STATUS, 16),
    CHAR_9("(", Fonts.BED_STATUS, 16),
    CHAR_DOTS(":", Fonts.BED_STATUS, 16),

    SPACE(" ", Fonts.SPACE, 1);

    private final String character;
    private final Fonts font;
    private final Integer width;

    StatusChars(String character, Fonts font, Integer width) {
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
        Map<String, StatusChars> map = Map.of("0", StatusChars.CHAR_0, "1", StatusChars.CHAR_1, "2", StatusChars.CHAR_2, "3", StatusChars.CHAR_3, "4", StatusChars.CHAR_4, "5", StatusChars.CHAR_5, "6", StatusChars.CHAR_6, "7", StatusChars.CHAR_7, "8", StatusChars.CHAR_8, "9", StatusChars.CHAR_9);
        String value = number.toString();
        Integer length = 0;
        for (int i = 0; i < value.length(); i++) {
            StatusChars statusChars = map.get(String.valueOf(value.charAt(i)));
            string.append(Component.text(statusChars.getCharacter()).style((style) -> {
                style.font(statusChars.getFont().getKey());
            }));
            length += statusChars.getWidht();
        }
        return length;
    }

    public static Integer parceNumber(TextComponent.Builder string, String number) {
        Map<String, StatusChars> map = Map.of("0", StatusChars.CHAR_0, "1", StatusChars.CHAR_1, "2", StatusChars.CHAR_2, "3", StatusChars.CHAR_3, "4", StatusChars.CHAR_4, "5", StatusChars.CHAR_5, "6", StatusChars.CHAR_6, "7", StatusChars.CHAR_7, "8", StatusChars.CHAR_8, "9", StatusChars.CHAR_9);
        Integer length = 0;
        for (int i = 0; i < number.length(); i++) {
            StatusChars statusChars = map.get(String.valueOf(number.charAt(i)));
            string.append(Component.text(statusChars.getCharacter()).style((style) -> {
                style.font(statusChars.getFont().getKey());
            }));
            length += statusChars.getWidht();
        }
        return length;
    }
}
