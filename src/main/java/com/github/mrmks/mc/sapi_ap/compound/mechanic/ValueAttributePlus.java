package com.github.mrmks.mc.sapi_ap.compound.mechanic;

import com.github.mrmks.mc.sapi_ap.EditorOptionHelper;
import com.github.mrmks.mc.sapi_ap.Hooks;
import com.github.mrmks.mc.sapi_ap.compound.CustomMechanic;
import com.google.common.collect.ImmutableList;
import com.sucy.skill.dynamic.DynamicSkill;
import com.sucy.skill.dynamic.custom.EditorOption;
import org.bukkit.entity.LivingEntity;

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
                EditorOptionHelper.text(ATTR_KEY, "The name of the attribute used in AttributePlus, please DO NOT append with \\\"[0]\\\" or \\\"[1]\\\"", ""),
                EditorOptionHelper.dropdown(MIN_MAX, "Min_Max", "Select min or max value will be stored", ImmutableList.of("min", "max")),
                EditorOptionHelper.dropdown(RANDOM, "Generate a random value between min and max? Option Min_Max will be ignored if set this to true", ImmutableList.of("false", "true")),
                EditorOptionHelper.text(KEY, "The unique key to store the value under", "key")
        );
    }

    @Override
    public boolean execute(LivingEntity livingEntity, int i, List<LivingEntity> list) {

        if (livingEntity == null || list.size() == 0) return false;

        LivingEntity entity = list.get(0);
        String attrKey = settings.getString(ATTR_KEY);
        boolean random = settings.getBool(RANDOM, false);
        boolean minMax = settings.getString(MIN_MAX).equalsIgnoreCase("min");
        String key = settings.getString(KEY, "key");

        HashMap<String, Object> map = DynamicSkill.getCastData(livingEntity);

        if (attrKey == null || map == null) return false;

        double res;
        double[] vs = Hooks.getAttribute(entity, attrKey);
        if (vs[0] == vs[1]) {
            res = vs[0];
        } else {
            if (random) res = Rand.nextDouble() * (vs[1] - vs[0]) + vs[0];
            else {
                boolean t = vs[0] < vs[1];
                res = minMax ? (t ? vs[0] : vs[1]) : (t ? vs[1] : vs[0]);
            }
        }
        map.put(key, res);

        return true;
    }
}
