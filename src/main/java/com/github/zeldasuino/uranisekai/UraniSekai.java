package com.github.zeldasuino.uranisekai;

import com.github.zeldasuino.uranisekai.particles.AuraParticle;
import com.github.zeldasuino.uranisekai.particles.ModParticles;
import com.github.zeldasuino.uranisekai.blocks.ModBlocks;
import com.github.zeldasuino.uranisekai.capabilities.aura.AuraCapabilityEvents;
import com.github.zeldasuino.uranisekai.events.ModEvents;
import com.github.zeldasuino.uranisekai.items.ModItems;
import com.github.zeldasuino.uranisekai.networking.ModMessages;
import com.mojang.logging.LogUtils;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(UraniSekai.MODID)
public class UraniSekai
{
    public UraniSekai(FMLJavaModLoadingContext context)
    {
        IEventBus modEventbus = context.getModEventBus();

        ModBlocks.register(modEventbus);
        ModItems.register(modEventbus);

        ModMessages.register();
        ModParticles.register(modEventbus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(ModEvents.class);
        MinecraftForge.EVENT_BUS.register(AuraCapabilityEvents.class);

    }

    public static final String MODID = "uranisekai";

    public static final Logger LOGGER = LogUtils.getLogger();


    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.AURA_PARTICLE.get(), AuraParticle.Provider::new);
    }
}

