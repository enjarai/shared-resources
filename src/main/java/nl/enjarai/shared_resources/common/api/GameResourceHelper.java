package nl.enjarai.shared_resources.common.api;

import nl.enjarai.shared_resources.common.config.SharedResourcesConfig;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class GameResourceHelper {
    /**
     * Returns the current custom path for this resource, or null if it is either disabled or not set.
     * Always use this instead of interacting with {@link SharedResourcesConfig} directly.
     * @param resource The resource to get the path for.
     */
    @Nullable
    public static Path getPathFor(GameResource resource) {
        if (!SharedResourcesConfig.INSTANCE.isEnabled(resource)) {
            return null;
        }

        var path = SharedResourcesConfig.INSTANCE.getGlobalDirectory().getDirectory(resource);

        // Ensure the path exists.
        if (path != null) {
            resource.ensureExists(path);
        }

        return path;
    }
}
