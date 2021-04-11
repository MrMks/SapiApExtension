package com.github.mrmks.mc.sapi_ap.compound.mechanic;

import com.github.mrmks.mc.sapi_ap.compound.CustomMechanic;
import com.google.common.collect.ImmutableList;
import com.sucy.skill.dynamic.DynamicSkill;
import com.sucy.skill.dynamic.custom.EditorOption;
import org.bukkit.entity.LivingEntity;
import org.serverct.ersha.jd.AttributeAPI;
import org.serverct.ersha.jd.attribute.AttributeData;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ValueAttributePlus extends CustomMechanic {

    private static final String ATTR_KEY = "attrKey";
    private static final String MIN_MAX = "min_max";
    private static final String RANDOM = "random";
    private static final String KEY = "key";

    private static final Random Rand = new Random();

    @Override
    public String getKey() {
        return "value attributePlus";
    }

    public String getDisplayName() {
        return "Value AttributePlus";
    }

    @Override
    public String getDescription() {
        return "Stores the target's attribute status from plugin AttributePlus as a value under a given key for the caster";
    }

    @Override
    public List<EditorOption> getOptions() {
        return ImmutableList.of(
                EditorOption.text(ATTR_KEY, "AttrKey", "[" + ATTR_KEY + "]The name of the attribute used in AttributePlus, please DO NOT append with \\\"[0]\\\" or \\\"[1]\\\"", ""),
                EditorOption.dropdown(MIN_MAX, "Min_Max", "["+MIN_MAX+"]Select min or max value will be stored", ImmutableList.of("min", "max")),
                EditorOption.dropdown(RANDOM, "Random", "["+RANDOM+"]Generate a random value between min and max? Option Min_Max will be ignored if set this to true", ImmutableList.of("false", "true")),
                EditorOption.text(KEY, "Key", "["+KEY+"]The unique key to store the value under", "key")
        );
    }

    @Override
    public boolean execute(LivingEntity livingEntity, int i, List<LivingEntity> list) {

        if (livingEntity == null || list.size() == 0) return false;

        LivingEntity entity = list.get(0);
        AttributeData data = AttributeAPI.getAttrData(entity);
        String attrKey = settings.getString(ATTR_KEY);
        boolean random = settings.getBool(RANDOM, false);
        boolean minMax = settings.getString(MIN_MAX).equalsIgnoreCase("min");
        String key = settings.getString(KEY, "key");

        HashMap<String, Object> map = DynamicSkill.getCastData(livingEntity);

        if (data == null || attrKey == null || map == null) return false;

        double res;
        if (data.getAttributeValue().containsKey(attrKey) && data.getAttributeValue().containsKey(attrKey+"[1]")) {
            Number[] doubles = data.getAttributeValues(attrKey);
            double min = doubles.length > 0 ? doubles[0].doubleValue() : 0;
            double max = doubles.length > 1 ? doubles[1].doubleValue() : 0;
            if (max == min) res = min;
            else {
                if (random) res = Rand.nextDouble() * (max - min) + min;
                else {
                    boolean tmp = min < max;
                    res = minMax ? (tmp ? min : max) : (tmp ? max : min);
                }
            }
        } else {
            res = data.getAttributeValue().containsKey(attrKey) ? data.getAttributeValue(attrKey) : data.getAttributeValue(attrKey+"[1]");
        }

        map.put(key, res);

        return true;
    }
}
