package me.dalynkaa.spbedwars.mainListener;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import me.dalynkaa.spbedwars.guis.upgrade.UpgradeGui;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameShopType;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NpcClickListener implements Listener {
    public NpcClickListener(SPBedWars main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onNpcClick(NPCRightClickEvent event){
        GameShopType type = event.getNPC().data().get("type");
        if (type.equals(GameShopType.NORMAL)){
            TeamPlayer teamPlayer = TeamPlayer.fromPlayer(BPlayer.getByUUID(event.getClicker().getUniqueId()));
                new ShopGui(teamPlayer);
        }else {
            TeamPlayer teamPlayer = TeamPlayer.fromPlayer(BPlayer.getByUUID(event.getClicker().getUniqueId()));
            new UpgradeGui(teamPlayer);
        }
    }
}
