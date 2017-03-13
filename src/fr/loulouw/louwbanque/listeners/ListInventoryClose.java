package fr.loulouw.louwbanque.listeners;

import fr.loulouw.louwbanque.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ListInventoryClose implements Listener {

    public ListInventoryClose(){
        Main.javaPlugin.getServer().getPluginManager().registerEvents(this,Main.javaPlugin);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        String playername = p.getName();
        if(e.getInventory().getTitle().contains("Banque de " + ChatColor.DARK_BLUE + p.getName())){ //On verifie que l'inventaire ferme est bien une banque
            String number = e.getInventory().getTitle().substring(e.getInventory().getTitle().length()-1);
            File file = new File("plugins/LouwBanque/Banque/"+playername + number + ".yml");
            ArrayList<ItemStack> list = new ArrayList<>(); //On creer une ArrayList d'item
            YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);//On charge le fichier contenant les items
            ItemStack[] contents = e.getInventory().getContents();//On stock tout les items dans contents a partir de l'inventaire ferm� (e.getInventory())
            for (ItemStack item : contents) {
                if (!(item == null)) {
                    list.add(item); //On les ajoutes a une liste
                }
            }
            inv.set("Inventory", list); //On ecrit dans le fichier
            try {
                inv.save(file); //On le sauvegarde
            } catch (IOException evv) {
                evv.printStackTrace();
            }
        }
    }
}
