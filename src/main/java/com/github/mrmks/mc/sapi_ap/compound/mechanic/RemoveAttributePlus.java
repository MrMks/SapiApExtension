package com.github.mrmks.mc.sapi_ap.compound.mechanic;

import com.github.mrmks.mc.sapi_ap.compound.CustomMechanic;
import com.google.common.collect.ImmutableList;
import com.sucy.skill.dynamic.custom.EditorOption;
import org.bukkit.entity.LivingEntity;
import org.serverct.ersha.jd.api.EntityAttributeAPI;

import java.util.List;

public class RemoveAttributePlus extends CustomMechanic {

    private static final String SOURCE = "source";

    @Override
    public String getKey() {
        return "remove attributePlus";
    }

    @Override
    public String getDisplayName() {
        return "Remove AttributePlus";
    }

    @Override
    public String getDescription() {
        return "Remove all attributes from the given source";
    }

    @Override
    public List<EditorOption> getOptions() {
        return ImmutableList.of(
                EditorOption.text(SOURCE, "Source", "[" + SOURCE + "] The source of the attribute", "skill")
        );
    }

    @Override
    public boolean execute(LivingEntity livingEntity, int i, List<LivingEntity> list) {
        String source = settings.getString(SOURCE);
        for (LivingEntity le : list) {
            EntityAttributeAPI.removeEntityAttribute(le, source);
        }
        return true;
    }
}
