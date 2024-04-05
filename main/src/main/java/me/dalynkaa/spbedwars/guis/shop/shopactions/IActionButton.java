package me.dalynkaa.spbedwars.guis.shop.shopactions;


import me.dalynkaa.spbedwars.guis.shop.item.ShopItem;
import org.bukkit.entity.Player;

public interface IActionButton<T extends ShopItem, F extends Player> {
    /**
     * Executes the event passed to it
     *
     * @param item Inventory action
     */
    void execute(final T item, final F whoCLicked);

}
