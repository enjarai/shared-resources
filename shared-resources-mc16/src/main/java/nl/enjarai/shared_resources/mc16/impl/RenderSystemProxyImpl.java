package nl.enjarai.shared_resources.mc16.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import nl.enjarai.shared_resources.versioned.RenderSystemProxy;

public class RenderSystemProxyImpl implements RenderSystemProxy {
    @Override
    public void setShaderTexture(Identifier texture) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
    }

    @Override
    public void setShaderColor(float r, float g, float b, float a) {
        RenderSystem.color4f(r, g, b, a);
    }
}
