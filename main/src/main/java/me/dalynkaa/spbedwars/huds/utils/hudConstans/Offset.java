package me.dalynkaa.spbedwars.huds.utils.hudConstans;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.List;

public class Offset {
    public static void pushHorizontalOffset(TextComponent.Builder string, long offset) {
        final char[] POSITIVE_OFFSET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        final char[] NEGATIVE_OFFSET = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        StringBuilder offsetString = new StringBuilder();

        while (offset != 0) {
            long absOffset = Math.abs(offset);

            int bit = Long.SIZE - Long.numberOfLeadingZeros(absOffset);

            long greater = 1L << bit;
            long lesser = 1L << (bit - 1);

            long greaterResult = greater - absOffset;
            long lesserResult = lesser - absOffset;

            if (Long.bitCount(greaterResult) < Long.bitCount(lesserResult)) {
                if (offset < 0) {
                    offset += greater;
                    offsetString.append(POSITIVE_OFFSET[bit]);
                    string.append(Component.text(POSITIVE_OFFSET[bit]).style((style) -> style.font(Fonts.SPACE.getKey())));
                } else {
                    offset -= greater;
                    offsetString.append(NEGATIVE_OFFSET[bit]);
                    string.append(Component.text(NEGATIVE_OFFSET[bit]).style((style) -> style.font(Fonts.SPACE.getKey())));
                }
            } else {
                if (offset < 0) {
                    offset += lesser;
                    offsetString.append(POSITIVE_OFFSET[bit - 1]);
                    string.append(Component.text(POSITIVE_OFFSET[bit - 1]).style((style) -> style.font(Fonts.SPACE.getKey())));
                } else {
                    offset -= lesser;
                    offsetString.append(NEGATIVE_OFFSET[bit - 1]);
                    string.append(Component.text(NEGATIVE_OFFSET[bit - 1]).style((style) -> style.font(Fonts.SPACE.getKey())));
                }
            }
        }
        //Logger.debug("Offset: " + offsetString + " (" + offset + ")");
    }

    private static List<Integer> findCombination(int idx, int sum, int target) {
        int[] arr = {1, 2, 4, 8, 16, 32, 64, 128};
        if (sum == target) {
            return new ArrayList<>();
        }
        if (idx == arr.length || sum > target) {
            return null;
        }
        List<Integer> with = findCombination(idx + 1, sum + arr[idx], target);
        if (with != null) {
            with.add(idx);
            return with;
        }
        List<Integer> without = findCombination(idx + 1, sum, target);
        if (without != null) {
            return without;
        }
        return null;
    }

    public static Integer getBackground(TextComponent.Builder component, int target) {
        return getBackground(component, target, true, true);
    }

    public static Integer getBackground(TextComponent.Builder component, int target, boolean drawEnd, boolean drawStart) {
        Integer bgSize = 0;
        List<Integer> res = findCombination(0, 0, target);
        if (res == null) {
            return 0;
        }
        pushHorizontalOffset(component, -target);
        Background[] bg = {Background.BG_1, Background.BG_2, Background.BG_4, Background.BG_8, Background.BG_16, Background.BG_32, Background.BG_64, Background.BG_128};
        if (drawStart) {
            component.append(Component.text(Background.BG_START.getCharacter()).style((style) -> {
                style.font(Background.BG_START.getFont().getKey());
                style.color(TextColor.color(253, 254, 254));
            }));
            pushHorizontalOffset(component, -1);
            bgSize -= 1;
        }

        for (Integer re : res) {
            Background statusChars = bg[re];
            component.append(Component.text(statusChars.getCharacter()).style((style) -> {
                style.font(statusChars.getFont().getKey());
                style.color(TextColor.color(253, 254, 254));
            }));
            pushHorizontalOffset(component, -1);
            bgSize -= 1;
        }
        if (drawEnd) {
            component.append(Component.text(Background.BG_END.getCharacter()).style((style) -> {
                style.font(Background.BG_END.getFont().getKey());
                style.color(TextColor.color(253, 254, 254));
            }));
        }
        return bgSize;

    }

}
