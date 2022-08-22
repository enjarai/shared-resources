package nl.enjarai.shared_resources.api;

import nl.enjarai.shared_resources.util.ModRegistry;

/**
 * Register resource directories here.
 * See {@link DirectoryResourceBuilder} on how to create a custom resource directory.
 */
public class DirectoryResourceRegistry extends ModRegistry<DirectoryResource> {
    public static final DirectoryResourceRegistry REGISTRY = new DirectoryResourceRegistry();
}
