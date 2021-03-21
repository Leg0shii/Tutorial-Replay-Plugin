package de.legoshi.parkour.util;

import org.bukkit.ChatColor;

public enum Message {

      Prefix(ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + ChatColor.BOLD + "EVANMC" + ChatColor.RESET + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY),
      SUCC_TUTORIAL_CREATE("Successfully created Tutorial!"),
      SUCC_TUTORIAL_DELETE("Successfully deleted Tutorial!"),
      SUCC_TUTORIAL_ADDREPLAY("Successfully added replay to the Tutorial!"),
      SUCC_TUTORIAL_ADDSTART("Successfully added a starting point to the Tutorial!"),
      SUCC_TUTORIAL_ADDEND("Successfully added an ending point to the Tutorial!"),
      SUCC_TUTORIAL_ADDTIMER("Successfully added a timer to the Tutorial!"),
      SUCC_TUTORIAL_ADDTEXT("Successfully added text to the Tutorial!"),
      SUCC_TUTORIAL_CHANGETEXT("Successfully removed text from the Tutorial!"),

      MSG_TUTORIAL_CREATE("/tutorial create <tutorialName>"),
      MSG_TUTORIAL_DELETE("/tutorial delete <tutorialName>"),
      MSG_TUTORIAL_ADDREPLAY("/tutorial add replay <tutorialName> <replayName>"),
      MSG_TUTORIAL_ADDSTART("/tutorial add start <tutorialName>"),
      MSG_TUTORIAL_ADDEND("/tutorial add end <tutorialName>"),
      MSG_TUTORIAL_ADDTIMER("/tutorial add timer <tutorialName> <timerName>"),
      MSG_TUTORIAL_ADDTEXT("/tutorial add text <tutorialName> <number> <text> <duration>"),
      MSG_TUTORIAL_CHANGETEXT("/tutorial change text <tutorialName> <number> <text> <duration>"),

      ERR_TUTORIAL_CREATE("Tutorial name already in use!"),
      ERR_TUTORIAL_DELETE("This tutorial couldnt be deleted!"),
      ERR_TUTORIAL_NOTEXIST("This tutorial does not exist!"),
      ERR_TUTORIAL_TEXTDOESNTEXIST("This text index doesnt exist!"),

      ERR_NOTANUMBER("Please enter a number!");


      String m;

      Message(String message) {
            this.m = message;
      }

      public String getMessage() {
            return Message.Prefix.getRawMessage() + this.m;
      }
      public String getRawMessage() {
            return this.m;
      }

}
