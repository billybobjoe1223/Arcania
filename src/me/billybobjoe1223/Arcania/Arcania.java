package me.billybobjoe1223.Arcania;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Arcania extends JavaPlugin {
	int propsize;
	Object[] propstring;
	String pstring;
	public Command command;
	public CommandSender commandsender;
	public String commandName;
	public String[] arguments;
	boolean enabled;
	public int maxMana;
	public String manaName;
	public static Map<Player, Integer> playerMana = new HashMap<Player, Integer>();
	public boolean isFileThere;
	static String mainDirectory = "plugins/Arcania";
	static File Arcania = new File(mainDirectory + File.separator + "Arcania.dat");
	static File Spells = new File(mainDirectory + File.separator + "Spells.dat");
	static Properties prop = new Properties();
	static Properties sprop = new Properties();
	private final ArcaniaPlayerListener playerListener = new ArcaniaPlayerListener(this);
	Logger log = Logger.getLogger("Minecraft");
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Event.Priority.Highest, this);
		new File(mainDirectory).mkdir();
		if(!Arcania.exists()) {
			try {
				Arcania.createNewFile();
				FileOutputStream out = new FileOutputStream(Arcania);
				prop.put("MaxMana","100");
				prop.put("ManaName", "Mana");
				prop.store(out,"MaxMana is the maximum amount of mana a player can have, ManaName is the display name of mana.");
				out.flush();
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			loadProcedure();
		}
		if(!Spells.exists()) {
			try {
				Spells.createNewFile();
				FileOutputStream out = new FileOutputStream(Spells);
				sprop.put("Herp", "20");
				sprop.store(out, "Add commands and their mana costs here.");
				out.flush();
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			spellLoadProcedure();
		}
		log.info("Arcania has been enabled");
		enabled = true;
	}
	
	public void onDisable() {
		log.info("Arcania has been disabled");
		enabled = false;
	}
	public void spellLoadProcedure() {
		try {
			FileInputStream in = new FileInputStream(Spells);
			sprop.load(in);
			propstring = sprop.stringPropertyNames().toArray();
			propsize = propstring.length;
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		command = cmd;
		commandsender = sender;
		commandName = commandLabel;
		arguments = args;
		if(commandLabel.equalsIgnoreCase("spells")) {
			sender.sendMessage(ChatColor.BLUE + "Spells: ");
			for(propsize = propstring.length; propsize > 0; propsize--) {
				sender.sendMessage(ChatColor.BLUE + "Spell: " + propstring[propsize-1].toString());
				sender.sendMessage(ChatColor.BLUE + "Cost: " + sprop.getProperty(propstring[propsize-1].toString()));
			}
			return true;
		}
		if(commandLabel.equalsIgnoreCase("arcania") && args.length == 1) {
			if(args[0].equalsIgnoreCase("reload") && sender.isOp()) {	
				spellLoadProcedure();
				loadProcedure();
				sender.sendMessage(ChatColor.BLUE + "Arcania has been reloaded");
				return true;
			}
		}
		return false;
	}
	public void loadProcedure() {
		try {
			FileInputStream in = new FileInputStream(Arcania);
			prop.load(in);
			maxMana = Integer.parseInt(prop.getProperty("MaxMana"));
			manaName = prop.getProperty("ManaName");
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
