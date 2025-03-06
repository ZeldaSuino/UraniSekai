package com.github.zeldasuino.uranisekai.networking.packets;

import com.github.zeldasuino.uranisekai.capabilities.aura.AuraCapability;
import com.github.zeldasuino.uranisekai.capabilities.aura.AuraProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AuraSyncS2C extends Packet {
    public AuraCapability aura;

    public AuraSyncS2C(AuraCapability a){
        super();
        aura = a;
    }

    public AuraSyncS2C(FriendlyByteBuf buf){
        super(buf);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(aura.hasAura());
        buf.writeInt(aura.getAuraRank());
        buf.writeInt(aura.getAuraType());
        buf.writeInt(aura.getAuraGauge());
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        aura = new AuraCapability();
        aura.isAuraUser = buf.readBoolean();
        aura.setAuraRank(buf.readInt());
        aura.setAuraType(buf.readInt());
        aura.setAuraGauge(buf.readInt());
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

        }
        );
        return true;
    }
}
