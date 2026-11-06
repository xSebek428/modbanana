package pl.novabanana;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class NovaScreen extends Screen {
    public NovaScreen() { super(Text.literal("NovaBANANA")); }

    @Override
    protected void init() {
        int w = 220, h = 20, x = (width - w) / 2;
        int total = NovaBananaClient.EVENTS.size() * 26 + 40;
        int y = Math.max(40, (height - total) / 2 + 10);
        for (FakeEvent e : NovaBananaClient.EVENTS) {
            addDrawableChild(ButtonWidget.builder(Text.literal("§e" + e.name()), btn -> {
                NovaBananaClient.fire(client, e);
                close();
            }).dimensions(x, y, w, h).build());
            y += 26;
        }
        addDrawableChild(ButtonWidget.builder(Text.literal("Zamknij"), b -> close())
                .dimensions(x, y + 10, w, h).build());
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        super.render(ctx, mx, my, delta); // tlo rysuje juz Screen.render
        ctx.drawCenteredTextWithShadow(textRenderer,
                Text.literal("§e§lNovaBANANA §7| wybierz eventowke"), width / 2, 20, 0xFFFFFFFF);
    }
}
