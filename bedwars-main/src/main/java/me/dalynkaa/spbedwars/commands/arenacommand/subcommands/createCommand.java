package me.dalynkaa.spbedwars.commands.arenacommand.subcommands;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.commands.arenacommand.ArenaSubCommand;
import me.dalynkaa.spbedwars.utils.config.Config;
import me.dalynkaa.spbedwars.utils.dataclasses.another.ArenaCreation;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.ArenaTypes;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.MessageType;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameArena;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class createCommand extends ArenaSubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "This command for create arena";
    }

    @Override
    public String getSyntax() {
        return "/arena create <name> <type>";
    }

    @Override
    public void perform(Player player, String[] args) {
        BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
        if (args.length<4){
            bPlayer.sendMessage(Component.text("Недостатосно аргументов!"), MessageType.ERROR);
            return;
        }
        bPlayer.sendMessage(Component.text("Начало создания игровой арены"), MessageType.NORMAL);
        UUID uuid = UUID.randomUUID();
        String arenaName = args[1];
        ArenaTypes arenaTypes = ArenaTypes.valueOf(args[2]);
        String schemName = args[3];
        World world = createWorld(uuid);
        player.teleport(new Location(world, 0, 1, 0));
        GameArena gameArena = new GameArena(uuid, arenaName, schemName, world, arenaTypes);
        gameArena.pasteSchem();
        gameArena.save();
        bPlayer.sendMessage(Component.text("Арена создана!"), MessageType.SUCCESS);
        bPlayer.setEditArena(gameArena.getId());
        bPlayer.save();
        SPBedWars.getInstance().currentCreation = new ArenaCreation(gameArena, bPlayer);
        SPBedWars.getInstance().currentCreation.cornersSetStage();
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2){
            return Arrays.asList("<имя_арены>");
        } else if (args.length == 3) {
            List<String> result = new ArrayList<>();
            result.add(Config.getServerType().name());
            return result;
        }else if (args.length == 4) {
            List<String> result = getAllArenasSchems();
            return result;
        }
        return null;
    }

    public World createWorld(UUID uuid){
        WorldCreator wc = new WorldCreator(uuid.toString());
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.FLAT);
        wc.generateStructures(false);
        wc.generatorSettings("{\"layers\": [{\"block\": \"air\", \"height\": 1}], \"biome\":\"plains\"}");
        World world = wc.createWorld();
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setGameRule(GameRule.MOB_GRIEFING,false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING,false);
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        Location location = new Location(world, 0, 0 ,0);
        location.getBlock().setType(Material.STONE);
        return world;
    }
    public static List<String> getAllArenasSchems(){
        File dir = new File(SPBedWars.getInstance().getDataFolder().getPath(), "schem");
        List<String> schems = Arrays.stream(dir.list()).toList();
        return Arrays.stream(dir.list()).toList();
    }
}
