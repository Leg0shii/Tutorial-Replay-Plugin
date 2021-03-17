package de.legoshi.parkour.util;

import de.legoshi.parkour.Main;
import de.legoshi.parkour.util.fakeplayer.NPC;
import de.legoshi.parkour.util.player.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class Replay extends BukkitRunnable {

      private Player player;
      private int counter;

      private FW fwRec;
      private FW fwPlay;

      private final String fwPath = "./ParkourReplays/";
      private String fwFile;


      public Replay(Player player) {

            this.counter = 0;
            this.player = player;

            this.fwFile = player.getUniqueId().toString() + ".yml";
            this.fwRec = new FW(fwPath,fwFile);

            FW fw = new FW(fwPath,fwFile);
            if(fw.exist()) this.fwPlay = fw;
            else this.fwPlay = null;

      }

      @Override
      public void run() {

            if(counter%20 == 0) Bukkit.getConsoleSender().sendMessage("Rec " + player.getName() + ":" + counter);
            this.fwRec.setLocation(player.getLocation(), counter++);

      }

      public void stopReplayRec(PlayerObject playerObject) {

            playerObject.getFw().save();
            fwRec.setValue("length", counter);
            //this.cancel();

      }

      public void saveReplayRec() {

            Bukkit.getConsoleSender().sendMessage("Trying to save replay");
            this.fwRec.save();
            this.cancel();

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
            player.sendMessage("Variables Saved");

      }

      public void playReplay(Player player, String name) {

            final int[] index = {0};
            FW fwnew = new FW(fwPath, fwFile);
            NPC npc = new NPC(name, fwnew.getLocation(player, 0));
            npc.spawn(player);
            //player.sendMessage("Replay started!");

            new BukkitRunnable() {

                  @Override
                  public void run() {

                        if(index[0]%20 == 0) Bukkit.getConsoleSender().sendMessage("Repl " + player.getName() + ":" + index[0]);

                        if((fwnew.getInt("length") >= index[0])) { // playerObject.getPlayerStatus().isReplayStart() && playerObject.getPlayerStatus().isReplaymode() &&

                              System.out.println(fwnew.getLocation(player, index[0]).toString());
                              npc.teleport(fwnew.getLocation(player, index[0]), player);
                              index[0]++;

                        } else if (!(fwnew.getInt("length") >= index[0])){ // !(playerObject.getPlayerStatus().isReplaymode()) ||

                              //player.sendMessage("Replay finished.");
                              npc.destroy(player);
                              cancel();

                        }

                  }

            }.runTaskTimer(Main.getInstance(), 1, 1L);

      }

      public void saveReplayFile(Player player) {

            //Bukkit.getScheduler().cancelTask(playerObject.getTaskID());
            //playerObject.getFw().setValue("length", playerObject.getIndex());
            //playerObject.getFw().save();

            String playername = player.getName();

            //File fold = new File("./ParkourReplays/" + mapid + "/" + playername + ".yml");
            //File fnew = new File("./ParkourReplays/" + mapid + "/" + mapid + "_" + playername + ".yml");

            //if(fnew.exists()) {

                  //fnew.delete();
                  //playerObject.getPlayer().sendMessage("Removed worse play!");

            //}

            //fold.renameTo(fnew);

            //playerObject.getPlayer().sendMessage("Saved Replay!");

      }

      public void deleteReplayFile(PlayerObject playerObject) {

            playerObject.getFw().save();

            String playername = player.getName();

            File fold = new File(fwPath + fwFile);
            fold.delete();

            //playerObject.getPlayer().sendMessage("Deleted Replay!");

      }

      public FW getFwRec() {
            return fwRec;
      }

      public void setFwRec(FW fwRec) {
            this.fwRec = fwRec;
      }

      public FW getFwPlay() {
            return fwPlay;
      }

      public void setFwPlay(FW fwPlay) {
            this.fwPlay = fwPlay;
      }

      public int getCounter() {
            return counter;
      }

      public void setCounter(int counter) {
            this.counter = counter;
      }

}
