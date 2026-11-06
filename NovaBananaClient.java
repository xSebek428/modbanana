package pl.novabanana;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class NovaBananaClient implements ClientModInitializer {
    public static List<FakeEvent> EVENTS;

    private static KeyBinding menuKey;
    private static final Queue<String> chatQueue = new ArrayDeque<>();
    private static int delay = 0;

    @Override
    public void onInitializeClient() {
        EVENTS = EventConfig.load();

        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.novabanana.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X,
                KeyBinding.Category.create(Identifier.of("novabanana", "main"))));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (menuKey.wasPressed()) {
                if (mc.currentScreen == null && mc.player != null) mc.setScreen(new NovaScreen());
            }
            // kolejka linii czatu: jedna linia co 2 s, tylko lokalnie
            if (!chatQueue.isEmpty() && --delay <= 0) {
                mc.inGameHud.getChatHud().addMessage(Text.literal(chatQueue.poll()));
                mc.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                delay = 40;
            }
        });
    }

    /** Odpala fejkowa eventowke. Widzisz ja tylko Ty - nic nie jest wysylane na serwer. */
    public static void fire(MinecraftClient mc, FakeEvent e) {
        if (mc.player == null) return;
        mc.inGameHud.setTitleTicks(10, 60, 10);
        mc.inGameHud.setTitle(Text.literal(e.title()));
        mc.inGameHud.setSubtitle(Text.literal(e.subtitle()));
        mc.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        chatQueue.clear();
        chatQueue.addAll(e.lines());
        delay = 1;
    }
}
