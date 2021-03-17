package de.legoshi.parkour.commands;

import de.legoshi.parkour.Main;
import de.legoshi.parkour.util.Replay;
import de.legoshi.parkour.util.player.PlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplayCommand implements CommandExecutor {

      /*
      /replay start <name>
      /replay stop <name>
      /replay play
       */

      @Override
      public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if(!(sender instanceof Player)) sender.sendMessage("You are not a Player.");
            if(!sender.isOp()) sender.sendMessage("You dont have permission for this.");

            if(args.length==2) {
                  String arg = args[0];
                  String name = args[1];
                  Player player = returnPlayer(name);

                  if(player==null) {
                        sender.sendMessage("Player not online!");
                        return false;
                  }

                  Replay replay = new Replay(player);
                  PlayerObject playerObject = Main.getInstance().playerManager.playerObjectHashMap.get(player);

                  if(arg.equals("start")) {
                        playerObject.setReplayRecordMode(true);
                        replay.startReplayRecording(playerObject);
                        //start recording
                  } else if(arg.equals("stop")) {
                        playerObject.setReplayRecordMode(false);
                        //stop recording
                  } else if(arg.equals("play")) {
                        replay.playReplay(player, name);
                  }
                  return false;
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
