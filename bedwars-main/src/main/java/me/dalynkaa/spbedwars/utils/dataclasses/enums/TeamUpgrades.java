package me.dalynkaa.spbedwars.utils.dataclasses.enums;

public enum TeamUpgrades {
    SHARPNESS("Острота", 4),
    REINFORCED_ARMOR1("Укрепленная броня I", 2),
    REINFORCED_ARMOR2("Укрепленная броня II", 4),
    REINFORCED_ARMOR3("Укрепленная броня III", 8),
    REINFORCED_ARMOR4("Укрепленная броня IV", 16),
    SPAWNER1("Спавнер I", 2),
    SPAWNER2("Спавнер II", 4),
    SPAWNER3("Спавнер III", 6),
    SPAWNER4("Спавнер IV", 8),
    MINER1("Шахтер I", 2),
    MINER2("Шахтер II", 4);

    private final String name;
    private final Integer costAmount;

    TeamUpgrades(String name, Integer costAmount) {
        this.name = name;
        this.costAmount = costAmount;
    }

    public String getName() {
        return name;
    }

    public Integer getCostAmount() {
        return costAmount;
    }
}
