package me.dalynkaa.bedwarslobby.utils.usable;


import org.bukkit.event.Event;

public interface IInventoryButton<T extends Event> {
    /**
     * Executes the event passed to it
     *
     * @param event Inventory action
     */
    void execute(final T event);

}
