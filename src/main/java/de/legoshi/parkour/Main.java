package de.legoshi.parkour;

import de.legoshi.parkour.commands.ReplayCommand;
import de.legoshi.parkour.events.JoinListener;
import de.legoshi.parkour.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

      public static Main instance;
      public PlayerManager playerManager;

      @Override
      public void onEnable() {

            instance = this;
            playerManager = new PlayerManager();
            createFolders();
            CommandRegistration();
            ListenerRegistration();

      }

      @Override
      public void onDisable() {
            // Plugin shutdown logic
      }

      public static Main getInstance() {
            return instance;
      }

      private void CommandRegistration() {

            getCommand("replay").setExecutor(new ReplayCommand());

      }

      private void ListenerRegistration() {

            PluginManager pm = Bukkit.getPluginManager();
            pm.registerEvents(new JoinListener(), this);

      }

      private void createFolders() {

            File file = new File("./ParkourReplays/");
            file.mkdir();

      }

}
