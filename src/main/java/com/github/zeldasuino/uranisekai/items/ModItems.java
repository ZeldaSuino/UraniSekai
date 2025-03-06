package com.github.zeldasuino.uranisekai.items;

import com.github.zeldasuino.uranisekai.UraniSekai;
import com.github.zeldasuino.uranisekai.capabilities.aura.AuraProvider;
import com.github.zeldasuino.uranisekai.capabilities.tech.TechHandlerProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UraniSekai.MODID);

    public static final RegistryObject<Item> LAMP = ITEMS.register("lamp", () -> new LampItem( new Item.Properties()
                                                                    .stacksTo(1)));
    public static final RegistryObject<Item> TALISMAN = ITEMS.register("talisman", () -> new TalismanItem(new Item.Properties()
                                                                    .stacksTo(1)));
    public static final RegistryObject<Item> SCROLL = ITEMS.register("scroll", () -> new Item(new Item.Properties()
            .stacksTo(16)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
class TalismanItem extends Item {

    TalismanItem(Properties p){
        super(p);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        boolean crouch = player.isCrouching();
        AtomicReference<ItemStack> buffer;
        if (!crouch)
            player.getCapability(AuraProvider.AURA).ifPresent(
                    (aura) -> player.getCapability(TechHandlerProvider.TECH).ifPresent(
                    (tech) -> {

                 buffer.set(tech.inventory.getStackInSlot(1));

            }));

        return super.use(level, player, hand);
    }
}
class LampItem extends Item{

    public LampItem(Properties p) {
        super(p);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack p = super.getDefaultInstance();
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("isLit", false);
        p.addTagElement("uranisekai", nbt);
        return p;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext p) {

        if (p.getPlayer() == null) return InteractionResult.FAIL;

        ItemStack x = p.getItemInHand();
        CompoundTag nbt = new CompoundTag();
        boolean firstuse_flag = false;

        if (!p.getItemInHand().hasTag()){
            firstuse_flag = true;
            nbt.putBoolean("isLit", false);
            x.addTagElement("uranisekai", nbt);
        }
        else{
            nbt = x.getTag();
        }

        if (!firstuse_flag) nbt.putBoolean("isLit", !nbt.getBoolean("isLit"));
        p.getPlayer().sendSystemMessage(Component.literal("server " + nbt.getBoolean("isLit")));

        x.setTag(nbt);

//        p.getPlayer().getCapability(AuraProvider.AURA).ifPresent( (aura) ->
//                UraniSekai.LOGGER.info("Aura Gauge: " + aura.getAuraGauge()));


        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player p) {
            Level l = p.level();
            BlockHitResult bh = getPlayerPOVHitResult(p.level(), p, ClipContext.Fluid.SOURCE_ONLY);

            AABB faiaaa = new AABB(bh.getLocation(), bh.getLocation());
            faiaaa = faiaaa.inflate(1.5);
            if (!l.isClientSide) {
                ServerLevel sl = (ServerLevel) l;
                SimpleParticleType t = ParticleTypes.SMALL_FLAME;
                sl.sendParticles(t,
                        faiaaa.minX, faiaaa.minY, faiaaa.minZ,
                        5, 0, 0, 0, 0.05);
                sl.sendParticles(t,
                        faiaaa.maxX, faiaaa.maxY, faiaaa.maxZ,
                        5, 0, 0, 0, 0.05);
                sl.sendParticles(t,
                        faiaaa.maxX, faiaaa.minY, faiaaa.minZ,
                        5, 0, 0, 0, 0.05);
                sl.sendParticles(t,
                        faiaaa.minX, faiaaa.maxY, faiaaa.minZ,
                        5, 0, 0, 0, 0.05);
                sl.sendParticles(t,
                        faiaaa.minX, faiaaa.minY, faiaaa.maxZ,
                        5, 0, 0, 0, 0.05);
                sl.sendParticles(t,
                        faiaaa.maxX, faiaaa.maxY, faiaaa.minZ,
                        5, 0, 0, 0, 0.05);
                sl.sendParticles(t,
                        faiaaa.minX, faiaaa.maxY, faiaaa.maxZ,
                        5, 0, 0, 0, 0.05);
                sl.sendParticles(t,
                        faiaaa.maxX, faiaaa.minY, faiaaa.maxZ,
                        5, 0, 0, 0, 0.05);
            }

            AABB finalFaiaaa = faiaaa;
            l.getEntities(p, faiaaa).stream().filter((e) -> e instanceof LivingEntity).forEach((e) -> {
                if (finalFaiaaa.intersect(e.getBoundingBox()).getCenter().distanceTo(finalFaiaaa.getCenter()) <= 2.5)
                    e.setSecondsOnFire(3);
            });
        }
        return super.onEntitySwing(stack, entity);
    }
}

