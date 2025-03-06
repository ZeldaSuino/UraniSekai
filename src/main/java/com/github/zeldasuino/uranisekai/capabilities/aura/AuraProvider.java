package com.github.zeldasuino.uranisekai.capabilities.aura;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AuraProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>{
    // tokenzinho usado pelo manager pra procurar pela tua capabilidade

    public static Capability<AuraCapability> AURA = CapabilityManager.get(new CapabilityToken<AuraCapability>() { });

    // Se a entidade tiver uma referência nula
    // da tua capability tu cria algo novo
    // senão, tu retorna a aura atual.
    private AuraCapability aura;
    private final LazyOptional<AuraCapability> optional = LazyOptional.of(this::createObj);

    private AuraCapability createObj(){
        if (aura == null){
            aura = new AuraCapability();
        }
        return aura;
    }

    // Função que vai ser usada pra procurar as capabilities de uma entidade
    // Bota aqui pra retornar (um optional) da sua capability se o token bater
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == AURA){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createObj().saveNBTData(nbt);
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        createObj().loadNBTData(nbt);
    }

}
