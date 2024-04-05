package me.dalynkaa.spbedwars.huds.utils.hudConstans;

import me.dalynkaa.spbedwars.utils.dataclasses.enums.Teams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.Map;

public enum WaitingChars {
    BACKGROUND('w', 'w', 'w', 'w', Fonts.GAME_WAITING, 169),
    GAME_WAITING('r', '|', '\u0086', '\u0090', Fonts.GAME_WAITING, 127),
    SLASH('R', '\\', 'f', 'p', Fonts.GAME_WAITING, 18),
    CHAR_0('\uE000', '\uE00A', '\uE014', '\uE01E', Fonts.GAME_WAITING, 16),
    CHAR_1('\uE001', '\uE00B', '\uE015', '\uE01F', Fonts.GAME_WAITING, 16),
    CHAR_2('\uE002', '\uE00C', '\uE016', '\uE020', Fonts.GAME_WAITING, 16),
    CHAR_3('\uE003', '\uE00D', '\uE017', '\uE021', Fonts.GAME_WAITING, 16),
    CHAR_4('\uE004', '\uE00E', '\uE018', '\uE022', Fonts.GAME_WAITING, 16),
    CHAR_5('\uE005', '\uE00F', '\uE019', '\uE023', Fonts.GAME_WAITING, 16),
    CHAR_6('\uE006', '\uE010', '\uE01A', '\uE024', Fonts.GAME_WAITING, 16),
    CHAR_7('\uE007', '\uE011', '\uE01B', '\uE025', Fonts.GAME_WAITING, 16),
    CHAR_8('\uE008', '\uE012', '\uE01C', '\uE026', Fonts.GAME_WAITING, 16),
    CHAR_9('\uE009', '\uE013', '\uE01D', '\uE027', Fonts.GAME_WAITING, 16);
    private final Character red;
    private final Character blue;
    private final Character green;
    private final Character yellow;

    private final Fonts font;
    private final Integer width;
    private static final Map<Character, WaitingChars> numberMap = Map.of('0', CHAR_0, '1', CHAR_1, '2', CHAR_2, '3', CHAR_3, '4', CHAR_4, '5', CHAR_5, '6', CHAR_6, '7', CHAR_7, '8', CHAR_8, '9', CHAR_9);

    WaitingChars(Character red, Character blue, Character green, Character yellow, Fonts font, Integer width) {
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.yellow = yellow;
        this.font = font;
        this.width = width;
    }

    private Character[] getCharacterList() {
        return new Character[]{red, blue, green, yellow};
    }

    public String getString(Teams teams) {
        return switch (teams) {
            case RED -> red.toString();
            case BLUE -> blue.toString();
            case GREEN -> green.toString();
            case YELLOW -> yellow.toString();
            default -> throw new IllegalStateException("Unexpected value: " + teams);
        };
    }

    private Integer getCharacterAsInt() {
        return Character.getNumericValue(red);
    }

    public Character getColoredCharacter(Teams team) {
        return switch (team) {
            case RED -> red;
            case BLUE -> blue;
            case GREEN -> green;
            case YELLOW -> yellow;
            default -> throw new IllegalStateException("Unexpected value: " + team);
        };
    }

    public Fonts getFont() {
        return font;
    }

    public Integer getWidth() {
        return width;
    }

    public static Integer parseNumber(TextComponent.Builder string, Integer number, Teams team) {
        Integer width = 0;
        String value = number.toString();
        for (int i = 0; i < value.length(); i++) {
            WaitingChars statusChars = numberMap.get(value.charAt(i));
            width += statusChars.width;
            string.append(Component.text(statusChars.getString(team)).style((style) -> style.font(statusChars.getFont().getKey())));
        }
        return width;
    }
}
