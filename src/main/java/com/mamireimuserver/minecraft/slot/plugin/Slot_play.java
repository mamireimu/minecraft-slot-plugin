/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mamireimuserver.minecraft.slot.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


/**
 *
 * @author mami
 */
public class Slot_play implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
            if(sender instanceof Player)
            {
                Player p = (Player)sender;
                int loop[]= new int[9];
                int slot_hit=0,slot_lucky=0;

                //config読み込み
                FileConfiguration config = Main.plugin.getConfig();
                    String import_item=config.getString("item.input");
                    String out_item=config.getString("item.out_item");
                    String out_block=config.getString("item.out_block");
                    int input_count=config.getInt("input_count");
                    int out_count=config.getInt("out_count");
                    int out_exchange=config.getInt("out_exchange");
                    int out_magnification=config.getInt("out_magnification");

                sender.sendMessage("スロット開始します!"); //コマンドを実行した人に「Hi!」とメッセージを送る。
                for(int for_int=0;for_int<9;for_int++){

                    //ランダム関数から出力時0の時だけ振り直し
                    do {
                    loop[for_int] = (int)(Math.random() * 10);
                    } while(0>=loop[for_int]);
                }
                new BukkitRunnable()
                {
                        private int counter = 0;
                        @Override
                        public void run()
                        {
                                if(p.isOnline())
                                {
                                        p.sendMessage(loop[0+(counter*3)]+" "+loop[1+(counter*3)]+" "+loop[2+(counter*3)]+" ");
                                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1.0f, 0.65f);

                                        if(counter<2)
                                        {
                                                counter++;
                                        }
                                        else
                                        {
                                                int atari = 0;
                                                /*
                                                 * 0 1 2
                                                 * 3 4 5
                                                 * 6 7 8
                                                 */
                                                //横
                                                if(loop[0]==loop[1]&&loop[1]==loop[2])atari++;
                                                if(loop[3]==loop[4]&&loop[4]==loop[5])atari++;
                                                if(loop[6]==loop[7]&&loop[7]==loop[8])atari++;

                                                //縦
                                                if(loop[0]==loop[3]&&loop[3]==loop[6])atari++;
                                                if(loop[1]==loop[4]&&loop[4]==loop[7])atari++;
                                                if(loop[2]==loop[5]&&loop[5]==loop[8])atari++;

                                                //斜め
                                                if(loop[0]==loop[4]&&loop[4]==loop[8])atari++;
                                                if(loop[2]==loop[4]&&loop[4]==loop[6])atari++;

                                                if(atari>0)
                                                {
                                                        p.sendMessage("§a当たりが§e" + atari + "§a個でました。");
                                                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + p.getName()
                                                                        + " emerald " + atari);
                                                        slot_hit=atari;
                                                        Main.
                                                }
                                                else
                                                {
                                                        p.sendMessage("§c当たりがでませんでした。");
                                                }
                                                this.cancel();
                                        }
                                }
                                else
                                {
                                        this.cancel();
                                }
                        }
                }.runTaskTimer(Main.plugin, 10, 20);// 10ticks遅延したあと、20ticksごとに処理。

            
                //スロットでの当選判定
                for(int f_i=0;f_i<=3;f_i++){
                    if(loop[f_i]==loop[f_i+3]&&loop[f_i]==loop[f_i+6]){
                        if(loop[f_i]==7){
                        slot_lucky++;
                        }else{
                        slot_hit++;
                        }   
                    }
                }

                
            
                 //当選計算
                int winning_coin_normal=out_count*(out_magnification^slot_hit);
                int winning_coin_lucky=out_count*(out_magnification^slot_lucky);
                int winning_coin_total=winning_coin_normal+winning_coin_lucky;

                int give_item_count=winning_coin_total%out_exchange;
                int give_block_count=winning_coin_total/out_exchange;

                //当選金額支給
                if(give_item_count!=0){
                player.getInventory().addItem(new ItemStack(Material.EMERALD_BLOCK, give_block_count));
                }
                player.getInventory().addItem(new ItemStack(Material.EMERALD, give_item_count));

               sender.sendMessage("スロット終了します。");
            }
               
            return false;
	}
}
