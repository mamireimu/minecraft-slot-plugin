/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mamireimuserver.minecraft.slot.plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


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
				Player player = (Player)sender;
				String uuid = player.getUniqueId().toString();
				if(!rolling.contains(uuid))
				{
					int kake = inputAmount;
					int typeId = inputTypeID;
					if(typeId>0 && kake>0)
					{
						if(removeItem(player.getInventory(), Material.getMaterial(typeId), 0, kake))
						{
							rolling.add(uuid);
							Random rnd = new Random();
							int[] i = new int[9];
							for(int n=0;n<=8;n++)
							{
								i[n]=rnd.nextInt(8)+1;
							}
							int atari=0;
							int atari7=0;
							/*
							 * 0 1 2
							 * 3 4 5
							 * 6 7 8
							 */
							//横
							if(i[0]==i[1] && i[1]==i[2]){atari++; if(i[0]==7){atari7++;}}
							if(i[3]==i[4] && i[4]==i[5]){atari++; if(i[3]==7){atari7++;}}
							if(i[6]==i[7] && i[7]==i[8]){atari++; if(i[6]==7){atari7++;}}
							//縦
							if(i[0]==i[3] && i[3]==i[6]){atari++; if(i[0]==7){atari7++;}}
							if(i[1]==i[4] && i[4]==i[7]){atari++; if(i[1]==7){atari7++;}}
							if(i[2]==i[5] && i[5]==i[8]){atari++; if(i[2]==7){atari7++;}}
							//斜め
							if(i[0]==i[4] && i[4]==i[8]){atari++; if(i[0]==7){atari7++;}}
							if(i[2]==i[4] && i[4]==i[6]){atari++; if(i[2]==7){atari7++;}}
							int reward = 0;
							if(atari7>0)
							{
								reward = ((10^atari)+(7^atari7))*kake;
							}
							else
							{
								reward = (10^atari)*kake;
							}
							final int atariClone = atari;
							final int atari7Clone = atari7;
							final int rewardClone = reward;
							new BukkitRunnable()
							{
								private int counter = 2;
								@Override
								public void run()
								{
									if(player.isOnline())
									{
										switch(counter)
										{
											case 2:
											{
												player.sendMessage(prefix+"§e"+i[0]+" "+i[1]+" "+i[2]);
												player.playSound(player.getLocation(),
														Sound.BLOCK_NOTE_PLING, 1.0f, 1.0f);
												break;
											}
											case 1:
											{
												player.sendMessage(prefix+"§e"+i[3]+" "+i[4]+" "+i[5]);
												player.playSound(player.getLocation(),
														Sound.BLOCK_NOTE_PLING, 1.0f, 1.0f);
												break;
											}
											default:
											{
												player.sendMessage(prefix+"§e"+i[6]+" "+i[7]+" "+i[8]);
												player.playSound(player.getLocation(),
														Sound.BLOCK_NOTE_PLING, 1.0f, 2.0f);
												if(atariClone>0)
												{
													player.sendMessage(prefix+"§6アタリが§e"+atariClone+"§6個、うち7のアタリが§e"
															+atari7Clone+"§6個揃ったので§c"+rewardClone+"§6個の報酬アイテムが貰えました！");
													player.playSound(player.getLocation(),
															Sound.ENTITY_PLAYER_LEVELUP, 1.25f, 0.5f);
													HashMap<Integer, ItemStack> map = player.getInventory()
															.addItem(new ItemStack(Material.getMaterial(outputTypeID), rewardClone));
													if(map.size()>0)
													{
														player.sendMessage(prefix+"§cインベントリに入りきらなかったアイテムが地面に散らばりました。");
														for(int i : map.keySet())
														{
															Item item = player.getWorld().dropItem(
																	player.getLocation(), map.get(i));
															item.setVelocity(new Vector(0,0,0));
															noItemPickDelay(item);
														}
													}
												}
												else
												{
													player.sendMessage(prefix+"§cアタリが1つも出ませんでした。");
												}
												rolling.remove(uuid);
												this.cancel();
												break;
											}
										}
										counter--;
									}
									else
									{
										rolling.remove(uuid);
										this.cancel();
									}
								}
							}.runTaskTimer(this, 0, 20);
						}
						else
						{
							player.sendMessage(prefix+"§cスロットに必要なアイテムが足りません。\n"
									+ prefix + "§c必要なアイテムと数: " + Material.getMaterial(inputTypeID)+ "(" + inputAmount+"個)");
						}
					}
					else
					{
						player.sendMessage(prefix+"§cスロットに必要なアイテムと数が設定されていません。運営に報告お願いします。");
					}
				}
				else
				{
					player.sendMessage(prefix+"§c現在スロットを回しています。");
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("slotsetting"))
		{
			boolean success = false;
			if(args.length>=3)
			{
				if(args[0].equalsIgnoreCase("input"))
				{
					if(args[1].equalsIgnoreCase("type"))
					{
						if(isInt(args[2]))
						{
							int i = Integer.parseInt(args[2]);
							FileConfiguration config = getConfig();
							config.set("item.input.typeID", i);
							loadConfig();
							sender.sendMessage(prefix+"§eスロットに必要なアイテムIDを§6"+i+"§eに設定しました。");
							success = true;
						}
					}
					else if(args[1].equalsIgnoreCase("amount"))
					{
						if(isInt(args[2]))
						{
							int i = Integer.parseInt(args[2]);
							FileConfiguration config = getConfig();
							config.set("item.input.amount", i);
							loadConfig();
							sender.sendMessage(prefix+"§eスロットに必要なアイテムの数を§6"+i+"§e個に設定しました。");
							success = true;
						}
					}
				}
				else if(args[0].equalsIgnoreCase("output"))
				{
					if(args[1].equalsIgnoreCase("type"))
					{
						if(isInt(args[2]))
						{
							int i = Integer.parseInt(args[2]);
							FileConfiguration config = getConfig();
							config.set("item.output.typeID", i);
							loadConfig();
							sender.sendMessage(prefix+"§eスロットの景品アイテムのアイテムIDを§6"+i+"§eに設定しました。");
							success = true;
						}
					}
				}
			}
			if(!success)
			{
				sender.sendMessage(prefix+"§c/slotsetting input type <アイテムID>\n"
						+ prefix+ "§c/slotsetting input amount <アイテム数>\n"
						+ prefix+ "§c/slotsetting output type <アイテムID>");
			}
		}
		return false;
	}
}
