package nl.enjarai.shared_resources.registry;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import nl.enjarai.shared_resources.SharedResources;
import nl.enjarai.shared_resources.api.ResourceDirectory;
import nl.enjarai.shared_resources.api.ResourceDirectoryBuilder;
import nl.enjarai.shared_resources.api.ResourceDirectoryRegistry;
import nl.enjarai.shared_resources.api.SharedResourcesEntrypoint;

public class ResourceDirectories implements SharedResourcesEntrypoint {
    public static final ResourceDirectory RESOURCEPACKS = new ResourceDirectoryBuilder()
            .setDisplayName(Text.translatable("shared_resources.directory.resourcepacks"))
            .setDefaultDirectory("resourcepacks")
            .build();
    public static final ResourceDirectory SHADERPACKS = new ResourceDirectoryBuilder()
            .setDisplayName(Text.translatable("shared_resources.directory.shaderpacks"))
            .setDefaultDirectory("shaderpacks")
            .build();

    @Override
    public void onInitialize(ResourceDirectoryRegistry registry) {
        registry.register(SharedResources.id("resourcepacks"), RESOURCEPACKS);

        // Only load shaderpack compat if iris is available.
        if (FabricLoader.getInstance().isModLoaded("iris")) {
            SharedResources.LOGGER.info("Iris available, loading shaderpack compat.");

            registry.register(SharedResources.id("shaderpacks"), SHADERPACKS);
        }
    }
}
