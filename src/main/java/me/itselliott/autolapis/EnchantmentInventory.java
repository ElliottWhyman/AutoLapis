package me.itselliott.autolapis;

import org.bukkit.DyeColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

/**
 * Manipulation of the enchanting inventory to add lapis
 */
public class EnchantmentInventory implements Listener {

    private ItemStack lapis;

    public EnchantmentInventory(AutoLapis plugin) {
        //Create the lapis item stack as it is a dye, not material
        Dye dye = new Dye();
        dye.setColor(DyeColor.BLUE);
        this.lapis = dye.toItemStack();
        this.lapis.setAmount(1);

        //Register event listeners in this class
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Keeps the lapis inside of the inventory
     */
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory() instanceof EnchantingInventory) {
            if (AutoLapis.getInstance().isLapisEnabled())
                event.getInventory().setItem(1, lapis);
        }
    }

    /**
     * Remove lapis when inventory is closed so it does not fall on the floor
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() instanceof EnchantingInventory) {
            if (AutoLapis.getInstance().isLapisEnabled())
                event.getInventory().setItem(1, null);
        }
    }

    /**
     * Prevent player from taking lapis
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() instanceof EnchantingInventory) {
            if (AutoLapis.getInstance().isLapisEnabled())
                if (event.getSlot() == 1)
                    event.setCancelled(true);
        }
    }


}
