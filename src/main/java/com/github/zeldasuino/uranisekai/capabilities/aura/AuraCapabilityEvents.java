package com.github.zeldasuino.uranisekai.capabilities.aura;

import com.github.zeldasuino.uranisekai.UraniSekai;
import com.github.zeldasuino.uranisekai.networking.ModMessages;
import com.github.zeldasuino.uranisekai.networking.packets.AuraSyncS2C;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class AuraCapabilityEvents {

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if (event.isWasDeath()){
            event.getOriginal().getCapability(AuraProvider.AURA).ifPresent(oldStore -> {
                event.getEntity().getCapability(AuraProvider.AURA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void a(LivingDamageEvent e){
        if (e.getSource().getEntity() != null && e.getEntity() != null) {

            Entity source = e.getSource().getEntity();
            Entity target = e.getEntity();
            ServerLevel level = (ServerLevel) target.level();


            try {
                source.getCapability(AuraProvider.AURA).ifPresent( (aura) -> {


                            if (aura.isAuraUser) {
                                int bonus = new Random().nextInt(20);

                                UraniSekai.LOGGER.info(
                                        "Aura User " + source.getName().getString() + " " +
                                        "Attacked " + target.getName().getString() + " " +
                                        "Extra Damage: " + bonus
                        );
                                e.setAmount(e.getAmount() + bonus);

                                if (!level.isClientSide()) {
                                    aura.setAuraGauge(aura.getAuraGauge() - 1);

                                if (source instanceof Player p) {
                                    ModMessages.sendToPlayer(new AuraSyncS2C(aura), (ServerPlayer) p);
                                    p.sendSystemMessage(Component.literal("Aura Gauge: " + aura.getAuraGauge()));
                                }
                                }


                        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                                        target.position().x(),
                                target.position().y() + target.getBbHeight()/2,
                                        target.position().z(),
                                bonus*2, 0, 0, 0, 1);
                        }
                }
                );
            }
            catch (NullPointerException exc){
                UraniSekai.LOGGER.warn(exc.getMessage());
            }
        }
    }
    
}
