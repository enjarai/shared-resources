package nl.enjarai.shared_resources;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.api.GameResourceRegistry;
import nl.enjarai.shared_resources.api.SharedResourcesEntrypoint;
import nl.enjarai.shared_resources.registry.GameResources;

public class SharedResourcesPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        // Load all resource directories.
        FabricLoader.getInstance().getEntrypoints("shared-resources", SharedResourcesEntrypoint.class).forEach(
                entrypoint -> entrypoint.registerResources(GameResourceRegistry.REGISTRY));

        // Load config directory override if enabled.
        var configDir = GameResourceHelper.getPathFor(GameResources.CONFIG);

        if (configDir != null) {

            SharedResources.LOGGER.info("Config directory override enabled, changing fabric config directory to: {}. *proceed with caution*", configDir);

            try {
                var field = FabricLoaderImpl.class.getDeclaredField("configDir");
                field.setAccessible(true);
                field.set(FabricLoader.getInstance(), configDir);

            } catch (IllegalAccessException | NoSuchFieldException e) {
                SharedResources.LOGGER.error("Failed to set config directory override.", e);

            }
        }
    }
}
