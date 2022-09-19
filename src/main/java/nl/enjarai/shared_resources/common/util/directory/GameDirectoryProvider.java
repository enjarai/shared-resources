package nl.enjarai.shared_resources.common.util.directory;

import nl.enjarai.shared_resources.api.GameResource;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public interface GameDirectoryProvider {
    @Nullable
    Path getDirectory(GameResource resource);
}
