package fr.loulouw.louwbanque;

import fr.loulouw.louwbanque.commandes.CommandBanque;
import fr.loulouw.louwbanque.listeners.ListInventoryClose;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    public static JavaPlugin javaPlugin;

    @Override
    public void onEnable(){
        Main.javaPlugin = this;

        new ListInventoryClose();
        getCommand("banque").setExecutor(new CommandBanque());
    }


}
