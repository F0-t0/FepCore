package pl.fepbox.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import pl.fepbox.core.menu.Menu;

public class menuClick implements Listener {
    @EventHandler 
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Menu menu) {
            menu.handleMenu(event);
        }
    }
}
