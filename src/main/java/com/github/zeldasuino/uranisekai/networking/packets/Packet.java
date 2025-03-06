package com.github.zeldasuino.uranisekai.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class Packet implements IPacket {

    public Packet(){}

    public Packet(FriendlyByteBuf buf){
        decode(buf);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void decode(FriendlyByteBuf buf) {

    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        return false;
    }
}
