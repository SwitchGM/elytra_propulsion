package com.Switch.elytra_propulsion;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Created by SwitchGM,
 * Project : Elytra Propulsion
 * Package : com.Switch.elytra_propulsion
 * ©2018 JoeAmphlett
 */
public class Main extends JavaPlugin implements Listener {

    HashMap<Player, Long> cooldownMap = new

    @Override
    public void onEnable() {
        setupConfigFile();
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = this.getConfig();

        Material material = Material.getMaterial(config.getString("material"));

        if (player.isGliding()) {
            if (player.getInventory().getItemInMainHand().getType() == material) {
                if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {

                }
            }
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
