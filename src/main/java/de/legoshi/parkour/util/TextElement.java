package de.legoshi.parkour.util;

public class TextElement {

      String message;
      int time;

      public TextElement(String message, int time) {
            this.message = message;
            this.time = time;
      }

      public int getTime() {
            return time;
      }

      public void setTime(int time) {
            this.time = time;
      }

      public String getMessage() {
            return message;
      }

      public void setMessage(String message) {
            this.message = message;
      }

}
