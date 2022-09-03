package nl.enjarai.shared_resources.common.api;

import nl.enjarai.shared_resources.common.api.GameResource;

import java.nio.file.Path;

/**
 * Represents a file resource in <code>.minecraft</code>.
 * Should be registered in {@link GameResourceRegistry} and probably stored in a constant for later use.
 */
public interface ResourceFile extends GameResource {

    @Override
    default void ensureExists(Path resourceLocation) {
        if (!resourceLocation.toFile().exists()) {
            resourceLocation.toFile().getParentFile().mkdirs();
        }
    }
}
