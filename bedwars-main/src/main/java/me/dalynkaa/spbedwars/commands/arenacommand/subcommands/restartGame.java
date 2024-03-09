package me.dalynkaa.spbedwars.commands.arenacommand.subcommands;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.commands.arenacommand.ArenaSubCommand;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.MessageType;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameArena;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class restartGame extends ArenaSubCommand {
    @Override
    public String getName() {
        return "restart";
    }

    @Override
    public String getDescription() {
        return "restart game";
    }

    @Override
    public String getSyntax() {
        return "/arena restart <arena>";
    }

    @Override
    public void perform(Player player, String[] args) {
        BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
        if (args.length<2){
            bPlayer.sendMessage(Component.text("Недостатосно аргументов!"), MessageType.ERROR);
            return;
        }
        String id = args[1];
        UUID uuid = UUID.fromString(id);
        BWGame temp_game = BWGame.getGameById(uuid);
        GameArena arena = temp_game.getArena();
        for (BPlayer bPlayer1: temp_game.getPlayers()){
            bPlayer1.getPlayer().kick();
        }
        SPBedWars.getInstance().activeGames.remove(uuid);
        UUID arenaId = UUID.randomUUID();
        BWGame game = new BWGame(arenaId, arena, GameStage.WAITING);
        SPBedWars.getInstance().activeGames.put(arenaId, game);
        World world = new WorldCreator(arena.getId().toString()).createWorld();
        game.setGameStage(GameStage.REBUILDING);
        game.clearArena();
        game.setGameStage(GameStage.WAITING);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2){
            ArrayList<String> gameIds = new ArrayList<>();
            for (BWGame game: SPBedWars.getInstance().activeGames.values()){
                gameIds.add(game.getGameId().toString());
            }
            return gameIds;
        }
        return null;
    }
}
