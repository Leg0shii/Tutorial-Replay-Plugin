package de.legoshi.parkour.events;

import de.legoshi.parkour.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

      @EventHandler
      public void onQuit(PlayerQuitEvent event) {

            Main.getInstance().playerManager.getPlayerObjectHashMap().remove(event.getPlayer());

      }

}
