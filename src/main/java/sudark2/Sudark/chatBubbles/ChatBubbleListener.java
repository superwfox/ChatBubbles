package sudark2.Sudark.chatBubbles;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;

import java.util.HashMap;

import static sudark2.Sudark.chatBubbles.ChatBubbles.get;

public class ChatBubbleListener implements Listener {
    static HashMap<Player, TextDisplay> bubbles = new HashMap<>();

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        String msg = event.getMessage();
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location loc = player.getLocation();

        TextDisplay display = (TextDisplay) world.spawnEntity(loc, EntityType.TEXT_DISPLAY);
        display.setBackgroundColor(Color.WHITE);
        display.setText("§l§0[ " + msg + " ]");
        display.getTransformation().getScale().mul(new Vector3f(1.5F, 1.5F, 1.5F)); // 直接整体放大 1.5 倍
        display.setSeeThrough(true);
        display.setBillboard(Display.Billboard.HORIZONTAL);
        display.setTextOpacity((byte) 250);

        if (!player.getPassengers().isEmpty()) {
            display.addPassenger(bubbles.get(player));
        }
        putOn(display);
        player.addPassenger(display);
        bubbles.put(player, display);

        Bukkit.getScheduler().runTaskLater(get(), display::remove, 8 * 20);

    }

    public static void putOn(Entity entity) {

        if (entity instanceof TextDisplay text) {
            Vector3f old = text.getTransformation().getTranslation();
            text.setTransformation(
                    new Transformation(
                            old.add(new Vector3f(0f, 0.25f, 0f)),  // 新 translation
                            text.getTransformation().getLeftRotation(),
                            text.getTransformation().getScale(),
                            text.getTransformation().getRightRotation()
                    )
            );
        }

        if (entity.getPassengers().isEmpty()) return;
        Entity en = entity.getPassengers().stream().findFirst().orElse(null);
        putOn(en);
    }
}
