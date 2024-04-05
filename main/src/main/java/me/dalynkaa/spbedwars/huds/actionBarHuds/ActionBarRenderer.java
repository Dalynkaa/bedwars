package me.dalynkaa.spbedwars.huds.actionBarHuds;

import me.dalynkaa.spbedwars.huds.actionBarHuds.gamePreStatusHud.GameWaitingHud;
import me.dalynkaa.spbedwars.huds.utils.CustomBossBar;
import me.dalynkaa.spbedwars.huds.utils.RenderStatus;
import me.dalynkaa.spbedwars.huds.utils.RenderStatusType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class ActionBarRenderer {
    private final HashSet<AbstractActionBarHud> ACTION_BAR_HUDS;
    private UUID playerUuid;

    public ActionBarRenderer() {
        this.ACTION_BAR_HUDS = new HashSet<>();
    }

    public void render() {
        TextComponent.Builder component = Component.text();
        for (AbstractActionBarHud abstractBossBarHud : ACTION_BAR_HUDS) {
            RenderStatus status = abstractBossBarHud.render(playerUuid);
            if (status.getStatus().equals(RenderStatusType.RENDER)) {
                component.append(status.getMessage());
            } else if (status.getStatus().equals(RenderStatusType.SAME)) {
                component.append(Component.text(""));
            }
        }
        Player player = Bukkit.getPlayer(playerUuid);
        if (player != null) {
            player.sendActionBar(component.build());
        }

    }

    public void initializePlayer(UUID player) {
        this.playerUuid = player;
        registerHud(player, new GameWaitingHud());
    }

    public void removePlayer(UUID playerUuid) {
        this.ACTION_BAR_HUDS.clear();
        CustomBossBar.remove(playerUuid);
    }

    public void registerHud(UUID player, AbstractActionBarHud abstractActionBarHud) {
        ACTION_BAR_HUDS.add(abstractActionBarHud);
    }
}
