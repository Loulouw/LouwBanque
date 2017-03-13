package fr.loulouw.louwbanque;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.loulouw.louwbanque.commandes.CommandBanque;
import fr.loulouw.louwbanque.listeners.ListInventoryClose;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    public static JavaPlugin javaPlugin;
    public static WorldGuardPlugin worldGuard;

    @Override
    public void onEnable(){
        Main.javaPlugin = this;



        getLogger().info("[*] LouwBanque en cours de demarrage...");
        getLogger().info("[*] ====");
        getLogger().info("[*] Chargement des dependances... ");
        worldGuard = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");
        new ListInventoryClose();
        getLogger().info("[*] dependances chargees");
        getLogger().info("[*] ====");
        getLogger().info("[*] Chargement des commandes... ");
        getCommand("banque").setExecutor(new CommandBanque());
        getLogger().info("[*] Commandes chargees");
        getLogger().info("[*] ====");

        getLogger().info("[*] LouwBanque est demarre");
    }

    @Override
    public void onDisable(){
        for(Player p : getServer().getOnlinePlayers()) p.closeInventory();
    }


}
