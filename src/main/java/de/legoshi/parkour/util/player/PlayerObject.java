package de.legoshi.parkour.util.player;

import de.legoshi.parkour.util.FW;
import org.bukkit.entity.Player;

public class PlayerObject {

      public Player player;
      public int taskID;
      public FW fw;
      public int index;
      public boolean replayRecordMode;
      public boolean replayViewMode;

      public PlayerObject(Player player) {
            this.replayRecordMode = false;
            this.replayViewMode = false;
            this.index = 0;
            this.player = player.getPlayer();
      }

      public PlayerObject(Player player, FW fw) {
            this.replayRecordMode = false;
            this.replayViewMode = false;
            this.index = 0;
            this.fw = fw;
            this.player = player;
      }

      public boolean isReplayRecordMode() {
            return replayRecordMode;
      }

      public void setReplayRecordMode(boolean replayRecordMode) {
            this.replayRecordMode = replayRecordMode;
      }

      public boolean isReplayViewMode() {
            return replayViewMode;
      }

      public void setReplayViewMode(boolean replayViewMode) {
            this.replayViewMode = replayViewMode;
      }

      public int getTaskID() {
            return taskID;
      }

      public void setTaskID(int taskID) {
            this.taskID = taskID;
      }

      public int getIndex() {
            return index;
      }

      public void setIndex(int index) {
            this.index = index;
      }

      public Player getPlayer() {
            return player;
      }

      public void setPlayer(Player player) {
            this.player = player;
      }

      public FW getFw() {
            return fw;
      }

      public void setFw(FW fw) {
            this.fw = fw;
      }
}
