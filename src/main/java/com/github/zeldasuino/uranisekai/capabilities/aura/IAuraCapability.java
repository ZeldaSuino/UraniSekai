package com.github.zeldasuino.uranisekai.capabilities.aura;

public interface IAuraCapability {
    boolean hasAura();
    int getAuraRank();
    int getAuraType();
    int getAuraGauge();
    void setAuraRank(int in);
    void setAuraType(int in);
    void setAuraGauge(int in);

}
