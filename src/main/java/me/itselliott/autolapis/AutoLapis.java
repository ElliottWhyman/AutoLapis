package me.itselliott.autolapis;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import org.apache.commons.lang3.BooleanUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This plugin keeps lapis inside of the enchanting table
 * @author Elliott_
 */
public class AutoLapis extends JavaPlugin {

    private CommandsManager<CommandSender> commands;

    private static AutoLapis instance;

    /**
     * Boolean that states weather lapis will auto fill inside an enchantment table
     */
    private boolean lapisEnabled = true;

    @Override
    public void onEnable() {
        instance = this;
        setupCommands();
        new EnchantmentInventory(this);

        //Copy the default config file
        getConfig().options().copyDefaults(true);
        saveConfig();

        //get config value and set it as main boolean
        this.lapisEnabled = BooleanUtils.toBoolean(getConfig().getString("autoLapis"));
    }

    /**
     * @return instance of this class that extends JavaPlugin
     */
    public static AutoLapis getInstance() {
        return instance;
    }

    /**
     * @return if lapis is allowed to be kept inside enchanting inventory
     */
    public boolean isLapisEnabled() {
        return this.lapisEnabled;
    }

    /**
     * Set weather lapis is allowed to be kept inside enchanting inventory
     * @param lapisEnabled boolean that states if lapis is kept
     */
    public void setLapisEnabled(boolean lapisEnabled) {
        this.lapisEnabled = lapisEnabled;
    }

    /**
     * Method to register all of the plugins commands
     */
    private void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
            }
        };
        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
        cmdRegister.register(AutoLapisCommand.AutoLapisParentCommand.class);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }
        return true;
    }

}
