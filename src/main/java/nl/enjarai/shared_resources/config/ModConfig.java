package nl.enjarai.shared_resources.config;

import nl.enjarai.shared_resources.util.directory.EmptyGameDirectoryProvider;
import nl.enjarai.shared_resources.util.directory.GameDirectoryProvider;

public class ModConfig {
    public static final ModConfig INSTANCE = new ModConfig();

    public GameDirectoryProvider globalDirectory = new EmptyGameDirectoryProvider();
    public boolean resourcePacksEnabled;
    public boolean shaderPacksEnabled;
}
