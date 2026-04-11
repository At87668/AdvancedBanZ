package net.hnt8.advancedban.bungee.listener;

import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;
import net.hnt8.advancedban.MethodInterface;
import net.hnt8.advancedban.Universal;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 *
 * @author Beelzebu
 */
public class PubSubMessageListener implements Listener {
    
    private static final MethodInterface mi = Universal.get().getMethods();

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onMessageReceive(PubSubMessageEvent e) {
        if (e.getChannel().equals("advancedban:main")) {
            String raw = e.getMessage();
            String[] parts = raw.split(" ");
            String payload = raw.substring((parts[0] + parts[1]).length() + 2);

            if (raw.startsWith("kick ")) {
                if (ProxyServer.getInstance().getPlayer(parts[1]) != null) {
                    ProxyServer.getInstance().getPlayer(parts[1]).disconnect(net.hnt8.advancedban.bungee.BungeeMethods.miniMessageToBaseComponents(payload));
                }
            } else if (raw.startsWith("notification ")) {
                for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                    if (mi.hasPerms(pp, parts[1])) {
                        mi.sendMessage(pp, payload);
                    }
                }
            } else if (raw.startsWith("message ")) {
                if (ProxyServer.getInstance().getPlayer(parts[1]) != null) {
                    ProxyServer.getInstance().getPlayer(parts[1]).sendMessage(net.hnt8.advancedban.bungee.BungeeMethods.miniMessageToBaseComponents(payload));
                }
                if (parts[1].equalsIgnoreCase("CONSOLE")) {
                    ProxyServer.getInstance().getConsole().sendMessage(net.hnt8.advancedban.bungee.BungeeMethods.miniMessageToLegacy(payload));
                }
            }
        } else if (e.getChannel().equals("advancedban:connection")) {
            String[] msg = e.getMessage().split(",");
            Universal.get().getIps().remove(msg[0].toLowerCase());
            Universal.get().getIps().put(msg[0].toLowerCase(), msg[1]);
        }
    }
}