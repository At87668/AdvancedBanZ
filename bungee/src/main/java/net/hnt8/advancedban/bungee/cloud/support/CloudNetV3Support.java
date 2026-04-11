package net.hnt8.advancedban.bungee.cloud.support;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import net.hnt8.advancedban.bungee.cloud.CloudSupport;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

import java.util.UUID;

public class CloudNetV3Support implements CloudSupport {

    @Override
    public void kick(UUID uniqueID, String reason) {
        String legacy = net.hnt8.advancedban.bungee.BungeeMethods.miniMessageToLegacy(reason);
        CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class).getPlayerExecutor(uniqueID).kick(legacy);
    }
}
