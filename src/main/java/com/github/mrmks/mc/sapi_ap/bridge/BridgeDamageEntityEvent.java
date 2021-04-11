package com.github.mrmks.mc.sapi_ap.bridge;

import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BridgeDamageEntityEvent extends BridgeDamageEvent {

    private final static HandlerList handlers = new HandlerList();

    private EntityDamageByEntityEvent event;
    public BridgeDamageEntityEvent(EntityDamageByEntityEvent event) {
        super(event);
        this.event = event;
    }

    @Override
    public EntityDamageByEntityEvent getEvent() {
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
