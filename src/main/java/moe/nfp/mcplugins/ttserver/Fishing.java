package moe.nfp.mcplugins.ttserver;

import java.util.Random;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.EntityType;

public class Fishing implements Listener {
  @EventHandler
  public void onPlayerFish(PlayerFishEvent event) {
    Player player = event.getPlayer();
    for (PotionEffect effect : player.getActivePotionEffects()) {
      if (effect.getType().getName() == "REGENERATION") {
        player.removePotionEffect(effect.getType());
      }
    }
    
    // player caught something
    if (event.getCaught() != null) {
      EntityType caughtType = event.getCaught().getType();
      if (caughtType.toString() == "DROPPED_ITEM") {
        float penalty_saturation = 0.2f;
        float saturationLevel = player.getSaturation();
        float newSaturationLevel = saturationLevel - penalty_saturation;	    				
        if (newSaturationLevel <= 0) {
          newSaturationLevel = 0;
          
          Random random = new Random();
          double r = random.nextDouble();
          if (r <= 0.30) {
            int penalty_hunger = 1;
            int foodLevel = player.getFoodLevel();
            int newFoodLevel = foodLevel - penalty_hunger;
            if (newFoodLevel < 0) { newFoodLevel = 0; }
            player.setFoodLevel(newFoodLevel);
          }
        }
        player.setSaturation(newSaturationLevel);
      }
    }
  }
}