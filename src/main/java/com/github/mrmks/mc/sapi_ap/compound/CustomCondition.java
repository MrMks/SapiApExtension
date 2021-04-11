package com.github.mrmks.mc.sapi_ap.compound;

import com.sucy.skill.dynamic.ComponentType;
import com.sucy.skill.dynamic.custom.CustomEffectComponent;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public abstract class CustomCondition extends CustomEffectComponent {
    @Override
    public ComponentType getType() {
        return ComponentType.CONDITION;
    }

    @Override
    public boolean execute(LivingEntity livingEntity, int i, List<LivingEntity> list) {
        return test(livingEntity, i, list) && executeChildren(livingEntity, i, list);
    }

    protected abstract boolean test(LivingEntity caster, int level, List<LivingEntity> targets);
}
