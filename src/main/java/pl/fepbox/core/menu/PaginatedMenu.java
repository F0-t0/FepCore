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
    protected int margin;
    private static ItemStack nextPage;
    private static ItemStack previousPage;
    private static ItemStack currentPage;
    private static int MAX_INTERIOR_ROWS = 4;

    public PaginatedMenu(int margin, String title, int maxItemsPerPage, List<T> items) {
        super(getSize(margin, items) * 9, title);
        this.maxItemsPerPage = maxItemsPerPage;
        this.items = items;
        this.margin = margin;

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
        { // CURRENT PAGE
            ItemMeta meta = currentPage.getItemMeta();
            meta.displayName(MiniMessageUtils.deserialize("<light_blue>Current Page"));
            meta.lore(
                    MiniMessageUtils.deserialize(
                            List.of(
                                    "<light_gray>You are on page: <blue>" + page + "<light_gray>/<gray>"
                                            + getMaxPages())));
            currentPage.setItemMeta(meta);
        }
    }

    private static <T> int getSize(int margin, List<T> items) {
        if (margin > 4) {
            margin = 4;
        }
        double itemsAmount = items.size();

        int rows = (margin > 0) ? 4 : 6;
        if (margin <= 6 || items == null || items.isEmpty()) {
            return 6;
        }
        double slotsPerRow = (9 - margin * 2);
        int finalRows;

        if (itemsAmount > slotsPerRow * rows) {
            return finalRows = 6;
        } else {
            return finalRows = (int) Math.ceil(itemsAmount / slotsPerRow) + 2;
        }
    }

    public abstract ItemStack renderItem(T itemData);

    public abstract void onPageItemClick(T itemData, Player player);

    private int getItemsPerPage(int margin) {
        if (margin <= 0)
            return 54;
        return MAX_INTERIOR_ROWS * (9 - margin * 2);
    }

    public int getMaxPages() {
        int itemsPerPage = Math.max(1, getItemsPerPage(margin));
        return Math.max(1, (int) Math.ceil((double) items.size() / itemsPerPage));
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

    public boolean isMarginSlot(int slot, int margin, int size) {
        if (margin <= 0) {
            return false;
        }
        int rows = size / 9;
        int row = slot / 9;
        int col = slot % 9;
        return row == 0 // top bar
                || row == rows - 1 // bottom bar
                || col < margin // left column
                || col >= 9 - margin; // right column
    }

    @Override
    public void decorate() {
        ItemStack filler = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = filler.getItemMeta();
        meta.displayName(MiniMessageUtils.deserialize("<black>"));
        filler.setItemMeta(meta);

        int size = getSize(margin, items) * 9;
        for (int i = 0; i < size; i++) {
            inv.setItem(i, filler);
        }
        inv.setItem(size - 3, nextPage);
        inv.setItem(size - 4, currentPage);
        inv.setItem(size - 5, previousPage);
        for (int i = 0; i < size; i++) {
            int j = 0;
            if (!isMarginSlot(i, margin, size)) {
                inv.setItem(i, renderItem(items.get(j)));
                j++;
            }
        }
    }

}
