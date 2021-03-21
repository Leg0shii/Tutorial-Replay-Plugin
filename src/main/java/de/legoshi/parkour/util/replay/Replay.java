package de.legoshi.parkour.util.replay;

import de.legoshi.parkour.Main;
import de.legoshi.parkour.util.FW;
import de.legoshi.parkour.util.fakeplayer.NPC;
import de.legoshi.parkour.util.player.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Replay extends BukkitRunnable {

      private Player player;
      private int counter;
      private FW fwRec;
      private final String fwPath = "./ParkourReplays/";
      private String fwFile;

      public Replay(String replName) {
            this.fwFile = replName + ".yml";
      }

      public Replay(Player player, String replName) {
            this.counter = 0;
            this.player = player;
            this.fwFile = replName + ".yml";
            this.fwRec = new FW(fwPath,fwFile);
      }

      @Override
      public void run() {
            //if(counter%20 == 0) Bukkit.getConsoleSender().sendMessage("Rec " + player.getName() + ":" + counter);
            this.fwRec.setLocation(player.getLocation(), counter++);
      }

      public void startReplayRecording(PlayerObject playerObject) {

            Player player = playerObject.getPlayer();
            FW fw = new FW(fwPath, fwFile);
            final int[] index = {0};
            playerObject.setIndex(0);
            //player.sendMessage("Recording started");

            final int taskID = new BukkitRunnable() {

                  @Override
                  public void run() {

                        fw.setLocation(player.getLocation(), index[0]);
                        playerObject.setIndex(index[0]);
                        index[0]++;

                        if(index[0]%20 == 0) Bukkit.getConsoleSender().sendMessage(player.getName() + ": " + index[0]);
                        if(!playerObject.isReplayRecordMode() || index[0] > 6000 || !player.isOnline()) {

                              fw.setValue("length", playerObject.getIndex());
                              fw.save();
                              cancel();

                        }

                  }

            }.runTaskTimer(Main.getInstance(), 0, 1L).getTaskId();

            playerObject.setTaskID(taskID);
            playerObject.setFw(fw);
            //player.sendMessage("Variables Saved");

      }

      public void playReplay(String name) {

            final int[] index = {0};
            FW fwnew = new FW(fwPath, fwFile);
            NPC npc = new NPC(name, fwnew.getLocation(0));
            npc.spawn();
            //player.sendMessage("Replay started!");

            new BukkitRunnable() {

                  @Override
                  public void run() {
                        //if(index[0]%20 == 0) Bukkit.getConsoleSender().sendMessage("Repl " + player.getName() + ":" + index[0]);
                        if((fwnew.getInt("length") >= index[0])) { // playerObject.getPlayerStatus().isReplayStart() && playerObject.getPlayerStatus().isReplaymode() &&
                              npc.teleport(fwnew.getLocation(index[0]));
                              index[0]++;
                        } else if (!(fwnew.getInt("length") >= index[0])){ // !(playerObject.getPlayerStatus().isReplaymode()) ||
                              //player.sendMessage("Replay finished.");
                              npc.destroy();
                              cancel();
                        }

                  }

            }.runTaskTimer(Main.getInstance(), 1, 1L);

      }

}
