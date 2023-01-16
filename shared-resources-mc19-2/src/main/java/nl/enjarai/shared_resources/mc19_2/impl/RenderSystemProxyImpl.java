package nl.enjarai.shared_resources.mc19_2.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Identifier;
import nl.enjarai.shared_resources.versioned.RenderSystemProxy;

public class RenderSystemProxyImpl implements RenderSystemProxy {
    @Override
    public void setShaderTexture(Identifier texture) {
        RenderSystem.setShaderTexture(0, texture);
    }

    @Override
    public void setShaderColor(float r, float g, float b, float a) {
        RenderSystem.setShaderColor(r, g, b, a);
    }
}
