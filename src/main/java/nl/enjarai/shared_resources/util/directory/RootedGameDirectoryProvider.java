package nl.enjarai.shared_resources.util.directory;

import nl.enjarai.shared_resources.api.ResourceDirectory;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class RootedGameDirectoryProvider implements GameDirectoryProvider {
    protected Path root;

    public RootedGameDirectoryProvider(Path root) {
        this.root = root;
    }

    public Path getRoot() {
        return root;
    }

    @Nullable
    @Override
    public Path getDirectory(ResourceDirectory resource) {
        return getRoot().resolve(resource.getDefaultSubdirectory());
    }
}
