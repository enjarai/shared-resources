package nl.enjarai.shared_resources.mc19_2.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import nl.enjarai.shared_resources.versioned.Versioned;

public class LongButtonWidget extends ButtonWidget {
    public LongButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        Versioned.RENDER_SYSTEM.setShaderTexture(WIDGETS_TEXTURE);
        Versioned.RENDER_SYSTEM.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        int yImage = getYImage(isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        drawTexture(
                matrices,
                x, y,
                0, 46 + yImage * 20,
                10, height
        );
        int fillableWidth = width - 20;
        int repetitions = fillableWidth / 80;
        for (int i = 0; i < repetitions; i++) {
            drawTexture(
                    matrices,
                    x + 10 + fillableWidth / repetitions * i, y,
                    10 + 80 / repetitions * i, 46 + yImage * 20,
                    fillableWidth / repetitions, height
            );
        }
        drawTexture(
                matrices,
                x + width - 20, y,
                200 - 20, 46 + yImage * 20,
                20, height
        );

        renderBackground(matrices, minecraftClient, mouseX, mouseY);
        int j = active ? 16777215 : 10526880;
        String message = getMessage().getString();

        int messageSpace = (int) (width * 0.9);
        int messageWidth = textRenderer.getWidth(message);
        String trimmedMessage = textRenderer.trimToWidth(message, messageSpace, true);
        if (messageWidth > messageSpace) {
            trimmedMessage = "..." + trimmedMessage;
        }

        drawCenteredText(
                matrices, textRenderer,
                trimmedMessage,
                x + width / 2, y + (height - 8) / 2,
                j | MathHelper.ceil(alpha * 255.0F) << 24
        );
    }
}
