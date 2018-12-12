package com.Switch.elytra_propulsion;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;

/**
 * Created by SwitchGM,
 * Project : Elytra Propulsion
 * Package : com.Switch.elytra_propulsion
 * ©2018 JoeAmphlett
 */
public class Main extends JavaPlugin implements Listener {

    private HashMap<Player, Long> cooldownMap = new HashMap<>();

    @Override
    public void onEnable() {
        setupConfigFile();
        getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {

        Player player = (Player) sender;

        if (command.equalsIgnoreCase("elytrapropulsion") || command.equalsIgnoreCase("elytra") || command.equalsIgnoreCase("ep")) {
            PluginDescriptionFile pluginFile = this.getDescription();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Elytra Propulsion:"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6 Version: " + pluginFile.getVersion()));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6 Author: " + pluginFile.getAuthors()));
        }
        return false;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (player.hasPermission("elytrapropulsion.boost")) {
            if (player.isGliding()) {

                FileConfiguration config = this.getConfig();
                Material material = Material.getMaterial(config.getString("material"));

                if (player.getInventory().getItemInMainHand().getType() == material) {
                    if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
                        if (cooldownMap.containsKey(player)) {

                            int cooldown = config.getInt("cooldown");

                            if (cooldownMap.get(player) + (cooldown * 1000) <= System.currentTimeMillis()) {
                                cooldownMap.remove(player);

                            } else {
                                String cooldownMessage = config.getString("cooldown_message");
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', cooldownMessage));
                                return;

                            }
                        }

                        int multiplier = config.getInt("multiplier");
                        Vector vector = player.getLocation().getDirection();
                        Vector newVector = new Vector(vector.getX() * multiplier, vector.getY() * multiplier, vector.getZ() * multiplier);

                        player.setVelocity(newVector);
                        cooldownMap.put(player, System.currentTimeMillis());
                    }
                }
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to do this"));
        }
    }

    private void setupConfigFile() {
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();

        config.addDefault("material", "STICK");
        config.addDefault("multiplier", 5);
        config.addDefault("cooldown", 3);
        config.addDefault("cooldown_message", "&7You are on cooldown !");

        saveConfig();
    }
}
