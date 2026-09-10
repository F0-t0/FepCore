package pl.fepbox.core.utils;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MiniMessageUtils {
    public static Component deserialize(String input) {
        return MiniMessage.miniMessage().deserialize(input);
    }
    public static List<Component> deserialize(List<String> input) {
        List<Component> lists = new ArrayList<>();
        for (String line : input) {
            lists.add(MiniMessage.miniMessage().deserialize(line));
        }
        return lists;
    }
}
