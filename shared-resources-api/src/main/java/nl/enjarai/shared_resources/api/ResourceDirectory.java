package nl.enjarai.shared_resources.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Represents a directory in <code>.minecraft</code> where a certain resource is stored.
 * Should be registered in {@link GameResourceRegistry} and probably stored in a constant for later use.
 */
@SuppressWarnings("unused")
public interface ResourceDirectory extends GameResource {

    /**
     * Whether this directory completely overrides the default directory for this resource.
     */
    boolean isOverridesDefaultDirectory();

    @Override
    default void ensureExists(Path resourceLocation) {
        if (!Files.exists(resourceLocation)) {
            try {
                Files.createDirectories(resourceLocation);
            } catch (IOException ignored) {
            }
        }
    }
}
