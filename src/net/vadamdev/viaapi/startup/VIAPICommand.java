package net.vadamdev.viaapi.startup;

import net.vadamdev.viaapi.VIAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author VadamDev
 */
public class VIAPICommand extends Command {
    public VIAPICommand() {
        super("viapi");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("viapi.admin")) {
                player.sendMessage("§cError : You don't have the permission to perform this command !");
                return true;
            }

            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(" §7§l§m-------------------------------\n§r" +
                            "   §6§lVIAPI §f§l- §eCommands List\n" +
                            "   §7Authors: §f§nVadamDev§r §7and §f§nJava_Implements§r\n \n" +

                            "   §6• §7/viapi (listDepends or depends) : List all plugins that use VIAPI\n" +
                            "   §6• §7/viapi integrations : List all present integrations of VIAPI\n" +
                            " §7§l§m-------------------------------");
                }else if(args[0].equalsIgnoreCase("listDepends") || args[0].equalsIgnoreCase("depends")) {
                    StringBuilder stringBuilder = new StringBuilder();

                    VIAPI.get().getDependsMap().forEach((pluginName, apiVersion) -> stringBuilder.append("§a" + pluginName + " §7(" + apiVersion.name().replace("_", ".") + ")§f, "));

                    player.sendMessage("Depends (" +  VIAPI.get().getDependsMap().size() + "): " + stringBuilder);
                }else if(args[0].equalsIgnoreCase("integrations")) {
                    StringBuilder stringBuilder = new StringBuilder();

                    VIAPI.get().getIntegrationManager().getIntegrationsMap().forEach((integrationName, integration) -> stringBuilder.append("§a" + integrationName + " §7(" + integration.getVersion() + " - By " + integration.getAuthor() + ")§f, "));

                    player.sendMessage("Integrations (" +  VIAPI.get().getIntegrationManager().getIntegrationsMap().size() + "): " + stringBuilder);
                }else sendHelpMessage(sender);
            }else sendHelpMessage(sender);
        }
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(" §7§l§m-------------------------------\n§r" +
                "   §6§lVIAPI §f§l- §eInformations\n" +
                "   §7Authors: §f§nVadamDev§r §7and §f§nJava_Implements§r\n \n" +

                "   §6∎ §eVIAPI is a huge toolkit for plugin developers\n" +
                "   §6➥ §eVIAPI is required for (almost) all VadamDev plugins\n \n" +

                "   §6∎ §eSee /viapi help for a command list\n" +
                " §7§l§m-------------------------------");
    }
}
