package de.legoshi.parkour.manager;

import de.legoshi.parkour.util.player.PlayerObject;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerManager {

      public HashMap<Player, PlayerObject> playerObjectHashMap;

      public PlayerManager() {

            this.playerObjectHashMap = new HashMap<>();

      }

      public HashMap<Player, PlayerObject> getPlayerObjectHashMap() {
            return playerObjectHashMap;
      }

      public void setPlayerObjectHashMap(HashMap<Player, PlayerObject> playerObjectHashMap) {
            this.playerObjectHashMap = playerObjectHashMap;
      }
}
