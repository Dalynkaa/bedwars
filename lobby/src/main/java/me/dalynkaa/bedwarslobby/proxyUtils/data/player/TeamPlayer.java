package me.dalynkaa.bedwarslobby.proxyUtils.data.player;


import java.util.UUID;

public class TeamPlayer extends BPlayer{

    public TeamPlayer(UUID uuid) {
        super(uuid);
    }

    public TeamPlayer(BPlayer bPlayer) {
        super(bPlayer.getUuid(), bPlayer.getEditArena(), bPlayer.getPreviusGame(), bPlayer.getCurrentGame());

    }
    public static TeamPlayer fromPlayer(BPlayer player){
        return new TeamPlayer(player);
    }
}
