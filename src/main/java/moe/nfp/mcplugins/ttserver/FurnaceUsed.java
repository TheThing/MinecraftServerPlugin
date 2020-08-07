package moe.nfp.mcplugins.ttserver;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

public class FurnaceUsed implements Listener  {
  @EventHandler
  public void furnaceSmeltEvent(FurnaceSmeltEvent event){
    ItemStack result = event.getResult();
    if (event.getSource().getType() == Material.GOLD_ORE && result.getType() == Material.GOLD_NUGGET) {
      result.setAmount(2 + (int)(Math.random() * 3));
      event.setResult(result);
    }
  }
}
