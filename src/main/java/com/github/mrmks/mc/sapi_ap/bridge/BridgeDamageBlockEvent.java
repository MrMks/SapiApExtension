package com.github.mrmks.mc.sapi_ap.bridge;

import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class BridgeDamageBlockEvent extends BridgeDamageEvent {

    private static final HandlerList handlers = new HandlerList();

    private EntityDamageByBlockEvent event;
    public BridgeDamageBlockEvent(EntityDamageByBlockEvent event) {
        super(event);
        this.event = event;
    }

    @Override
    public EntityDamageByBlockEvent getEvent() {
        return event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
