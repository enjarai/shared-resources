package nl.enjarai.shared_resources.common;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.api.GameResourceRegistry;
import nl.enjarai.shared_resources.api.SharedResourcesEntrypoint;
import nl.enjarai.shared_resources.common.config.SharedResourcesConfig;
import nl.enjarai.shared_resources.common.registry.GameResources;
import nl.enjarai.shared_resources.common.util.SRConfigEntryPoint;

import java.lang.reflect.Field;
import java.nio.file.Path;

public class SharedResourcesPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        // Make sure one of our version-specific submods is loaded
        if (FabricLoader.getInstance().getAllMods().stream().noneMatch(container -> container
                        .getMetadata()
                        .getId().startsWith("shared-resources-mc"))) {
            throw new RuntimeException("Shared Resources didn't load correctly! You're probably using an unsupported version of Minecraft.");
        }

        GameResourceHelper.setConfigSource(SharedResourcesConfig.INSTANCE);

        // Load the version specific text builder
        FabricLoader.getInstance().getEntrypoints("shared-resources-config", SRConfigEntryPoint.class).forEach(it -> SharedResources.TEXT_BUILDER = it.getTextBuilder());

        // Load all resource directories.
        FabricLoader.getInstance().getEntrypoints("shared-resources", SharedResourcesEntrypoint.class).forEach(
                entrypoint -> entrypoint.registerResources(GameResourceRegistry.REGISTRY));

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
