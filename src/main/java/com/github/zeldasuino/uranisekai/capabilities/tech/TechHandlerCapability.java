package com.github.zeldasuino.uranisekai.capabilities.tech;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TechHandlerCapability{

    TechHandlerCapability(){
        inventory = new ItemStackHandler(3);
        inventory.setStackInSlot(1, Items.STICK.getDefaultInstance());
    }

    public ItemStackHandler inventory;

    public void copyFrom(TechHandlerCapability tech){
        this.inventory = tech.inventory;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt = inventory.serializeNBT();
    }
    public void loadNBTData(CompoundTag nbt){
        inventory.deserializeNBT(nbt);
    }

}
