package me.dalynkaa.spbedwars.huds.bossBarHuds;

import me.dalynkaa.spbedwars.huds.bossBarHuds.bedStatusHud.BedStatusHud;
import me.dalynkaa.spbedwars.huds.bossBarHuds.bedStatusHud.TimerStatusHud;
import me.dalynkaa.spbedwars.huds.utils.CustomBossBar;
import me.dalynkaa.spbedwars.huds.utils.RenderStatus;
import me.dalynkaa.spbedwars.huds.utils.RenderStatusType;
import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.hudConstans.Chars;
import me.dalynkaa.spbedwars.utils.hudConstans.Offset;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.*;

public class BossBarRenderer {
    private final HashSet<AbstractBossBarHud> BOSS_BAR_HUDS;
    private CustomBossBar BOSS_BAR;
    private UUID playerUuid;

    public BossBarRenderer() {
        this.BOSS_BAR_HUDS = new HashSet<>();
    }

    public void render() {
        TextComponent.Builder component = Component.text();
        for (AbstractBossBarHud abstractBossBarHud : BOSS_BAR_HUDS) {
            RenderStatus status = abstractBossBarHud.render(playerUuid);
            if (status.getStatus().equals(RenderStatusType.RENDER)) {
                component.append(status.getMessage());
            } else if (status.getStatus().equals(RenderStatusType.SAME)) {
                component.append(Component.text(""));
            }
        }
        BOSS_BAR.setTitle(component.build());

    }

    public void initializePlayer(UUID player) {
        BedStatusHud bedStatusHud = new BedStatusHud();
        TimerStatusHud timerStatusHud = new TimerStatusHud();
        this.BOSS_BAR = CustomBossBar.get(player);
        this.playerUuid = player;
        registerHud(player, bedStatusHud);
        registerHud(player, timerStatusHud);
    }

    public void removePlayer(UUID playerUuid) {
        this.BOSS_BAR_HUDS.clear();
        CustomBossBar.remove(playerUuid);
    }

    public void registerHud(UUID player, AbstractBossBarHud abstractBossBarHud) {
        BOSS_BAR_HUDS.add(abstractBossBarHud);
    }
}
