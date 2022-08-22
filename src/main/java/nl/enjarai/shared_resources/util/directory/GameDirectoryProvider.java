package nl.enjarai.shared_resources.util.directory;

import nl.enjarai.shared_resources.api.DirectoryResource;

import javax.annotation.Nullable;
import java.nio.file.Path;

public interface GameDirectoryProvider {
    @Nullable
    Path getDirectory(DirectoryResource resource);
}
