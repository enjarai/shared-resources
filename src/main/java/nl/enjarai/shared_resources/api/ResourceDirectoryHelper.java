package nl.enjarai.shared_resources.api;

import nl.enjarai.shared_resources.config.ModConfig;

import javax.annotation.Nullable;
import java.nio.file.Path;

public class ResourceDirectoryHelper {
    @Nullable
    public static Path getDirectory(ResourceDirectory resource) {
        return ModConfig.INSTANCE.getGlobalDirectory().getDirectory(resource);
    }
}
