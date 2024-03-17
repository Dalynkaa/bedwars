package me.dalynkaa.bedwarslobby.gameMenus.gameselect;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ArenaTypes;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ServerType;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.GameRegistrator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.List;

public class GameSelectMenu {
    private Gui gui;

    public GameSelectMenu(BPlayer bPlayer) {
        gui = Gui.gui()
                .rows(6)
                .title(Component.text("Выбор игры"))
                .disableAllInteractions()
                .create();
        //gui.getFiller().fillBorder(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem());
        //соло
        Integer[] soloSlots = {19, 28};
        List<Component> soloLore = new ArrayList<>();
        soloLore.add(Component.text("Подключиться к случайной игре", TextColor.fromCSSHexString("#6c5ce7")));
        soloLore.add(Component.text("Серверов: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        soloLore.add(Component.text("Игр: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerGamesList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        soloLore.add(Component.text("Игроков: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerPlayerList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        GuiItem soloRandom = ItemBuilder.from(CustomStack.getInstance("bedwars:redbed").getItemStack()).name(Component.text("Соло игра")).lore(soloLore).asGuiItem(
                event -> connectToGame(ArenaTypes.SOLO, bPlayer)
        );
        GuiItem soloServers = ItemBuilder.from(CustomStack.getInstance("bedwars:redplanet").getItemStack()).name(Component.text("Соло сервера")).asGuiItem(
                event -> openServersMenu(ArenaTypes.SOLO, bPlayer)
        );
        //дуо
        Integer[] duoSlots = {21, 30};
        List<Component> duoLore = new ArrayList<>();
        duoLore.add(Component.text("Подключиться к случайной игре", TextColor.fromCSSHexString("#6c5ce7")));
        duoLore.add(Component.text("Серверов: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        duoLore.add(Component.text("Игр: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerGamesList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        duoLore.add(Component.text("Игроков: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerPlayerList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        GuiItem duoRandom = ItemBuilder.from(CustomStack.getInstance("bedwars:bluebed").getItemStack()).name(Component.text("Дуо игра")).lore(duoLore).asGuiItem(
                event -> connectToGame(ArenaTypes.DUO, bPlayer)
        );
        GuiItem duoServers = ItemBuilder.from(CustomStack.getInstance("bedwars:blueplanet").getItemStack()).name(Component.text("Дуо сервера")).asGuiItem(
                event -> openServersMenu(ArenaTypes.DUO, bPlayer)
        );
        //трио
        Integer[] trioSlots = {23, 32};
        List<Component> trioLore = new ArrayList<>();
        trioLore.add(Component.text("Подключиться к случайной игре", TextColor.fromCSSHexString("#6c5ce7")));
        trioLore.add(Component.text("Серверов: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        trioLore.add(Component.text("Игр: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerGamesList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        trioLore.add(Component.text("Игроков: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerPlayerList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        GuiItem trioRandom = ItemBuilder.from(CustomStack.getInstance("bedwars:yellowbed").getItemStack()).name(Component.text("Трио игра")).lore(trioLore).asGuiItem(
                event -> connectToGame(ArenaTypes.TRIO, bPlayer)
        );
        GuiItem trioServers = ItemBuilder.from(CustomStack.getInstance("bedwars:yellowplanet").getItemStack()).name(Component.text("Трио сервера")).asGuiItem(
                event -> openServersMenu(ArenaTypes.TRIO, bPlayer)
        );
        //сквад
        Integer[] squadSlots = {25, 34};
        List<Component> quadroLore = new ArrayList<>();
        quadroLore.add(Component.text("Подключиться к случайной игре", TextColor.fromCSSHexString("#6c5ce7")));
        quadroLore.add(Component.text("Серверов: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        quadroLore.add(Component.text("Игр: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerGamesList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        quadroLore.add(Component.text("Игроков: ", TextColor.fromCSSHexString("#6c5ce7")).append(Component.text(GameRegistrator.getServerPlayerList(ServerType.CLASSIC), TextColor.fromCSSHexString("#a29bfe"))));
        GuiItem squadRandom = ItemBuilder.from(CustomStack.getInstance("bedwars:greenbed").getItemStack()).name(Component.text("Сквад игра")).lore(quadroLore).asGuiItem(
                event -> connectToGame(ArenaTypes.QUADRO, bPlayer)
        );
        GuiItem squadServers = ItemBuilder.from(CustomStack.getInstance("bedwars:greenplanet").getItemStack()).name(Component.text("Сквад сервера")).asGuiItem(
                event -> openServersMenu(ArenaTypes.QUADRO, bPlayer)
        );

        gui.setItem(soloSlots[0], soloRandom);
        gui.setItem(soloSlots[1], soloServers);
        gui.setItem(duoSlots[0], duoRandom);
        gui.setItem(duoSlots[1], duoServers);
        gui.setItem(trioSlots[0], trioRandom);
        gui.setItem(trioSlots[1], trioServers);
        gui.setItem(squadSlots[0], squadRandom);
        gui.setItem(squadSlots[1], squadServers);

        gui.open(bPlayer.getPlayer());
        TexturedInventoryWrapper.setPlayerInventoryTexture(bPlayer.getPlayer(), new FontImageWrapper("bedwars:select_menu"), "", 0, -8);
    }

    public void connectToGame(ArenaTypes type, BPlayer bPlayer) {
        bPlayer.connectToGame(type, ServerType.CLASSIC);
    }

    public void openServersMenu(ArenaTypes type, BPlayer bPlayer) {

    }
}
