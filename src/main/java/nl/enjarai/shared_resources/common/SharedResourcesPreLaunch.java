package nl.enjarai.shared_resources.common;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.Identifier;
import nl.enjarai.shared_resources.api.GameResource;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.api.GameResourceRegistry;
import nl.enjarai.shared_resources.api.SharedResourcesEntrypoint;
import nl.enjarai.shared_resources.common.config.SharedResourcesConfig;
import nl.enjarai.shared_resources.common.registry.GameResources;
import nl.enjarai.shared_resources.versioned.Versioned;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Iterator;

import static nl.enjarai.shared_resources.common.config.SharedResourcesConfig.CONFIG;

public class SharedResourcesPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        // Make sure one of our version-specific submods is loaded
        if (FabricLoader.getInstance().getAllMods().stream().noneMatch(container -> container
                        .getMetadata()
                        .getId().startsWith("shared-resources-mc"))) {
            throw new RuntimeException("Shared Resources didn't load correctly! You're probably using an unsupported version of Minecraft.");
        }

        // Check if cloth config is loaded
        if (FabricLoader.getInstance().getAllMods().stream().noneMatch(container -> container
                .getMetadata()
                .getId().startsWith("cloth-config"))) {
            throw new RuntimeException("Shared Resources didn't load correctly! Cloth Config is required for Shared Resources to work.");
        }

        // Load the versioned objects
        Versioned.load();

        // Prepare the API
        GameResourceHelper.setConfigSource(CONFIG);

        // Load all resource directories.
        FabricLoader.getInstance().getEntrypoints("shared-resources", SharedResourcesEntrypoint.class).forEach(
                entrypoint -> entrypoint.registerResources(GameResourceRegistry.REGISTRY));
        GameResourceRegistry.REGISTRY.finalise();
        CONFIG.initEnabledResources();

        // Load config directory override if enabled.
        Path configDir = GameResourceHelper.getPathFor(GameResources.CONFIG);

        if (configDir != null) {

            SharedResources.LOGGER.info("Config directory override enabled, changing fabric config directory to: {}. *proceed with caution*", configDir);

            try {
                Field field = FabricLoaderImpl.class.getDeclaredField("configDir");
                field.setAccessible(true);
                field.set(FabricLoader.getInstance(), configDir);

            } catch (IllegalAccessException | NoSuchFieldException e) {
                SharedResources.LOGGER.error("Failed to set config directory override.", e);

            }
        }
    }
}
