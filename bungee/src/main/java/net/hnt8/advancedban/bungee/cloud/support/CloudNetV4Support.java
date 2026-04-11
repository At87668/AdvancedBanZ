package net.hnt8.advancedban.bungee.cloud.support;

import eu.cloudnetservice.driver.CloudNetDriver;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import net.hnt8.advancedban.bungee.cloud.CloudSupport;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;

import java.util.Objects;
import java.util.UUID;

public class CloudNetV4Support implements CloudSupport {

    @Override
    public void kick(UUID uniqueId, String reason) {
        MiniMessage mm = MiniMessage.miniMessage();
        Component comp;
        try {
            comp = mm.deserialize(reason.replace('§', '&'));
        } catch (Exception ex) {
            comp = net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().deserialize(reason.replace('&', '§'));
        }

        Objects.requireNonNull(CloudNetDriver.instance().serviceRegistry()
                .firstProvider(PlayerManager.class)
                .onlinePlayer(uniqueId), "player is null in CloudNetV4")
                .playerExecutor()
                .kick(comp);
    }

}
