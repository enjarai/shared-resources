package nl.enjarai.shared_resources.api;

/**
 * Entrypoint interface for the Shared Resources mod.
 * Use this to register custom resource directories without a hard dependency.
 */
public interface SharedResourcesEntrypoint {
    void registerResources(ResourceDirectoryRegistry registry);
}
