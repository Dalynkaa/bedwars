package me.dalynkaa.spbedwars.utils.dataclasses.enums;

public enum ArenaTypes {
    SOLO("Одиночный", 4, 1),
    DUO("Дуо", 8, 2),
    TRIO("Трио", 12, 3),
    QUADRO("Четверной", 16, 4);

    private final Integer players;
    private final Integer playerForCommand;
    private final String translate;

    ArenaTypes(String translate, Integer players, Integer playerForCommand){
        this.translate = translate;
        this.players = players;
        this.playerForCommand = playerForCommand;
    }

    public Integer getPlayers() {
        return players;
    }


    public Integer getPlayerForCommand() {
        return playerForCommand;
    }


    public String getTranslate() {
        return translate;
    }

}
