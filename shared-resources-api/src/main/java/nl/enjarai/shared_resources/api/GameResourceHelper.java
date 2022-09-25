package nl.enjarai.shared_resources.api;

import net.fabricmc.loader.api.FabricLoader;
import nl.enjarai.shared_resources.util.GameResourceConfig;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class GameResourceHelper {
    private static final Path GAME_ROOT = FabricLoader.getInstance().getGameDir();
    private static GameResourceConfig config;

    /**
     * Returns the current custom path for this resource, or null if it is either disabled or not set.
     * @param resource The resource to get the path for.
     */
    @Nullable
    public static Path getPathFor(@Nullable GameResource resource) {
        if (config == null || resource == null || !config.isEnabled(resource)) return null;

        Path path = config.getDirectory(resource);

        // Ensure the path exists.
        if (path != null) {
            resource.ensureExists(path);
        }

        return path;
    }

    /**
     * Returns the current custom path for this resource, or the registered default path if it is either disabled or not set.
     * Only use this for resources that completely overwrite their default location.
     */
    public static Path getPathOrDefaultFor(GameResource resource) {
        return getPathOrDefaultFor(resource, GAME_ROOT.resolve(resource.getDefaultPath()));
    }

    /**
     * Returns the current custom path for this resource, or the specified default path if it is either disabled or not set.
     * Only use this for resources that completely overwrite their default location.
     */
    public static Path getPathOrDefaultFor(GameResource resource, Path defaultPath) {
        Path path = getPathFor(resource);
        return path != null ? path : defaultPath;
    }

    /**
     * Sets the source of statuses and paths for resources.
     * Available to let the main mod interact with this API.
     * <b>Unless you really know what you're doing, don't use.</b>
     */
    public static void setConfigSource(GameResourceConfig config) {
        GameResourceHelper.config = config;
    }
}
