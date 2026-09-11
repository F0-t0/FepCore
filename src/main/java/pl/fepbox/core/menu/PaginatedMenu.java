package pl.fepbox.core.menu;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.fepbox.core.utils.MiniMessageUtils;

public abstract class PaginatedMenu<T> extends Menu {
    protected int page = 0;
    protected int maxItemsPerPage;
    protected List<T> items;
    private static ItemStack nextPage;
    private static ItemStack previousPage;
    private static ItemStack currentPage;

    public PaginatedMenu(int size, String title, int maxItemsPerPage, List<T> items) {
        super(size, title);
        this.maxItemsPerPage = maxItemsPerPage;
        this.items = items;

        nextPage = new ItemStack(Material.AMETHYST_SHARD);
        { // NEXT PAGE META
            ItemMeta meta = nextPage.getItemMeta();
            meta.displayName(MiniMessageUtils.deserialize("<gold>Next Page »"));
            nextPage.setItemMeta(meta);
        }
        previousPage = new ItemStack(Material.ECHO_SHARD);
        { // PREVIOUS PAGE META
            ItemMeta meta = previousPage.getItemMeta();
            meta.displayName(MiniMessageUtils.deserialize("<gray>« Previous Page"));
            previousPage.setItemMeta(meta);
        }
        currentPage = new ItemStack(Material.END_CRYSTAL);
        { //CURRENT PAGE 
            ItemMeta meta = currentPage.getItemMeta();
            meta.displayName(MiniMessageUtils.deserialize("<light_blue>Current Page"));
            meta.lore(
                MiniMessageUtils.deserialize(
                    List.of(
                        "<light_gray>You are on page: <blue>" + page + "<light_gray>/<gray>" + getMaxPages()
                    ))
                );
            currentPage.setItemMeta(meta);
        }
    }

    public abstract ItemStack renderItem(T itemData);
    public abstract void onPageItemClick(T itemData, Player player);

    public int getMaxPages() {
        if (items.isEmpty()) return 1;
        return (int) Math.ceil((double) items.size() / maxItemsPerPage);
    }




    public static void setNextPageItem(ItemStack nextPage) {
        PaginatedMenu.nextPage = nextPage;
    }

    public static void setPreviousPageItem(ItemStack previousPage) {
        PaginatedMenu.previousPage = previousPage;
    }

    public static void setCurrentPage(ItemStack currentPage) {
        PaginatedMenu.currentPage = currentPage;
    }

}