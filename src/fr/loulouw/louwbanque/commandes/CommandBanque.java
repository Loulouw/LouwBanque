package fr.loulouw.louwbanque.commandes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CommandBanque implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {

        this.checkFichier(); //On vérifie si le dossier Banque existe

        if(!(sender instanceof Player)){ //On vérifie que la commande provient d'un joueur et non de la consle
            ConsoleCommandSender cs = (ConsoleCommandSender) sender;
            cs.sendMessage("Vous n'êtes pas un joueur");
            return true;
        }
        Player p = (Player) sender; //On récupère le joueur qui a envoyé la commande

        generateInventory(p);
        return true;
    }

    private void generateInventory(Player p){
        String playername = p.getName(); //Son nom

        int tailleBanque = 6*9;
        File file = new File("plugins/BePlugBanque/Banque/"+playername+".yml");

        if(!file.exists()){ //Si le fichier n'existe pas on le creer
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration inv = YamlConfiguration.loadConfiguration(file); //On charge le fichier
        ItemStack[] contents = new ItemStack[tailleBanque]; //Creation d'un tableau d'item
        List<?> list = inv.getList("Inventory"); //On recupere les item du fichier et on les stock
        if(list!=null){
            for(int i=0;i<list.size();i++){
                if(list.get(i)!=null) contents[i] = (ItemStack) list.get(i); //on les ajoutes dans le tableau d'item
            }
        }

        Inventory invF = Bukkit.createInventory(null, tailleBanque,"Banque de " + ChatColor.DARK_BLUE + playername); //On crée un inventaire avec un titre spécial
        invF.clear(); //on le clear (ca sert pas a grand chose je pense mais au cas ou)
        invF.setContents(contents); // on lui attribut tout les item contenant dans contents
        p.openInventory(invF); //on fait ouvrir l'inventaire au joueur
    }


    private void checkFichier(){
        File file=  new File("plugins/LouwBanque/Banque");
        if(!file.exists()){
            file.mkdir(); //Si non on le creer
        }
    }
}
