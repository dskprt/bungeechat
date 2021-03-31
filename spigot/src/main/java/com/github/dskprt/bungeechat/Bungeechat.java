package com.github.dskprt.bungeechat;

import com.github.dskprt.bungeechat.listeners.ChatListener;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public final class Bungeechat extends JavaPlugin implements PluginMessageListener {

    public static Bungeechat INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "dumb:bungeechat");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "dumb:bungeechat", this);
        this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if(!channel.equals("dumb:bungeechat")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String server = in.readUTF();
        String username = in.readUTF();
        String msg = in.readUTF();

        if(server.equals(this.getServer().getName())) return;

        this.getLogger().info("received message: " + server + "; " + username + "; " + msg);
        this.getServer().broadcastMessage(String.format("[%s] <%s> %s", server, username, msg));
    }
}
