package com.github.mrmks.mc.sapi_ap.bridge;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class BridgeDamageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private EntityDamageEvent event;
    public BridgeDamageEvent(EntityDamageEvent event) {
        this.event = event;
    }

    public EntityDamageEvent getEvent() {
        return event;
    }

    @Override
    public boolean isCancelled() {
        return event.isCancelled();
    }

    @Override
    public void setCancelled(boolean b) {
        event.setCancelled(b);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
