package de.legoshi.parkour.commands;

import de.legoshi.parkour.Main;
import de.legoshi.parkour.util.Message;
import de.legoshi.parkour.util.player.PlayerObject;
import de.legoshi.parkour.util.replay.Replay;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ReplayCommand implements CommandExecutor {

      @Override
      public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            /*
            - /replay start <filename>
            - /replay stop <filename>
            - /replay play <filename>

            save player in replay inside file so no need to give playerlist
             */

            if (!(sender instanceof Player)) sender.sendMessage(Message.ERR_NOTAPLAYER.getMessage());
            if (!sender.isOp()) sender.sendMessage(Message.ERR_NOPERMISSIONS.getMessage());
            Player commandsender = ((Player) sender).getPlayer();

            if (args.length >= 2) {

                  String arg = args[0];
                  String replname = args[1];
                  Replay replay = new Replay(replname);

                  ArrayList<PlayerObject> playerObjectArrayList = new ArrayList<>();
                  for(Player rec : Bukkit.getOnlinePlayers()) {
                        playerObjectArrayList.add(Main.getInstance().playerManager.playerObjectHashMap.get(rec));
                  }

                  if(playerObjectArrayList.size() == 0) {
                        commandsender.sendMessage(Message.ERR_REPLAY_NOPLAYERS.getMessage());
                        return false;
                  }

                  switch (arg) {
                        case "start":
                              //start recording
                              if(args.length == 2) {
                                    for (PlayerObject playerObject : playerObjectArrayList)
                                          playerObject.setReplayRecordMode(true);
                                    replay.startReplayRecording(playerObjectArrayList);
                                    commandsender.sendMessage(Message.SUCC_REPLAY_START.getMessage().replace("$filename", replname));
                              } else commandsender.sendMessage(Message.MSG_REPLAY_PLAY.getMessage());
                              break;
                        case "stop":
                              //stop recording
                              if(args.length == 2) {
                                    for (PlayerObject playerObject : playerObjectArrayList)
                                          playerObject.setReplayRecordMode(false);
                                    commandsender.sendMessage(Message.SUCC_REPLAY_STOP.getMessage().replace("$filename", replname));
                              } else commandsender.sendMessage(Message.MSG_REPLAY_PLAY.getMessage());
                              break;
                        case "play":
                              if(args.length == 2) {
                                    commandsender.sendMessage(Message.SUCC_REPLAY_PLAY.getMessage().replace("$filename", replname));
                                    replay.playReplay();
                              } else commandsender.sendMessage(Message.MSG_REPLAY_PLAY.getMessage());
                              break;
                  }
                  return false;
            }
            sendHelp(commandsender);
            return false;


      }

      public Player returnPlayer(String name) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                  System.out.println("Name: " + name + "!");
                  System.out.println("NameOnline: " + all.getName() + "!");
                  if (all.getName().equals(name)) return all;
            }
            return null;
      }

      public void sendHelp(Player player) {
            player.sendMessage(Message.MSG_REPLAY_START.getMessage());
            player.sendMessage(Message.MSG_REPLAY_STOP.getMessage());
            player.sendMessage(Message.MSG_REPLAY_PLAY.getMessage());
      }

}
