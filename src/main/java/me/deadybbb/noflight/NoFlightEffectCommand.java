package me.deadybbb.noflight;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoFlightEffectCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(NamedTextColor.RED + "Usage: /noflight <player>");
            return false;
        }

        Player target = sender.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(NamedTextColor.RED + "Player not found");
            return false;
        }

        NoFlightEffect.applyEffect(target);
        sender.sendMessage(NamedTextColor.GREEN + "Custom effect applied to " + target.getName());
        return true;
    }
}
