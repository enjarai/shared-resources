package nl.enjarai.shared_resources.api;

import nl.enjarai.shared_resources.util.GameResourceConfig;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class GameResourceHelper {
    private static GameResourceConfig config;

    /**
     * Returns the current custom path for this resource, or null if it is either disabled or not set.
     * @param resource The resource to get the path for.
     */
    @Nullable
    public static Path getPathFor(GameResource resource) {
        if (config == null || !config.isEnabled(resource)) return null;

        Path path = config.getDirectory(resource);

        // Ensure the path exists.
        if (path != null) {
            resource.ensureExists(path);
        }

        return path;
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
