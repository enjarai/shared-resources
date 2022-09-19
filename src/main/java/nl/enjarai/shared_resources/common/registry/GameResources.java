package nl.enjarai.shared_resources.common.registry;

import net.fabricmc.loader.api.FabricLoader;
import nl.enjarai.shared_resources.api.*;
import nl.enjarai.shared_resources.common.SharedResources;

import static nl.enjarai.shared_resources.common.SharedResources.TEXT_BUILDER;

public class GameResources implements SharedResourcesEntrypoint {
    public static final ResourceDirectory RESOURCEPACKS = new ResourceDirectoryBuilder("resourcepacks")
            .setDisplayName(TEXT_BUILDER.translatable("shared_resources.directory.resourcepacks"))
            .build();
    public static final ResourceDirectory SAVES = new ResourceDirectoryBuilder("saves")
            .setDisplayName(TEXT_BUILDER.translatable("shared_resources.directory.saves"))
            .requiresRestart()
            .overridesDefaultDirectory()
            .build();
    public static final ResourceDirectory CONFIG = new ResourceDirectoryBuilder("config")
            .setDisplayName(TEXT_BUILDER.translatable("shared_resources.directory.config"))
            .setDescription(
                    TEXT_BUILDER.translatable("shared_resources.directory.config.description[0]"),
                    TEXT_BUILDER.translatable("shared_resources.directory.config.description[1]")
            )
            .requiresRestart()
            .overridesDefaultDirectory()
            .defaultEnabled(false)
            .isExperimental()
            .build();
    public static final ResourceDirectory SHADERPACKS = new ResourceDirectoryBuilder("shaderpacks")
            .setDisplayName(TEXT_BUILDER.translatable("shared_resources.directory.shaderpacks"))
            .build();

    public static final ResourceFile OPTIONS = new ResourceFileBuilder("options.txt")
            .setDisplayName(TEXT_BUILDER.translatable("shared_resources.file.options"))
            .setDescription(TEXT_BUILDER.translatable("shared_resources.file.options.description"))
            .requiresRestart()
            .build();

    @Override
    public void registerResources(GameResourceRegistry registry) {
        registry.register(SharedResources.id("resourcepacks"), RESOURCEPACKS);
        registry.register(SharedResources.id("saves"), SAVES);
        registry.register(SharedResources.id("config"), CONFIG);

        registry.register(SharedResources.id("options"), OPTIONS);

        // Only load shaderpack compat if iris is available.
        if (FabricLoader.getInstance().isModLoaded("iris")) {
            SharedResources.LOGGER.info("Iris available, loading shaderpack compat.");

            registry.register(SharedResources.id("shaderpacks"), SHADERPACKS);
        }
    }
}
