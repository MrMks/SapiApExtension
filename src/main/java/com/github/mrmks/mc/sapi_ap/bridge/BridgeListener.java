package com.github.mrmks.mc.sapi_ap.bridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class BridgeListener implements Listener {

    /**
     * The Listener of AttributePlus is set up on HIGHEST priority, And this plugin is depend on AttributePlus,
     * So when the bukkit event system registering the listeners in handleList, this listener will appears after AP's listener.
     * In this way, we can know how many damage will be dealt to the entity
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamaged(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) return;
        if (event instanceof EntityDamageByEntityEvent) {
            call(new BridgeDamageEntityEvent((EntityDamageByEntityEvent) event));
        } else if (event instanceof EntityDamageByBlockEvent) {
            call(new BridgeDamageBlockEvent((EntityDamageByBlockEvent) event));
        } else {
            call(new BridgeDamageEvent(event));
        }
    }

    private void call(BridgeDamageEvent event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}
