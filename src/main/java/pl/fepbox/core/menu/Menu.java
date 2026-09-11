package pl.fepbox.core.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import pl.fepbox.core.utils.MiniMessageUtils;

public abstract class Menu implements InventoryHolder {
    protected Inventory inv;
    protected final int size;
    protected String title;

    private final Map<Integer, Consumer<InventoryClickEvent>> slotActions = new HashMap<>();

    public Menu(int size, String title) {
        this.size = size;
        this.title = title;
    }

    public abstract void decorate();

    public void open(Player player) {
        this.inv = Bukkit.createInventory(this, size, MiniMessageUtils.deserialize(title));
        slotActions.clear();
        decorate();
        player.openInventory(inv);
    }

    public void setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> action) {
        inv.setItem(slot, item);
        if (action != null) {
            slotActions.put(slot, action);
        } else {
            slotActions.remove(slot);
        }
    }

    public ItemStack createItem(String displayname, Material material, String... lore) {
        ItemStack itemstack = new ItemStack(material);
        ItemMeta itemmeta = itemstack.getItemMeta();
        itemmeta.displayName(MiniMessageUtils.deserialize(displayname));
        
        ArrayList<String> Stringlore = new ArrayList<>(Arrays.asList(lore));
        ArrayList<Component> loreList = new ArrayList<>();
        for (String line : Stringlore) {
            loreList.add(MiniMessageUtils.deserialize(line));
        }

        itemmeta.lore(loreList);

        itemstack.setItemMeta(itemmeta);
        return itemstack;
    }

    public void handleMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getSlot();
        if (slotActions.containsKey(slot)) {
            slotActions.get(slot).accept(event);
        }    
     }

    @Override
    public Inventory getInventory() {
        return inv;
    }

}