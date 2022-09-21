package nl.enjarai.shared_resources.versioned;

import net.minecraft.util.Identifier;

public interface RenderSystemProxy {
    void setShaderTexture(Identifier texture);

    void setShaderColor(float r, float g, float b, float a);
}
