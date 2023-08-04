package nl.enjarai.shared_resources.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Represents a file resource in <code>.minecraft</code>.
 * Should be registered in {@link GameResourceRegistry} and probably stored in a constant for later use.
 */
public interface ResourceFile extends GameResource {

    @Override
    default void ensureExists(Path resourceLocation) {
        if (!Files.exists(resourceLocation)) {
            try {
                Files.createDirectories(resourceLocation.getParent());
            } catch (IOException ignored) {
            }
        }
    }
}
