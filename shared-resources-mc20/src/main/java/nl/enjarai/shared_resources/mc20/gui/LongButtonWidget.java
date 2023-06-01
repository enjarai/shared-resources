package nl.enjarai.shared_resources.mc20.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import nl.enjarai.shared_resources.versioned.Versioned;

public class LongButtonWidget extends ButtonWidget {
    public LongButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        Versioned.RENDER_SYSTEM.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        int textureY = getTextureY();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        context.drawTexture(
                WIDGETS_TEXTURE,
                getX(), getY(),
                0, textureY,
                10, height
        );
        int fillableWidth = width - 20;
        int repetitions = fillableWidth / 80;
        for (int i = 0; i < repetitions; i++) {
            context.drawTexture(
                    WIDGETS_TEXTURE,
                    getX() + 10 + fillableWidth / repetitions * i, getY(),
                    10 + 80 / repetitions * i, textureY,
                    fillableWidth / repetitions, height
            );
        }
        context.drawTexture(
                WIDGETS_TEXTURE,
                getX() + width - 20, getY(),
                200 - 20, textureY,
                20, height
        );

        int j = active ? 16777215 : 10526880;
        String message = getMessage().getString();

        int messageSpace = (int) (width * 0.9);
        int messageWidth = textRenderer.getWidth(message);
        String trimmedMessage = textRenderer.trimToWidth(message, messageSpace, true);
        if (messageWidth > messageSpace) {
            trimmedMessage = "..." + trimmedMessage;
        }

        context.drawCenteredTextWithShadow(
                textRenderer,
                trimmedMessage,
                getX() + width / 2, getY() + (height - 8) / 2,
                j | MathHelper.ceil(alpha * 255.0F) << 24
        );
    }
}
