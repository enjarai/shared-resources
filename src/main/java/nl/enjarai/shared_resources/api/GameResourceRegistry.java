package nl.enjarai.shared_resources.api;

import nl.enjarai.shared_resources.common.util.ModRegistry;

/**
 * Register resource directories here.
 * See {@link ResourceDirectoryBuilder} on how to create a custom resource directory.
 */
public class GameResourceRegistry extends ModRegistry<GameResource> {
    public static final GameResourceRegistry REGISTRY = new GameResourceRegistry();
}
