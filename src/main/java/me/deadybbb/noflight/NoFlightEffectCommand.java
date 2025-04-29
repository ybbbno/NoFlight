package me.deadybbb.noflight;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoFlightEffectCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(Component.text("You do not have permission to use this command!", NamedTextColor.RED));
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(Component.text("Usage: /noflight <player> <seconds>", NamedTextColor.RED));
            return false;
        }

        Player target = sender.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Component.text("Player not found", NamedTextColor.RED));
            return false;
        }

        int seconds;
        try{
            seconds = Integer.parseInt(args[1]);
            if (seconds < 0) {
                sender.sendMessage(Component.text("Seconds cannot be negative", NamedTextColor.RED));
                return false;
            }
        } catch (NumberFormatException e){
            sender.sendMessage(Component.text("That is not a number", NamedTextColor.RED));
            return false;
        }

        NoFlightEffect.applyEffect(target, seconds);
        sender.sendMessage(Component.text("NoFlight effect applied to " + target.getName(), NamedTextColor.GREEN));
        return true;
    }
}
