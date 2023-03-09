package nl.enjarai.shared_resources.common.registry;

import net.fabricmc.loader.api.FabricLoader;
import nl.enjarai.shared_resources.api.*;
import nl.enjarai.shared_resources.common.SharedResources;

import static nl.enjarai.shared_resources.versioned.Versioned.TEXT;

public class GameResources implements SharedResourcesEntrypoint {
    public static final ResourceDirectory RESOURCEPACKS = new ResourceDirectoryBuilder("resourcepacks")
            .setDisplayName(TEXT.translatable("shared_resources.directory.resourcepacks"))
            .build();
    public static final ResourceDirectory SAVES = new ResourceDirectoryBuilder("saves")
            .setDisplayName(TEXT.translatable("shared_resources.directory.saves"))
            .requiresRestart()
            .overridesDefaultDirectory()
            .build();
    public static final ResourceDirectory CONFIG = new ResourceDirectoryBuilder("config")
            .setDisplayName(TEXT.translatable("shared_resources.directory.config"))
            .setDescription(
                    TEXT.translatable("shared_resources.directory.config.description[0]"),
                    TEXT.translatable("shared_resources.directory.config.description[1]")
            )
            .requiresRestart()
            .overridesDefaultDirectory()
            .defaultEnabled(false)
            .isExperimental()
            .build();
    public static final ResourceDirectory SCREENSHOTS = new ResourceDirectoryBuilder("screenshots")
            .setDisplayName(TEXT.translatable("shared_resources.directory.screenshots"))
            .overridesDefaultDirectory()
            .build();


    public static final ResourceFile OPTIONS = new ResourceFileBuilder("options.txt")
            .setDisplayName(TEXT.translatable("shared_resources.file.options"))
            .setDescription(TEXT.translatable("shared_resources.file.options.description"))
            .requiresRestart()
            .build();
    public static final ResourceFile SERVERS = new ResourceFileBuilder("servers.dat")
            .setDisplayName(TEXT.translatable("shared_resources.file.servers"))
            .setDescription(TEXT.translatable("shared_resources.file.servers.description"))
            .build();
    public static final ResourceFile HOTBARS = new ResourceFileBuilder("hotbar.nbt")
            .setDisplayName(TEXT.translatable("shared_resources.file.hotbars"))
            .setDescription(TEXT.translatable("shared_resources.file.hotbars.description"))
            .build();

    public static final ResourceDirectory SHADERPACKS = new ResourceDirectoryBuilder("shaderpacks")
            .setDisplayName(TEXT.translatable("shared_resources.directory.shaderpacks"))
            .build();
    public static final ResourceDirectory SCHEMATICS = new ResourceDirectoryBuilder("schematics")
            .setDisplayName(TEXT.translatable("shared_resources.directory.schematics"))
            .setDescription(
                    TEXT.translatable("shared_resources.directory.schematics.description[0]"),
                    TEXT.translatable("shared_resources.directory.schematics.description[1]"),
                    TEXT.translatable("shared_resources.directory.schematics.description[2]")
            )
            .defaultEnabled(false)
            .overridesDefaultDirectory()
            .build();

    @Override
    public void registerResources(GameResourceRegistry registry) {
        registry.register(SharedResources.id("resourcepacks"), RESOURCEPACKS);
        registry.register(SharedResources.id("saves"), SAVES);
        registry.register(SharedResources.id("config"), CONFIG);
        registry.register(SharedResources.id("screenshots"), SCREENSHOTS);

        registry.register(SharedResources.id("options"), OPTIONS);
        registry.register(SharedResources.id("servers"), SERVERS);
        registry.register(SharedResources.id("hotbars"), HOTBARS);

        // Only compat if a mod is available.
        if (checkLoaded("iris")) registry.register(SharedResources.id("shaderpacks"), SHADERPACKS);
        if (checkLoaded("litematica")) registry.register(SharedResources.id("schematics"), SCHEMATICS);
    }

    private static boolean checkLoaded(String modid) {
        boolean loaded = FabricLoader.getInstance().isModLoaded(modid);
        if (loaded) {
            SharedResources.LOGGER.info("Mod {} is loaded, enabling compat.", modid);
        }
        return loaded;
    }
}
