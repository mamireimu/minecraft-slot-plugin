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
    
    
    public static Main plagin;
       
    /**
     * 起動時処理
     */
    @Override
    public void onEnable() {
        plugin=this;
        getLogger().info("スロット有効化");
        saveDefaultConfig();
        
        String import_item=getConfig().getString("item.input");
        String out_item=getConfig().getString("item.out");
        int input_count=getConfig().getInt("input_count");
        int out_count1=getConfig().getInt("out_count.count1");
        int out_count2=getConfig().getInt("out_count.count2");
        int out_count3=getConfig().getInt("out_count.count3");
        int out_count4=getConfig().getInt("out_count.count4");
        int out_count5=getConfig().getInt("out_count.count5");
        int out_count6=getConfig().getInt("out_count.count6");
        int out_count7=getConfig().getInt("out_count.count7");
        int out_count8=getConfig().getInt("out_count.count8");
        int out_count9=getConfig().getInt("out_count.count9");
        
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
}
