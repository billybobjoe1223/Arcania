package me.billybobjoe1223.Arcania;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerListener;

public class ArcaniaPlayerListener extends PlayerListener {
	static String mainDirectory = "plugins/Arcania";
	static File Arcania = new File(mainDirectory + File.separator + "Arcania.dat");
	static Properties prop = new Properties();
	public static Arcania plugin;
	int mana = 0;
	int pmana = 0;
	public ArcaniaPlayerListener(Arcania instance) {
        plugin = instance;
        plugin.setNaggable(false);
	}
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		final Arcania arcania = new Arcania();
		plugin.log.info(event.getMessage());
		String message = event.getMessage();
		if(message.charAt(1) != 0){
			
		}
		try {
			FileInputStream in = new FileInputStream(Arcania);
			prop.load(in);
			in.close();
			if(prop.getProperty() != null) {
				mana = Integer.parseInt(prop.getProperty(message));
				pmana = me.billybobjoe1223.Arcania.Arcania.playerMana.get(player);
				if(pmana >= mana) {
					if(player.isOp() == false) {
						pmana = pmana - mana;	
						me.billybobjoe1223.Arcania.Arcania.playerMana.put(player, pmana);
					}
					player.sendMessage(ChatColor.BLUE + "Spell cast, you now have " + Integer.toString(pmana) + " " +arcania.manaName + "left.");
				} else {
					player.sendMessage(ChatColor.BLUE + "You only have " + Integer.toString(pmana) + ", you need " + arcania.manaName + ". Spell not cast.");
					event.setCancelled(true);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}	
	}
}

