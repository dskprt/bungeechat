package com.github.dskprt.bungeechat;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.Map;

public final class Bungeechat extends Plugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getProxy().registerChannel("dumb:bungeechat");
        this.getProxy().getPluginManager().registerListener(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void sendMessage(String server, String username, String message, ServerInfo s) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(server);
        out.writeUTF(username);
        out.writeUTF(message);

        s.sendData("dumb:bungeechat", out.toByteArray());
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        if (!e.getTag().equals("dumb:bungeechat")) {
            return;
        }

        if (!(e.getSender() instanceof Server)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
        String server = in.readUTF();
        String username = in.readUTF();
        String message = in.readUTF();

        this.getLogger().info("received message: " + server + "; " + username + "; " + message);

        for(Map.Entry<String, ServerInfo> s : this.getProxy().getServers().entrySet()) {
            sendMessage(server, username, message, s.getValue());
        }
    }
}
