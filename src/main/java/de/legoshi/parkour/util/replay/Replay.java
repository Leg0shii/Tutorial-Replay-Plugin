package de.legoshi.parkour.util.replay;

import de.legoshi.parkour.Main;
import de.legoshi.parkour.util.FW;
import de.legoshi.parkour.util.fakeplayer.NPC;
import de.legoshi.parkour.util.player.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class Replay {

      private final String fwPath = "./ParkourReplays/";
      private final String fwFile;

      public Replay(String replName) {
            this.fwFile = replName + ".yml";
      }

      public void startReplayRecording(ArrayList<PlayerObject> playerObjects) {
            FW fw = new FW(fwPath, fwFile);
            final int[] index = {0};
            for (PlayerObject playerObject : playerObjects) playerObject.setIndex(0);
            final int taskID = new BukkitRunnable() {
                  @Override
                  public void run() {
                        fw.setValue("playercount", playerObjects.size());
                        int count = 1;
                        for (PlayerObject playerObject : playerObjects) {
                              fw.setLocationAll(playerObject.getPlayer().getLocation(), index[0], playerObject.getPlayer().getName());
                              fw.setValue("num" + count + ".player", playerObject.getPlayer().getName());
                              playerObject.setIndex(index[0]);
                              count++;
                        }
                        index[0]++;
                        for (PlayerObject playerObject : playerObjects) {
                              if (index[0] % 20 == 0)
                                    Bukkit.getConsoleSender().sendMessage(playerObject.getPlayer().getName() + ": " + index[0]);
                              if (!playerObject.isReplayRecordMode() || index[0] > 6000 || !playerObject.getPlayer().isOnline()) {
                                    fw.setValue("length", playerObject.getIndex());
                                    fw.save();
                                    cancel();
                              }
                        }
                  }

            }.runTaskTimer(Main.getInstance(), 0, 1L).getTaskId();
            for (PlayerObject playerObject : playerObjects) {
                  playerObject.setTaskID(taskID);
                  playerObject.setFw(fw);
            }
      }

      public void playReplay() {
            final int[] index = {0};
            FW fwnew = new FW(fwPath, fwFile);

            int count = fwnew.getInt("playercount");
            HashMap<String, NPC> hashMap = new HashMap<>();
            ArrayList<String> arrayList = new ArrayList<>();
            for(int i = 1; i<=count; i++) {
                  NPC npc = new NPC(fwnew.getLocationAll(0, fwnew.getString("num"+i+".player")));
                  npc.spawn();
                  arrayList.add(fwnew.getString("num"+i+".player"));
                  hashMap.put(fwnew.getString("num"+i+".player"), npc);
            }
            new BukkitRunnable() {

                  @Override
                  public void run() {
                              if ((fwnew.getInt("length") >= index[0])) {
                                    for (String name : arrayList) {
                                          hashMap.get(name).teleport(fwnew.getLocationAll(index[0], name));
                                    }
                                    index[0]++;
                              } else if (!(fwnew.getInt("length") >= index[0])) {
                                    for (String name : arrayList) {
                                          hashMap.get(name).destroy();
                                    }
                                    cancel();
                              }
                        }

            }.runTaskTimer(Main.getInstance(), 1, 1L);
      }
}

