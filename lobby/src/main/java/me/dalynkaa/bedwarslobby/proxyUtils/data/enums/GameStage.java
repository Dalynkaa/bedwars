package me.dalynkaa.bedwarslobby.proxyUtils.data.enums;

public enum GameStage {
    WAITING("Ожидание игроков..."),
    WAITING_TIMER("Ожидания начала..."),
    RUNNING("Игра"),
    GAME_END_CELEBRATING("Конец"),
    REBUILDING("Перестройка"),
    DISABLED("Выключенно!");

    private final String translated;

    GameStage(String translated) {
        this.translated = translated;
    }

    public String getTranslated() {
        return translated;
    }
}
