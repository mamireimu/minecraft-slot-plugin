/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mamireimuserver.minecraft.slot.plugin;

/**
 *
 * @author mami
 */

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TestGiveTask  extends BukkitRunnable {

    private Player player;
    private String msg;

    public TestGiveTask(Player p,Staring s) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        // What you want to schedule goes here
        plugin.getServer().broadcastMessage("Welcome to Bukkit! Remember to read the documentation!");
    }

