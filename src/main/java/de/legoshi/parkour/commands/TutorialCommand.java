package de.legoshi.parkour.commands;

import de.legoshi.parkour.Main;
import de.legoshi.parkour.util.FW;
import de.legoshi.parkour.util.Message;
import de.legoshi.parkour.util.TextElement;
import de.legoshi.parkour.util.replay.Replay;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class TutorialCommand implements CommandExecutor {

      private final String path = "./TutorialFiles/";
      private final int startPosVal = 998;
      private final int endPosVal = 999;

      /*TODO:
      - /tutorial start <tutorialName>

      - /tutorial create <tutorialName>
      - /tutorial delete <tutorialName>
      - /tutorial add replay <tutorialName> <replayName>
      - /tutorial add start <tutorialName>
      - /tutorial add end <tutorialName>
      - /tutorial add timer <tutorialName> <timerName>
      - /tutorial add text <tutorialName> <number> <"text"> <duration>
      - /tutorial change text <tutorialName> <number> <"text"> <duration>
      */

      @Override
      public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if (!(sender instanceof Player)) {
                  sender.sendMessage("You are not a player!");
                  return false;
            }
            Player player = ((Player) sender).getPlayer();
            assert player != null;

            if(args.length > 1) {
                  switch ((args[0])) {
                        case "start":
                              try { startTutorial(player, args);
                              } catch (InterruptedException e) { e.printStackTrace(); }
                              return false;
                        case "create":
                              createTutorial(player, args);
                              return false;
                        case "delete":
                              deleteTutorial(player, args);
                              return false;
                        case "add":
                              switch ((args[1])) {
                                    case "replay":
                                          if (args.length == 4) {
                                                addReplay(player, args);
                                          } else player.sendMessage(Message.MSG_TUTORIAL_ADDREPLAY.getMessage());
                                          return false;
                                    case "start":
                                          if (args.length == 3) {
                                                addStart(player, args);
                                          } else player.sendMessage(Message.MSG_TUTORIAL_ADDSTART.getMessage());
                                          return false;
                                    case "end":
                                          if (args.length == 3) {
                                                addEnd(player, args);
                                          } else player.sendMessage(Message.MSG_TUTORIAL_ADDEND.getMessage());
                                          return false;
                                    case "timer":
                                          if (args.length == 4) {
                                                addTimer(player, args);
                                          } else player.sendMessage(Message.MSG_TUTORIAL_ADDTIMER.getMessage());
                                          return false;
                                    case "text":
                                          if (args.length >= 6) {
                                                addText(player, args);
                                          } else player.sendMessage(Message.MSG_TUTORIAL_ADDTEXT.getMessage());
                                          return false;
                              }
                              sendAddHelp(player);
                              return false;
                        case "change":
                              if (args.length >= 6) {
                                    changeText(player, args);
                              } else player.sendMessage(Message.MSG_TUTORIAL_CHANGETEXT.getMessage());
                              return false;
                  }
            } else sendHelp(player);
            return false;
      }

      private void startTutorial(Player player, String[] args) throws InterruptedException {
            FW fw = new FW(path, args[1] + ".yml");
            if(fw.exist()) {
                  Location start = fw.getLocation(startPosVal);
                  Location end = fw.getLocation(endPosVal);
                  //int timer = fw.getInt("timer");
                  String replay = fw.getString("replay");
                  Replay r = new Replay(replay);
                  ArrayList<TextElement> textElements = new ArrayList<>();

                  for(int i = 1; i < fw.getInt("textamount"); i++) {
                        textElements.add(new TextElement(fw.getString(i +".text"), fw.getInt(i +".time")));
                  }
                  //get all people with participant role
                  for(Player all : Bukkit.getOnlinePlayers()) {
                        all.setGameMode(GameMode.SPECTATOR);
                        all.teleport(start);
                  }
                  r.playReplay();
                  int totalTime = 0;
                  for(TextElement textElement : textElements) {
                        System.out.println(textElements.size());
                        for(Player all : Bukkit.getOnlinePlayers()) {
                              totalTime = textElement.getTime() + totalTime;
                              Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() { //Problem because starting multiple schedulers at once?
                                    @Override
                                    public void run() {
                                          all.sendMessage(textElement.getMessage().replaceAll("\"",""));
                                    }
                              }, totalTime*20L);
                        }
                  }
                  Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                              for(Player all : Bukkit.getOnlinePlayers()) {
                                    all.teleport(end);
                                    all.setGameMode(GameMode.ADVENTURE);
                              }
                        }
                  }, (5+totalTime)*20L);
            } else player.sendMessage(Message.ERR_TUTORIAL_NOTEXIST.getMessage());
      }

      public void createTutorial(Player player, String[] args) {
            FW fw = new FW(path, args[1] + ".yml");
            if(!fw.exist()) {
                  fw.setValue("replay", "");
                  fw.setValue("timer", "");
                  fw.setValue("textamount", 1);
                  fw.setValue("1.text", "");
                  fw.setValue("1.time", 0);
                  fw.setLocation(player.getLocation(), startPosVal);
                  fw.setLocation(player.getLocation(), endPosVal);
                  fw.save();
                  player.sendMessage(Message.SUCC_TUTORIAL_CREATE.getMessage());
            } else player.sendMessage(Message.ERR_TUTORIAL_CREATE.getMessage());
      }

      public void deleteTutorial(Player player, String[] args) {
            File file = new File(path + args[1] + ".yml");
            if(file.exists()) {
                  if(file.delete()) {
                        player.sendMessage("Succ");
                        player.sendMessage(Message.SUCC_TUTORIAL_DELETE.getMessage());
                  }
                  else player.sendMessage(Message.ERR_TUTORIAL_DELETE.getMessage());
            }
      }

      public void addStart(Player player, String[] args) {
            FW fw = new FW(path, args[2] + ".yml");
            if(fw.exist()) {
                  fw.setLocation(player.getLocation(), startPosVal);
                  fw.save();
                  player.sendMessage(Message.SUCC_TUTORIAL_ADDSTART.getMessage());
            } else player.sendMessage(Message.ERR_TUTORIAL_NOTEXIST.getMessage());
      }

      public void addEnd(Player player, String[] args) {
            FW fw = new FW(path, args[2] + ".yml");
            if(fw.exist()) {
                  fw.setLocation(player.getLocation(), endPosVal);
                  fw.save();
                  player.sendMessage(Message.SUCC_TUTORIAL_ADDEND.getMessage());
            } else player.sendMessage(Message.ERR_TUTORIAL_NOTEXIST.getMessage());
      }

      public void addReplay(Player player, String[] args) {
            FW fw = new FW(path, args[2] + ".yml");
            if(fw.exist()) {
                  fw.setValue("replay", args[3]);
                  fw.save();
                  player.sendMessage(Message.SUCC_TUTORIAL_ADDREPLAY.getMessage());
            } else player.sendMessage(Message.ERR_TUTORIAL_NOTEXIST.getMessage());
      }

      private void addText(Player player, String[] args) {
            FW fw = new FW(path, args[2] + ".yml");
            if(fw.exist()) {
                  int i;
                  StringBuilder stringBuilder = new StringBuilder();
                  for(i = 5; (i < args.length && !args[i].contains("\"")); i++) {
                        stringBuilder.append(args[i-1]).append(" ");
                  }
                  String txt = stringBuilder.append(args[i]).toString();
                  txt = txt.replaceAll("\"", " ");

                  int num;
                  try { num = Integer.parseInt(args[i+1]); }
                  catch (NumberFormatException e) {
                        player.sendMessage(Message.ERR_NOTANUMBER.getMessage());
                        return;
                  }
                  int val = fw.getInt("textamount");
                  fw.setValue(val + ".text", txt);
                  fw.setValue(val + ".time", num);
                  fw.setValue("textamount", val+1);
                  fw.save();
                  player.sendMessage(Message.SUCC_TUTORIAL_ADDTEXT.getMessage());
            } else player.sendMessage(Message.ERR_TUTORIAL_NOTEXIST.getMessage());
      }

      private void changeText(Player player, String[] args) {
            FW fw = new FW(path, args[2] + ".yml");
            if(fw.exist()) {
                  int i = 5;
                  StringBuilder stringBuilder = new StringBuilder();
                  for(; (i < args.length && !args[i].contains("\"")); i++) {
                        stringBuilder.append(args[i-1]).append(" ");
                  }
                  String txt = stringBuilder.append(args[i]).toString();
                  txt = txt.replaceAll("\"", " ");
                  int num;
                  try { num = Integer.parseInt(args[5]); }
                  catch (NumberFormatException e) {
                        player.sendMessage(Message.ERR_NOTANUMBER.getMessage());
                        return;
                  }
                  if(num > 0 && num <= fw.getInt("textamount")) {
                        int val = fw.getInt("textamount");
                        fw.setValue(val + ".text", txt);
                        fw.setValue(val + ".time", num);
                        fw.save();
                  } else {
                        player.sendMessage(Message.ERR_TUTORIAL_TEXTDOESNTEXIST.getMessage());
                        return;
                  }
            } else player.sendMessage(Message.ERR_TUTORIAL_NOTEXIST.getMessage());
      }

      private void addTimer(Player player, String[] args) {
            FW fw = new FW(path, args[2] + ".yml");
            int num;
            try { num = Integer.parseInt(args[3]); }
            catch (NumberFormatException e) {
                  player.sendMessage(Message.ERR_NOTANUMBER.getMessage());
                  return;
            }
            if(fw.exist()) {
                  fw.setValue("timer", num);
                  fw.save();
            } else player.sendMessage(Message.ERR_TUTORIAL_NOTEXIST.getMessage());
      }

      public void sendHelp(Player player) {
            player.sendMessage(Message.MSG_TUTORIAL_START.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_CREATE.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_DELETE.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_ADDREPLAY.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_ADDSTART.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_ADDEND.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_ADDTIMER.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_ADDTEXT.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_CHANGETEXT.getMessage());
      }

      public void sendAddHelp(Player player) {
            player.sendMessage(Message.MSG_TUTORIAL_ADDREPLAY.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_ADDSTART.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_ADDEND.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_ADDTIMER.getMessage());
            player.sendMessage(Message.MSG_TUTORIAL_ADDTEXT.getMessage());
      }

}
