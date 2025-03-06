package com.github.zeldasuino.uranisekai.events;

import com.github.zeldasuino.uranisekai.UraniSekai;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.capabilities.*;
import com.github.zeldasuino.uranisekai.capabilities.aura.*;
import com.github.zeldasuino.uranisekai.capabilities.tech.*;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class ModEvents {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(AuraCapability.class);
        event.register(TechHandlerCapability.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
        // Player
        if (event.getObject() instanceof Player p){
            if (!p.getCapability(AuraProvider.AURA).isPresent()){
                event.addCapability(new ResourceLocation(UraniSekai.MODID, "aura"), new AuraProvider());
            }
            if (!p.getCapability(TechHandlerProvider.TECH).isPresent()){
                event.addCapability(new ResourceLocation(UraniSekai.MODID, "tech"), new TechHandlerProvider());
            }
        }
    }


}
