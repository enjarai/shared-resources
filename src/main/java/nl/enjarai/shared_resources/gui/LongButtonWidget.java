package nl.enjarai.shared_resources.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import nl.enjarai.shared_resources.SharedResources;

import java.util.function.BiFunction;

public class LongButtonWidget extends ButtonWidget {
    private static final BiFunction<Boolean, Boolean, Identifier> TEXTURES = (active, selected) -> {
        if (active) {
            return selected ? SharedResources.vanillaId("widget/button_highlighted") : SharedResources.vanillaId("widget/button");
        } else {
            return SharedResources.vanillaId("widget/button_disabled");
        }
    };

    public LongButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    /*? if >=1.20.4 {*/
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
    /*?} else {*//*
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
    *//*?}*/
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        int textureY = 0; // getTextureY();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        Identifier texture = TEXTURES.apply(this.active, this.isSelected());

        /*? if >=1.20.2 {*/
        context.drawGuiTexture(
                texture,
                getX(), getY(),
                width, height
        );
        /*?} else {*//*
        context.drawNineSlicedTexture(
                WIDGETS_TEXTURE, this.getX(), this.getY(),
                this.getWidth(), this.getHeight(),
                20, 4,
                200, 20,
                0, this.getTextureY()
        );
        *//*?}*/

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

    /*? if <=1.20.1 {*//*
    private int getTextureY() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isSelected()) {
            i = 2;
        }

        return 46 + i * 20;
    }
    *//*?}*/
}
