package me.dalynkaa.spbedwars.guis.upgrade.upgradeAction;


import me.dalynkaa.spbedwars.guis.upgrade.item.UpgradeItem;
import org.bukkit.entity.Player;

public interface IUpgradeActionButton<T extends UpgradeItem, F extends Player> {
    /**
     * Executes the event passed to it
     *
     * @param item Inventory action
     */
    void execute(final T item, final F whoCLicked);

}
