package nl.enjarai.shared_resources.util;

import nl.enjarai.shared_resources.api.GameResource;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public interface GameResourceConfig {
    boolean isEnabled(GameResource resource);

    @Nullable
    Path getDirectory(GameResource resource);
}
