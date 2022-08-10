package nl.enjarai.shared_resources.util.directory;

import javax.annotation.Nullable;
import java.nio.file.Path;

public interface GameDirectoryProvider {
    @Nullable
    Path getResourcePackDirectory();
}
