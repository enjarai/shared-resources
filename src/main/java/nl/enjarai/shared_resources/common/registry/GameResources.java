package nl.enjarai.shared_resources.common.registry;

import net.fabricmc.loader.api.FabricLoader;
import nl.enjarai.shared_resources.api.*;
import nl.enjarai.shared_resources.common.SharedResources;

import static nl.enjarai.shared_resources.common.SharedResources.id;
import static nl.enjarai.shared_resources.versioned.Versioned.TEXT;

public class GameResources implements SharedResourcesEntrypoint {
    // Folders
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
    public static final ResourceDirectory DATAPACKS = new ResourceDirectoryBuilder("datapacks")
            .setDisplayName(TEXT.translatable("shared_resources.directory.datapacks"))
            .setDescription(
                    TEXT.translatable("shared_resources.directory.datapacks.description[0]"),
                    TEXT.translatable("shared_resources.directory.datapacks.description[1]"),
                    TEXT.translatable("shared_resources.directory.datapacks.description[2]")
            )
            .build();

    // Files
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

    // Compat
    public static final ResourceDirectory SHADERPACKS = new ResourceDirectoryBuilder("shaderpacks")
            .setDisplayName(TEXT.translatable("shared_resources.directory.shaderpacks"))
            .addMixinPackage("iris")
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
            .addMixinPackage("litematica")
            .build();
    public static final ResourceDirectory REPLAY_RECORDINGS = new ResourceDirectoryBuilder("replay_recordings")
            .setDisplayName(TEXT.translatable("shared_resources.directory.replay_recordings"))
            .overridesDefaultDirectory()
            .addMixinPackage("replaymod")
            .build();
    public static final ResourceDirectory SKINSHUFFLE_DATA = new ResourceDirectoryBuilder("config/skinshuffle")
            .setDisplayName(TEXT.translatable("shared_resources.directory.skinshuffle_data"))
            .requiresRestart()
            .overridesDefaultDirectory()
            .addMixinPackage("skinshuffle")
            .build();
    public static final ResourceFile XAEROS_WORLDMAP_CONFIG = new ResourceFileBuilder("config/xaeroworldmap.txt")
            .setDisplayName(TEXT.translatable("shared_resources.file.xaeros_worldmap_config"))
            .requiresRestart()
            .addMixinPackage("xaeroworldmap")
            .build();
    public static final ResourceDirectory XAEROS_WORLDMAP_DATA = new ResourceDirectoryBuilder("XaeroWorldMap")
            .setDisplayName(TEXT.translatable("shared_resources.directory.xaeros_worldmap_data"))
            .requiresRestart()
            .overridesDefaultDirectory()
            .addMixinPackage("xaeroworldmap")
            .build();
    public static final ResourceDirectory EMOTES = new ResourceDirectoryBuilder("emotes")
            .setDisplayName(TEXT.translatable("shared_resources.directory.emotes"))
            .overridesDefaultDirectory()
            .addMixinPackage("emotecraft")
            .build();

    @Override
    public void registerResources(GameResourceRegistry registry) {
        // Directories
        registry.register(id("resourcepacks"), RESOURCEPACKS);
        registry.register(id("saves"), SAVES);
        registry.register(id("config"), CONFIG);
        registry.register(id("screenshots"), SCREENSHOTS);
        registry.register(id("datapacks"), DATAPACKS);

        // Files
        registry.register(id("options"), OPTIONS);
        registry.register(id("servers"), SERVERS);
        registry.register(id("hotbars"), HOTBARS);

        // Only compat if a mod is available.
        if (checkLoaded("iris")) registry.register(id("shaderpacks"), SHADERPACKS);
        if (checkLoaded("litematica")) registry.register(id("schematics"), SCHEMATICS);
        if (checkLoaded("replaymod")) registry.register(id("replay_recordings"), REPLAY_RECORDINGS);
        if (checkLoaded("skinshuffle")) registry.register(id("skinshuffle_data"), SKINSHUFFLE_DATA);
        if (checkLoaded("xaeroworldmap")) {
            registry.register(id("xaeros_worldmap_config"), XAEROS_WORLDMAP_CONFIG);
            registry.register(id("xaeros_worldmap_data"), XAEROS_WORLDMAP_DATA);
        }
        if (checkLoaded("emotecraft")) registry.register(id("emotes"), EMOTES);
    }

    private static boolean checkLoaded(String modid) {
        boolean loaded = FabricLoader.getInstance().isModLoaded(modid);
        if (loaded) {
            SharedResources.LOGGER.info("Mod {} is loaded, enabling compat.", modid);
        }
        return loaded;
    }
}
