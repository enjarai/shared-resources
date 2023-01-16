package nl.enjarai.shared_resources.versioned;

import net.fabricmc.loader.api.FabricLoader;

public class Versioned {

    public static TextBuilder TEXT;
    public static RenderSystemProxy RENDER_SYSTEM;
    public static ScreenElementsBuilder SCREEN_ELEMENTS;

    public static void load() {
        SRVersionedEntryPoint entryPoint = FabricLoader.getInstance()
                .getEntrypoints("shared-resources-versioned", SRVersionedEntryPoint.class).get(0);
        if (entryPoint == null) {
            throw new RuntimeException("Could not find versioned entrypoint, are you using an unsupported version of Minecraft?");
        }

        TEXT = entryPoint.getTextBuilder();
        RENDER_SYSTEM = entryPoint.getRenderSystemProxy();
        SCREEN_ELEMENTS = entryPoint.getScreenElementsBuilder();
    }
}
