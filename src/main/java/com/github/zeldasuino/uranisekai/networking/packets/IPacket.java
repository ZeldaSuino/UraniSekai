package com.github.zeldasuino.uranisekai.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacket {

    public void toBytes(FriendlyByteBuf buf);
    public void decode (FriendlyByteBuf buf);
    public boolean handle(Supplier<NetworkEvent.Context> supplier);

}
