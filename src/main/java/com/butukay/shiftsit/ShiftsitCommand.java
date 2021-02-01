package com.butukay.shiftsit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ShiftsitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final String playerName = sender.getName();

        if (!Shiftsit.playerDetails.containsKey(playerName)) {
            Shiftsit.playerDetails.put(playerName, "false");
        }

        if (Shiftsit.playerDetails.get(playerName).equals("true")) {
            sender.sendMessage(Shiftsit.getPlugin(Shiftsit.class).getConfig().getString("chat-msgs.now-disabled", "Shiftsit now disabled"));

            Shiftsit.playerDetails.put(playerName, "false");
        } else {
            sender.sendMessage(Shiftsit.getPlugin(Shiftsit.class).getConfig().getString("chat-msgs.now-enabled", "Shiftsit now enabled"));

            Shiftsit.playerDetails.put(playerName, "true");
        }

        return true;
    }
}
