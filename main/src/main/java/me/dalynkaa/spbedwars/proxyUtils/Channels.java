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
    GAME_JOIN("spbedwars:game_join"),
    GAME_JOIN_REQUEST("spbedwars:game_join_request"),
    GAME_CREATION_REQUEST("spbedwars:game_creation_request"),
    GAME_DELETE_REQUEST("spbedwars:game_delete"),
    ARENA_REGISTRATION("spbedwars:arena_registration"),
    ARENA_UNREGISTRATION("spbedwars:arena_unregistration");

    private final String channel;

    Channels(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }
}
