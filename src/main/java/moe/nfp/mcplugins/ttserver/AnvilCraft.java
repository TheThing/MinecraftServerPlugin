package moe.nfp.mcplugins.ttserver;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.event.inventory.PrepareAnvilEvent;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;

public class AnvilCraft implements Listener {
  @EventHandler
  public void onAnvilCraft(PrepareAnvilEvent e) {
    AnvilInventory inv = e.getInventory();
    
    if (!inv.getViewers().isEmpty()) {
      inv.setMaximumRepairCost(1000);
      
      ItemStack[] stacks = e.getInventory().getContents();
      for (int id = stacks.length-1; id >= 0; id--) {
        ItemStack i = stacks[id];
        if (i != null) {
          NBTItem n = new NBTItem(i);
          NBTCompound tags = n.getCompound("tags");
          if (tags != null) {
            if (tags.hasKey("RepairCost") && tags.getInteger("RepairCost") > 100) {
              tags.setInteger("RepairCost", 1000);
              e.getInventory().setItem(id, n.getItem());
            }
          }
        }
      }

      int rep = inv.getRepairCost();
      if (rep > 39) {
        inv.setRepairCost(39);
        e.getView().setProperty(Property.REPAIR_COST, 39);
      }

      if (inv.getItem(0) != null && inv.getItem(1) != null) {
        if (inv.getItem(0).getType() == inv.getItem(1).getType() && inv.getItem(1).getEnchantments().size() == 0) {
          if (rep > 20) {
            inv.setRepairCost(20);
            e.getView().setProperty(Property.REPAIR_COST, 20);
          }
        }
      }
		}
	}
}