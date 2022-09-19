package nl.enjarai.shared_resources.api;

/**
 * Entrypoint interface for the Shared Resources mod.
 * Use this to register custom resource directories without a hard dependency.
 * <b>Gets run at a preLaunch entrypoint</b>, limit interactions with vanilla classes as much as possible.
 * For more information see {@link net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint}
 */
public interface SharedResourcesEntrypoint {
    void registerResources(GameResourceRegistry registry);
}
