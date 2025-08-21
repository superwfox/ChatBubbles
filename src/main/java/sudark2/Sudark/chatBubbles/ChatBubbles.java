package sudark2.Sudark.chatBubbles;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatBubbles extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ChatBubbleListener(), this);
    }

    public static Plugin get(){
        return JavaPlugin.getPlugin(ChatBubbles.class);
    }
}
