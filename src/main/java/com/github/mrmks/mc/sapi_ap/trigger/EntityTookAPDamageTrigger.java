package com.github.mrmks.mc.sapi_ap.trigger;

import com.google.common.collect.ImmutableList;
import com.sucy.skill.api.Settings;
import com.sucy.skill.dynamic.ComponentType;
import com.sucy.skill.dynamic.custom.CustomTrigger;
import com.sucy.skill.dynamic.custom.EditorOption;
import org.bukkit.entity.LivingEntity;
import org.serverct.ersha.api.event.AttrEntityAttackEvent;

import java.util.List;
import java.util.Map;

public class EntityTookAPDamageTrigger implements CustomTrigger<AttrEntityAttackEvent> {
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
                EditorOption.dropdown(
                        "target",
                        "Target Caster",
                        "True makes children target the caster. False makes children target the entity.",
                        ImmutableList.of("True", "False")),
                EditorOption.dropdown(
                        "limit-min",
                        "Limit Min",
                        "[limit-min] Should restrict the lower limit?",
                        ImmutableList.of("True", "False")),
                EditorOption.number(
                        "dmg-min",
                        "Min Damage",
                        "The minimum damage that needs to be dealt",
                        0,
                        0),
                EditorOption.dropdown("" +
                        "limit-max",
                        "Limit Max",
                        "[limit-max] Should restrict the upper limit?",
                        ImmutableList.of("Ture", "False")),
                EditorOption.number(
                        "dmg-max",
                        "Max Damage",
                        "The maximum damage that can be dealt",
                        999,
                        0)
        );
    }

    @Override
    public Class<AttrEntityAttackEvent> getEvent() {
        return AttrEntityAttackEvent.class;
    }

    @Override
    public boolean shouldTrigger(AttrEntityAttackEvent event, int i, Settings settings) {
        double min = settings.getAttr("dmg-min", i, 0);
        double max = settings.getAttr("dmg-max", i, 999);
        boolean lmt_l = settings.getBool("limit-min", true);
        boolean lmt_u = settings.getBool("limit-max", true);
        double damage = event.getAttributeHandle().getDamage(event.getAttackerOrKiller());

        return (!lmt_l || damage >= min) && (!lmt_u || damage <= max);
    }

    @Override
    public void setValues(AttrEntityAttackEvent event, Map<String, Object> map) {
        map.put("api-taken", event.getAttributeHandle().getDamage(event.getAttackerOrKiller()));
    }

    @Override
    public LivingEntity getCaster(AttrEntityAttackEvent event) {
        return event.getEntity();
    }

    @Override
    public LivingEntity getTarget(AttrEntityAttackEvent event, Settings settings) {
        boolean isUsingTarget = settings.getString("target", "true").equalsIgnoreCase("false");
        return isUsingTarget ? event.getAttackerOrKiller() : event.getEntity();
    }
}
