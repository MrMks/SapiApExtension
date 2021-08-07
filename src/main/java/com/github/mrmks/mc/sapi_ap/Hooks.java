package com.github.mrmks.mc.sapi_ap;

import org.bukkit.entity.LivingEntity;
import org.serverct.ersha.jd.AttributeAPI;
import org.serverct.ersha.jd.api.EntityAttributeAPI;
import org.serverct.ersha.jd.attribute.AttributeData;

import java.util.Collections;

public class Hooks {
    public static void addAttribute(LivingEntity living, String source, String key, double value) {
        EntityAttributeAPI.addEntityAttribute(living, source, Collections.singletonList(key + ": " + value), false);
    }

    public static void removeAttribute(LivingEntity living, String source) {
        EntityAttributeAPI.removeEntityAttribute(living, source);
    }

    public static double[] getAttribute(LivingEntity living, String key) {
        AttributeData data = AttributeAPI.getAttrData(living);
        Number[] values = data.getAttributeValues(key);
        if (values.length == 0) return new double[]{0,0};
        if (values.length == 1) {
            double v = values[0].doubleValue();
            return new double[]{v, v};
        }
        return new double[]{values[0].doubleValue(), values[1].doubleValue()};
    }
}
