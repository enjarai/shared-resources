package nl.enjarai.shared_resources.api;

import nl.enjarai.shared_resources.config.ModConfig;

import javax.annotation.Nullable;
import java.nio.file.Path;

public class DirectoryResourceHelper {
    /**
     * Returns the current custom path for this resource directory, or null if it is either disabled or not set.
     * Use this instead of interacting with {@link ModConfig} directly.
     * @param resource The resource directory to get the path for.
     */
    @Nullable
    public static Path getPathFor(DirectoryResource resource) {
        if (!ModConfig.INSTANCE.isEnabled(resource)) {
            return null;
        }

        var path = ModConfig.INSTANCE.getGlobalDirectory().getDirectory(resource);

        // Ensure the path exists.
        if (path != null && !path.toFile().isDirectory()) {
            path.toFile().mkdirs();
        }

        return path;
    }
}
