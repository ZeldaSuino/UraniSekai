package com.github.zeldasuino.uranisekai.capabilities.aura;

import net.minecraft.nbt.CompoundTag;

public class AuraCapability implements IAuraCapability {

    public boolean isAuraUser = true;
    int auraRank;
    int auraType;
    int auraGauge;

    public void copyFrom(AuraCapability ac) {
        this.isAuraUser = ac.isAuraUser;
        this.auraRank = ac.auraRank;
        this.auraType = ac.auraType;
        this.auraGauge = ac.auraGauge;
    }

    public boolean hasAura() {
        return isAuraUser;
    }
    public int getAuraRank() {
        return auraRank;
    }
    public int getAuraType() {
        return auraType;
    }
    public int getAuraGauge() {
        return auraGauge;
    }
    public void setAuraRank(int in) {
        this.auraRank = in;
    }
    public void setAuraType(int in) {
        this.auraType = in;
    }
    public void setAuraGauge(int in) {
        this.auraGauge = in;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putBoolean("auraUser", isAuraUser);
        nbt.putInt("auraRank", auraRank);
        nbt.putInt("auraType", auraType);
        nbt.putInt("auraGauge", auraGauge);
    }

    public void loadNBTData(CompoundTag nbt){
        isAuraUser = nbt.getBoolean("auraUser");
        auraRank = nbt.getInt("auraRank");
        auraType = nbt.getInt("auraType");
        auraGauge = nbt.getInt("auraGauge");
    }



}

