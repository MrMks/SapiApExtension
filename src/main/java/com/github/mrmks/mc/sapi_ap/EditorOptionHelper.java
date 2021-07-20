package com.github.mrmks.mc.sapi_ap;

import com.sucy.skill.dynamic.custom.EditorOption;

import java.util.List;

public class EditorOptionHelper {
    public static EditorOption number(String key, String name, String desc, double base, double scale) {
        return EditorOption.number(key, name, formatDesc(key, desc), base, scale);
    }

    public static EditorOption number(String key, String desc, double base, double scale) {
        return number(key, formatName(key), desc, base, scale);
    }

    public static EditorOption text(String key, String name, String desc, String text) {
        return EditorOption.text(key, name, formatDesc(key, desc), text);
    }

    public static EditorOption text(String key, String desc, String text) {
        return text(key, formatName(key), desc, text);
    }

    public static EditorOption dropdown(String key, String name, String desc, List<String> list) {
        return EditorOption.dropdown(key, name, formatDesc(key, desc), list);
    }

    public static EditorOption dropdown(String key, String desc, List<String> list) {
        return dropdown(key, formatName(key), desc, list);
    }

    public static EditorOption list(String key, String name, String desc, List<String> list) {
        return EditorOption.list(key, name, formatDesc(key, desc), list);
    }

    public static EditorOption list(String key, String desc, List<String> list) {
        return list(key, formatName(key), desc, list);
    }

    public static String formatDesc(String key, String desc) {
        if (key == null) {
            return desc == null ? "" : desc;
        }
        String prefix = "[" + key + "]";
        if (desc == null || desc.isEmpty()) return prefix;
        return desc.startsWith(prefix) ? desc : prefix + " " + desc;
    }

    public static String formatName(String key) {
        if (key == null || key.isEmpty()) return "";
        if (key.length() == 1) return key.toUpperCase();
        return key.substring(0,1).toUpperCase() + key.substring(1);
    }
}
