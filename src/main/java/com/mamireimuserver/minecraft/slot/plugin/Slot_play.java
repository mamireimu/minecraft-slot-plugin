/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mamireimuserver.minecraft.slot.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author mami
 */
public class Slot_play implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
            int loop[]= new int[9];
            sender.sendMessage("スロット開始します!"); //コマンドを実行した人に「Hi!」とメッセージを送る。
            for(int for_int=0;for_int<9;for_int++){
                while(0<loop[for_int]) {
                loop[for_int] = (int)(Math.random() * 10);
                }

                if(for_int==2||for_int==5||for_int==8){
                    //sender.sendMessage(loop[for_int-2]+" "+loop[for_int-1]+" "+loop[for_int]+" ");
                    sender.sendMessage(for_int +" "+loop[for_int]);
                }
                else{sender.sendMessage(for_int +" "+loop[for_int]+"!!!");}
            }
            sender.sendMessage("スロット終了します。");
            
            return false;
	}
}
