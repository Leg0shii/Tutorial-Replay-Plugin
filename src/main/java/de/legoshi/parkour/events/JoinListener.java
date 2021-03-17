package de.legoshi.parkour.events;

import de.legoshi.parkour.Main;
import de.legoshi.parkour.util.player.PlayerObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

      @EventHandler
      public void onJoinListener(PlayerJoinEvent event) {

            Player player = event.getPlayer();
            PlayerObject playerObject = new PlayerObject(player);
            Main.getInstance().playerManager.getPlayerObjectHashMap().put(player, playerObject);

      }

}
