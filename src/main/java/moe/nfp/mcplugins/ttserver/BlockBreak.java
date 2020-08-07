package moe.nfp.mcplugins.ttserver;

import java.util.Random;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
  @EventHandler
  public void onBreak(BlockBreakEvent event){
    Block block = event.getBlock();

    if(block.getType() == Material.DIAMOND_ORE) {
      ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();

      event.setCancelled(true);
      block.setType(Material.AIR);

      if (mainHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.DIAMOND_ORE, 1));
        return;
      }

      int bonus = getBonusEnchantment(event);

      Random random = new Random();
      double r = random.nextDouble();

      boolean isGoldPick = false;
      if (mainHand.getType() == Material.GOLDEN_PICKAXE) {
        isGoldPick = true;
      }

      ItemMeta meta = mainHand.getItemMeta();
      if (meta != null) {
        ((Damageable)meta).setDamage(((Damageable)meta).getDamage() + 1);
        mainHand.setItemMeta(meta);
      }

      if (isGoldPick) {
        if (r <= 0.3) {
          block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT, 1 + bonus));
        } else if (r <= 0.6) {
          block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.EMERALD, 1 + bonus));
        } else {
          block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.DIAMOND, 1 + bonus));
        }
      } else {
        if (r <= 0.5) {
          block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT, 1 + bonus));
        } else if (r <= 0.75) {
          block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT, 1 + bonus));
        } else if (r <= 0.95) {
          block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.EMERALD, 1 + bonus));
        } else {
          block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.DIAMOND, 1 + bonus));
        }
      }
    }
  }

  public int getBonusEnchantment(BlockBreakEvent event) {
    ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
    int bonus = (int) (Math.random() * (item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 2)) - 1;
    if (bonus < 0) {
      bonus = 0;
    }
    return bonus;
  }
}