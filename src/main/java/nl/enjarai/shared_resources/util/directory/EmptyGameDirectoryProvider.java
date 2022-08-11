package nl.enjarai.shared_resources.util.directory;

import nl.enjarai.shared_resources.api.ResourceDirectory;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class EmptyGameDirectoryProvider implements GameDirectoryProvider {
    @Nullable
    @Override
    public Path getDirectory(ResourceDirectory resource) {
        return null;
    }
}
