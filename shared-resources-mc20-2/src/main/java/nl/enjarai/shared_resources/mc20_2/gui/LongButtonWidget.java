package nl.enjarai.shared_resources.mc20_2.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import nl.enjarai.shared_resources.versioned.Versioned;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class LongButtonWidget extends ButtonWidget {
    private static final BiFunction<Boolean, Boolean, Identifier> TEXTURES = (active, selected) -> {
        if (active) {
            return selected ? new Identifier("widget/button_highlighted") : new Identifier("widget/button");
        } else {
            return new Identifier("widget/button_disabled");
        }
    };

    public LongButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        Versioned.RENDER_SYSTEM.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        int textureY = 0; // getTextureY();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        Identifier texture = TEXTURES.apply(this.active, this.isSelected());

        context.drawGuiTexture(
                texture,
                getX(), getY(),
                width, height
        );
//
//        context.drawTexture(
//                texture,
//                getX(), getY(),
//                0, textureY,
//                10, height
//        );
//        int fillableWidth = width - 20;
//        int repetitions = fillableWidth / 80;
//        for (int i = 0; i < repetitions; i++) {
//            context.drawTexture(
//                    texture,
//                    getX() + 10 + fillableWidth / repetitions * i, getY(),
//                    10 + 80 / repetitions * i, textureY,
//                    fillableWidth / repetitions, height
//            );
//        }
//        context.drawTexture(
//                texture,
//                getX() + width - 20, getY(),
//                200 - 20, textureY,
//                20, height
//        );

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
