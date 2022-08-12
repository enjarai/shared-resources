package nl.enjarai.shared_resources.api;

import nl.enjarai.shared_resources.util.ModRegistry;

/**
 * Register resource directories here.
 * See {@link ResourceDirectoryBuilder} on how to create a custom resource directory.
 */
public class ResourceDirectoryRegistry extends ModRegistry<ResourceDirectory> {
    public static final ResourceDirectoryRegistry REGISTRY = new ResourceDirectoryRegistry();
}
