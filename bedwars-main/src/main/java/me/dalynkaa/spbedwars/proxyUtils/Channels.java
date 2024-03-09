package me.dalynkaa.spbedwars.proxyUtils;

public enum Channels {
    SERVER_REGISTRATION("spbedwars:server_registration"),
    SERVER_UNREGISTRATION("spbedwars:server_unregistration"),
    SERVERS_LIST("spbedwars:servers_list"),
    SERVER_EDIT("spbedwars:server_edit"),
    SERVER_REGISTRATION_REQUEST("spbedwars:server_registration_request"),
    GAME_REGISTRATION("spbedwars:game_registration"),
    GAME_UNREGISTRATION("spbedwars:game_unregistration"),
    GAME_UPDATE("spbedwars:game_update"),
    GAMES_LIST("spbedwars:games_list"),
    GAME_REGISTRATION_REQUEST("spbedwars:game_registration_request"),
    GAME_JOIN("spbedwars:game_join");

    private final String channel;

    Channels(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }
}
