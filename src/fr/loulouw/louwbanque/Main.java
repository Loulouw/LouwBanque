package fr.loulouw.louwbanque;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.loulouw.louwbanque.commandes.CommandBanque;
import fr.loulouw.louwbanque.listeners.ListInventoryClose;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    public static JavaPlugin javaPlugin;
    public static WorldGuardPlugin worldGuard;

    @Override
    public void onEnable(){
        Main.javaPlugin = this;

        worldGuard = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");

        new ListInventoryClose();
        getCommand("banque").setExecutor(new CommandBanque());
    }


}
