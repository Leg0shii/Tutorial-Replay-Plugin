package de.legoshi.parkour.commands;

import de.legoshi.parkour.Main;
import de.legoshi.parkour.util.replay.Replay;
import de.legoshi.parkour.util.player.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplayCommand implements CommandExecutor {

      @Override
      public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if(!(sender instanceof Player)) sender.sendMessage("You are not a Player.");
            if(!sender.isOp()) sender.sendMessage("You dont have permission for this.");

            if(args.length >= 2) {
                  String playerName = args[1];
                  Player player = returnPlayer(playerName);

                  if (args.length == 3) {
                        String arg = args[0];
                        String name = "Tutorial";
                        String replname = args[2];

                        if (player == null) {
                              sender.sendMessage("Player not online!");
                              return false;
                        }

                        Replay replay = new Replay(player, replname);
                        PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);

                        switch (arg) {
                              case "start":
                                    //start recording
                                    playerObject.setReplayRecordMode(true);
                                    replay.startReplayRecording(playerObject);
                                    player.sendMessage("Replay of " + name + " started successfully");
                                    break;
                              case "stop":
                                    //stop recording
                                    playerObject.setReplayRecordMode(false);
                                    player.sendMessage("Replay of " + name + " stopped successfully");
                                    break;
                              case "play":
                                    player.sendMessage("Started playing replay of " + name + ".");
                                    replay.playReplay(name);
                                    break;
                        }
                        return false;
                  } else player.sendMessage("Wrong syntax for replay!");
            }
            return false;

      }

      public Player returnPlayer(String name) {
            for(Player all : Bukkit.getOnlinePlayers()) {
                  if(all.getName().equals(name)) return all;
            }
            return null;
      }

}
