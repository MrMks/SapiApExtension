package com.github.mrmks.mc.sapi_ap.compound.mechanic;

import com.github.mrmks.mc.sapi_ap.EditorOptionHelper;
import com.github.mrmks.mc.sapi_ap.Hooks;
import com.github.mrmks.mc.sapi_ap.compound.CustomMechanic;
import com.google.common.collect.ImmutableList;
import com.sucy.skill.dynamic.custom.EditorOption;
import org.bukkit.entity.LivingEntity;
import org.serverct.ersha.jd.AttributeAPI;
import org.serverct.ersha.jd.attribute.AttributeData;

import java.util.List;

public class AppendAttributePlus extends CustomMechanic {

    private static final String SOURCE = "source";
    private static final String ATTR = "attr";
    private static final String VALUE = "value";
    private static final String TIMEOUT = "timeout";

    @Override
    public String getKey() {
        return "append attributePlus";
    }

    @Override
    public String getDisplayName() {
        return "Append AttributePlus";
    }

    @Override
    public String getDescription() {
        return "Append an ap attribute to the target, the attribute will be kept until it has been removed";
    }

    @Override
    public List<EditorOption> getOptions() {
        return ImmutableList.of(
                EditorOptionHelper.text(SOURCE, "The source of the attribute", "skill"),
                EditorOptionHelper.text(ATTR, "Attribute", "The target attribute to append", ""),
                EditorOptionHelper.number(VALUE, "The value to be append", 0, 0),
                EditorOptionHelper.number(TIMEOUT, "Remove this source after timeout, set 0 or negative number to disable", 0,0)
        );
    }

    @Override
    public boolean execute(LivingEntity livingEntity, int i, List<LivingEntity> list) {
        String attr = settings.getString(ATTR);
        if (attr == null) return true;
        double value = parseValues(livingEntity, VALUE, i, 0);
        String src = settings.getString(SOURCE, "skill");
        double timeout = parseValues(livingEntity, TIMEOUT, i, 0);
        List<String> attrList = ImmutableList.of(attr + ": " + value);
        for(LivingEntity le : list) {
            Hooks.addAttribute(le, src, attr, value);
            AttributeData data = AttributeAPI.getAttrData(le);
            data.addApiAttribute(src, attrList, false);
        }
        return true;
    }
}
