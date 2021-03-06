package com.github.dskprt.bungeechat.listeners;

import com.github.dskprt.bungeechat.Bungeechat;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(e.getPlayer().getServer().getName());
        out.writeUTF(e.getPlayer().getDisplayName());
        out.writeUTF(e.getMessage());

        e.getPlayer().getServer().sendPluginMessage(Bungeechat.INSTANCE, "dskprt:bungeechat", out.toByteArray());
    }
}
