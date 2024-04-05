package me.dalynkaa.spbedwars.huds.utils.hudConstans;

public enum Background {
    BG_1("1", Fonts.BED_STATUS, 1),
    BG_2("2", Fonts.BED_STATUS, 2),
    BG_4("3", Fonts.BED_STATUS, 4),
    BG_8("4", Fonts.BED_STATUS, 8),
    BG_16("5", Fonts.BED_STATUS, 16),
    BG_32("6", Fonts.BED_STATUS, 32),
    BG_64("7", Fonts.BED_STATUS, 64),
    BG_128("8", Fonts.BED_STATUS, 128),
    BG_START("s", Fonts.BED_STATUS, 4),
    BG_END("e", Fonts.BED_STATUS, 4);

    private final String character;
    private final Fonts font;
    private final Integer width;

    Background(String character, Fonts font, Integer width) {
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
}
