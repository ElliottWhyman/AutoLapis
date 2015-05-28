package me.itselliott.autolapis;

import com.sk89q.minecraft.util.commands.*;
import org.apache.commons.lang3.BooleanUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Commands used for AutoLapis
 */
public class AutoLapisCommand {

    public static class AutoLapisParentCommand {
        @Command(aliases = {"autolapis", "al"}, desc = "Main command that shows the status of the AutoLapis plugin")
        @NestedCommand(AutoLapisCommand.class)
        @CommandPermissions("autolapis.autolapis")
        public static void autoLapis(final CommandContext args, CommandSender sender) throws CommandException {
        }
    }

    @Command(aliases = "toggle", desc = "Toggles the state of the AutoLapis plugin")
    @CommandPermissions("autolapis.toggle")
    public static void toggle(final CommandContext args, CommandSender sender) throws CommandException {
        //Set the value of AutoLapis to the invert of what it currently is
        AutoLapis.getInstance().setLapisEnabled(!AutoLapis.getInstance().isLapisEnabled());
        //Update config file
        AutoLapis.getInstance().getConfig().set("autoLapis", !AutoLapis.getInstance().isLapisEnabled());
        //Save config file
        AutoLapis.getInstance().saveConfig();
        //Send a message to the sender of what the new value is
        sender.sendMessage(ChatColor.BLUE + "Lapis: " + (AutoLapis.getInstance().isLapisEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
    }

    @Command(aliases = "set", desc = "set the state of the AutoLapis plugin", min = 1, max = 1)
    @CommandPermissions("autolapis.set")
    public static void set(final CommandContext args, CommandSender sender) throws CommandException {
        //Set the value of AutoLapis to the senders argument
        AutoLapis.getInstance().setLapisEnabled(BooleanUtils.toBoolean(args.getString(0)));
        //Update config file
        AutoLapis.getInstance().getConfig().set("autoLapis", BooleanUtils.toBoolean(args.getString(0)));
        //Save config file
        AutoLapis.getInstance().saveConfig();
        //Send a message to the sender of what the new value is
        sender.sendMessage(ChatColor.BLUE + "Lapis: " + (AutoLapis.getInstance().isLapisEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));

    }

    @Command(aliases = {"view", "show"}, desc = "Shows the state of the AutoLapis Plugin", min = 0)
    @CommandPermissions("autolapis.set")
    public static void view(final CommandContext args, CommandSender sender) throws CommandException {
        //Send a message to the sender of what the value is
        sender.sendMessage(ChatColor.BLUE + "Lapis: " + (AutoLapis.getInstance().isLapisEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
    }

}
