/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mamireimuserver.minecraft.slot.plugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author mami
 */
public class Main extends JavaPlugin implements Listener{
    
    
    public static Main plugin;
       
    /**
     * 起動時処理
     */
    @Override
    public void onEnable() {
        plugin=this;
        getLogger().info("スロット有効化");
        saveDefaultConfig();
        
        String import_item=getConfig().getString("item.input");
        String out_item=getConfig().getString("item.out_item");
        String out_block=getConfig().getString("item.out_block");
        int input_count=getConfig().getInt("input_count");
        int out_count=getConfig().getInt("out_count");
        int out_exchange=getConfig().getInt("out_exchange");
        
        getCommand("slot_play").setExecutor(new Slot_play());
    }

    /**
     * 終了時処理
     */
    @Override
    public void onDisable() {
        getLogger().info("スロット無効化");
        saveConfig();
    }
    
    public statuc void Hallo(int loop_go){
        
    }
}
