package nl.enjarai.shared_resources.util.directory;

import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class EmptyGameDirectoryProvider implements GameDirectoryProvider {
    @Nullable
    @Override
    public Path getResourcePackDirectory() {
        return null;
    }
}
