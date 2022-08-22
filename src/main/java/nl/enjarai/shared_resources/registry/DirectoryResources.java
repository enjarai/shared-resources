package nl.enjarai.shared_resources.registry;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import nl.enjarai.shared_resources.SharedResources;
import nl.enjarai.shared_resources.api.DirectoryResource;
import nl.enjarai.shared_resources.api.DirectoryResourceBuilder;
import nl.enjarai.shared_resources.api.DirectoryResourceRegistry;
import nl.enjarai.shared_resources.api.SharedResourcesEntrypoint;

public class DirectoryResources implements SharedResourcesEntrypoint {
    public static final DirectoryResource RESOURCEPACKS = new DirectoryResourceBuilder("resourcepacks")
            .setDisplayName(Text.translatable("shared_resources.directory.resourcepacks"))
            .build();
    public static final DirectoryResource SAVES = new DirectoryResourceBuilder("saves")
            .setDisplayName(Text.translatable("shared_resources.directory.saves"))
            .build();
    public static final DirectoryResource CONFIG = new DirectoryResourceBuilder("config")
            .setDisplayName(Text.translatable("shared_resources.directory.config"))
            .requiresRestart()
            .overridesDefaultDirectory()
            .defaultEnabled(false)
            .isExperimental()
            .build();
    public static final DirectoryResource SHADERPACKS = new DirectoryResourceBuilder("shaderpacks")
            .setDisplayName(Text.translatable("shared_resources.directory.shaderpacks"))
            .build();

    @Override
    public void registerResources(DirectoryResourceRegistry registry) {
        registry.register(SharedResources.id("resourcepacks"), RESOURCEPACKS);
        registry.register(SharedResources.id("saves"), SAVES); // NOT WORKING YET, DONT USE
        registry.register(SharedResources.id("config"), CONFIG);
        // TODO make saves work.
        // TODO vanilla options.txt and other files maybe.

        // Only load shaderpack compat if iris is available.
        if (FabricLoader.getInstance().isModLoaded("iris")) {
            SharedResources.LOGGER.info("Iris available, loading shaderpack compat.");

            registry.register(SharedResources.id("shaderpacks"), SHADERPACKS);
        }
    }
}
