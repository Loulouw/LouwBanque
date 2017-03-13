package fr.loulouw.louwbanque.commandes;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.loulouw.louwbanque.Main;
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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        this.checkFichier(); //On vérifie si le dossier Banque existe

        if (!(sender instanceof Player)) { //On vérifie que la commande provient d'un joueur et non de la consle
            ConsoleCommandSender cs = (ConsoleCommandSender) sender;
            cs.sendMessage("Vous n'êtes pas un joueur");
            return true;
        }
        Player p = (Player) sender; //On récupère le joueur qui a envoyé la commande

        if (!checkRegion(p)) {
            p.sendMessage(ChatColor.RED + "Vous n'êtes pas dans une de vos maisons");
            return true;
        }

        String numBanque = "1";

        if (args.length != 0) {
            if (args[0].equals("1") | args[0].equals("2") | args[0].equals("3")) {
                numBanque = args[0];
            }
        }

        generateInventory(p, numBanque);
        return true;
    }

    private void generateInventory(Player p, String numBanque) {
        String playername = p.getName(); //Son nom

        int tailleBanque = 6 * 9;
        File file = new File("plugins/LouwBanque/Banque/" + playername + numBanque + ".yml");

        if (!file.exists()) { //Si le fichier n'existe pas on le creer
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration inv = YamlConfiguration.loadConfiguration(file); //On charge le fichier
        ItemStack[] contents = new ItemStack[tailleBanque]; //Creation d'un tableau d'item
        List<?> list = inv.getList("Inventory"); //On recupere les item du fichier et on les stock
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null) contents[i] = (ItemStack) list.get(i); //on les ajoutes dans le tableau d'item
            }
        }

        Inventory invF = Bukkit.createInventory(null, tailleBanque, "Banque de " + ChatColor.DARK_BLUE + playername + " " + ChatColor.DARK_PURPLE + numBanque); //On crée un inventaire avec un titre spécial
        invF.clear(); //on le clear (ca sert pas a grand chose je pense mais au cas ou)
        invF.setContents(contents); // on lui attribut tout les item contenant dans contents
        p.openInventory(invF); //on fait ouvrir l'inventaire au joueur
    }

    private boolean checkRegion(Player p) {
        RegionManager regionManager = Main.worldGuard.getRegionManager(p.getWorld());
        LocalPlayer lp = Main.worldGuard.wrapPlayer(p);
        ApplicableRegionSet applicableRegions = regionManager.getApplicableRegions(lp.getPosition());
        for (ProtectedRegion pr : applicableRegions) return pr.isMember(lp);
        return false;
    }


    private void checkFichier() {
        File file = new File("plugins/LouwBanque/Banque");
        if (!file.exists()) {
            file.mkdir(); //Si non on le creer
        }
    }
}
