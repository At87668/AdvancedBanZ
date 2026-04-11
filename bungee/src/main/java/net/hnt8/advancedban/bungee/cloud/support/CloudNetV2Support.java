
package net.hnt8.advancedban.bungee.cloud.support;

import de.dytanic.cloudnet.api.player.PlayerExecutorBridge;
import de.dytanic.cloudnet.bridge.CloudServer;
import net.hnt8.advancedban.bungee.cloud.CloudSupport;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

import java.util.UUID;

public class CloudNetV2Support implements CloudSupport {

    @Override
    public void kick(UUID uniqueID, String reason) {
        String legacy = net.hnt8.advancedban.bungee.BungeeMethods.miniMessageToLegacy(reason);
        PlayerExecutorBridge.INSTANCE.kickPlayer(CloudServer.getInstance().getCloudPlayers().get(uniqueID), legacy);
    }
}
