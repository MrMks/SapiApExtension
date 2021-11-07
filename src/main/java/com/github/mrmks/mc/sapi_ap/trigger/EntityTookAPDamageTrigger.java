package com.github.mrmks.mc.sapi_ap.trigger;

import com.github.mrmks.mc.sapi_ap.EditorOptionHelper;
import com.google.common.collect.ImmutableList;
import com.sucy.skill.api.Settings;
import com.sucy.skill.dynamic.ComponentType;
import com.sucy.skill.dynamic.DynamicSkill;
import com.sucy.skill.dynamic.custom.CustomTrigger;
import com.sucy.skill.dynamic.custom.EditorOption;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.serverct.ersha.jd.event.AttrEntityDamageEvent;

import java.util.List;
import java.util.Map;

public class EntityTookAPDamageTrigger implements CustomTrigger<AttrEntityDamageEvent> {
    @Override
    public String getKey() {
        return "TOOK_AP_DAMAGE";
    }

    @Override
    public String getDisplayName() {
        return "Took AP Damage";
    }

    @Override
    public ComponentType getType() {
        return ComponentType.TRIGGER;
    }

    @Override
    public String getDescription() {
        return "Applies skill effects when a player deals damage handled by AttributePlus.";
    }

    @Override
    public List<EditorOption> getOptions() {
        return ImmutableList.of(
                EditorOptionHelper.dropdown(
                        "target",
                        "Target Caster",
                        "True makes children target the caster. False makes children target the entity.",
                        ImmutableList.of("True", "False")),
                EditorOptionHelper.dropdown(
                        "limit-min",
                        "Limit Min",
                        "Should restrict the lower limit?",
                        ImmutableList.of("True", "False")),
                EditorOptionHelper.number(
                        "dmg-min",
                        "Min Damage",
                        "The minimum damage that needs to be dealt",
                        0,
                        0),
                EditorOptionHelper.dropdown("" +
                        "limit-max",
                        "Limit Max",
                        "Should restrict the upper limit?",
                        ImmutableList.of("Ture", "False")),
                EditorOptionHelper.number(
                        "dmg-max",
                        "Max Damage",
                        "The maximum damage that can be dealt",
                        999,
                        0)
        );
    }

    @Override
    public Class<AttrEntityDamageEvent> getEvent() {
        return AttrEntityDamageEvent.class;
    }

    @Override
    public boolean shouldTrigger(AttrEntityDamageEvent event, int i, Settings settings) {
        double min = settings.getAttr("dmg-min", i, 0);
        double max = settings.getAttr("dmg-max", i, 999);
        boolean lmt_l = settings.getBool("limit-min", true);
        boolean lmt_u = settings.getBool("limit-max", true);
        double damage = event.getDamage();

        return (!lmt_l || damage >= min) && (!lmt_u || damage <= max);
    }

    @Override
    public void setValues(AttrEntityDamageEvent event, Map<String, Object> map) {
        map.put("api-taken", event.getDamage());
    }

    @Override
    public LivingEntity getCaster(AttrEntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) return (LivingEntity) entity;
        else return null;
    }

    @Override
    public LivingEntity getTarget(AttrEntityDamageEvent event, Settings settings) {
        boolean isUsingTarget = settings.getString("target", "true").equalsIgnoreCase("false");
        Entity entity = isUsingTarget ? event.getDamager() : event.getEntity();
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    @Override
    public void postProcess(AttrEntityDamageEvent event, DynamicSkill skill) {
        if (skill.checkCancelled()) {
            event.setCancelled(true);
            skill.cancelTrigger();
        }
    }
}
