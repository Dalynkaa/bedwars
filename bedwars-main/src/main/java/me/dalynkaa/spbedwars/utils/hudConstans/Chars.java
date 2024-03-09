package me.dalynkaa.spbedwars.utils.hudConstans;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.Map;

public enum Chars {
    BED_RED("R", Fonts.BED_STATUS, 16),
    BED_BLUE("B", Fonts.BED_STATUS, 16),
    BED_GREEN("G", Fonts.BED_STATUS, 16),
    BED_YELLOW("Y", Fonts.BED_STATUS, 16),
    YES_CHECK("T", Fonts.BED_STATUS, 16),
    NO_CROSS("F", Fonts.BED_STATUS, 16),

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

    CHAR_1("!", Fonts.BED_STATUS, 16),
    CHAR_2("@", Fonts.BED_STATUS, 16),
    CHAR_3("#", Fonts.BED_STATUS, 16),
    CHAR_4("$", Fonts.BED_STATUS, 16),
    CHAR_5("%", Fonts.BED_STATUS, 16),
    CHAR_6("^", Fonts.BED_STATUS, 16),
    CHAR_7("&", Fonts.BED_STATUS, 16),
    CHAR_8("*", Fonts.BED_STATUS, 16),

    SPACE(" ", Fonts.SPACE, 1);

    private final String character;
    private final Fonts font;
    private final Integer width;

    Chars(String character, Fonts font, Integer width) {
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
        Map<String, Chars> map = Map.of("1", Chars.CHAR_1, "2", Chars.CHAR_2, "3", Chars.CHAR_3, "4", Chars.CHAR_4, "5", Chars.CHAR_5, "6", Chars.CHAR_6, "7", Chars.CHAR_7, "8", Chars.CHAR_8);
        String value = number.toString();
        Integer length = 0;
        for (int i = 0; i < value.length(); i++) {
            Chars chars = map.get(String.valueOf(value.charAt(i)));
            string.append(Component.text(chars.getCharacter()).style((style) -> {
                style.font(chars.getFont().getKey());
            }));
            length += chars.getWidht();
        }
        return length;
    }
}
