package nl.enjarai.shared_resources.common;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Iterator;

import static nl.enjarai.shared_resources.common.config.SharedResourcesConfig.CONFIG;

public class SharedResourcesPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        // Boot up Mixin Extras
        MixinExtrasBootstrap.init();

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

        // Carefully touch our config to get it to load and wake up the API
        SharedResourcesConfig.touch();

        // Load config directory override if enabled.
        Path configDir = GameResourceHelper.getPathFor(GameResources.CONFIG);

        if (configDir != null) {

            SharedResources.LOGGER.info("Config directory override enabled, changing fabric config directory to: {}. *proceed with caution*", configDir);

            try {
                Class<?> loaderClass;

                if (FabricLoader.getInstance().isModLoaded("quilt_loader")) {
                    loaderClass = Class.forName("org.quiltmc.loader.impl.QuiltLoaderImpl");
                }
                else if (FabricLoader.getInstance().isModLoaded("fabricloader")) {
                    loaderClass = Class.forName("net.fabricmc.loader.impl.FabricLoaderImpl");
                }
                else {
                    SharedResources.LOGGER.error("Could not find Fabric or Quilt. Abort setting config override");
                    return;
                }

                Field configDirField = loaderClass.getDeclaredField("configDir");
                configDirField.setAccessible(true);
                configDirField.set(loaderClass.getDeclaredField("INSTANCE").get(null), configDir);

            } catch (IllegalAccessException |
                     NoSuchFieldException |
                     ClassNotFoundException e) {
                SharedResources.LOGGER.error("Failed to set config directory override.", e);

            }
        }
    }

    public static void initApi() {
        // Prepare the API
        GameResourceHelper.setConfigSource(CONFIG);

        // Load all resource directories.
        FabricLoader.getInstance().getEntrypoints("shared-resources", SharedResourcesEntrypoint.class).forEach(
                entrypoint -> entrypoint.registerResources(GameResourceRegistry.REGISTRY));
        GameResourceRegistry.REGISTRY.finalise();
        CONFIG.initEnabledResources();
    }
}
