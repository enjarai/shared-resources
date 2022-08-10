package nl.enjarai.shared_resources.util.directory;

import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class RootedGameDirectoryProvider implements GameDirectoryProvider {
    protected Path root;

    public RootedGameDirectoryProvider(Path root) {
        this.root = root;
    }

    public Path getDirectory() {
        return root;
    }

    @Nullable
    @Override
    public Path getResourcePackDirectory() {
        return getDirectory().resolve("resourcepacks");
    }
}
