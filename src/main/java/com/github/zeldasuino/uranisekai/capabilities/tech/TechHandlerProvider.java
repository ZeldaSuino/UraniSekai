package com.github.zeldasuino.uranisekai.capabilities.tech;

import com.github.zeldasuino.uranisekai.capabilities.aura.AuraCapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class TechHandlerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<TechHandlerCapability> TECH = CapabilityManager.get(new CapabilityToken<TechHandlerCapability>() { });

    LazyOptional<TechHandlerCapability> holder = LazyOptional.of(this::getHandler);
    TechHandlerCapability handler;

    private TechHandlerCapability getHandler() {
        if (handler == null) {
            handler = new TechHandlerCapability();
        }
        return handler;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return (cap == TECH) ? holder.cast() : LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        getHandler().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getHandler().loadNBTData(nbt);
    }
}
