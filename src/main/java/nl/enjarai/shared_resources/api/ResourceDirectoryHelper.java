package nl.enjarai.shared_resources.api;

import nl.enjarai.shared_resources.config.ModConfig;

import javax.annotation.Nullable;
import java.nio.file.Path;

public class ResourceDirectoryHelper {
    @Nullable
    public static Path getPathFor(ResourceDirectory resource) {
        var path = ModConfig.INSTANCE.getGlobalDirectory().getDirectory(resource);

        // Ensure the path exists.
        if (path != null && !path.toFile().isDirectory()) {
            path.toFile().mkdirs();
        }

        return path;
    }
}
