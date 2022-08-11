package nl.enjarai.shared_resources.config;

import net.minecraft.util.Identifier;
import nl.enjarai.shared_resources.util.directory.GameDirectoryProvider;
import nl.enjarai.shared_resources.util.directory.RootedGameDirectoryProvider;

import java.nio.file.Path;
import java.util.HashMap;

// TODO make this save to json and be editable ingame
public class ModConfig {
    public static final ModConfig INSTANCE = new ModConfig();

    private GameDirectoryProvider globalDirectory = new RootedGameDirectoryProvider(Path.of("global_resources"));
    public HashMap<Identifier, Boolean> enabled = new HashMap<>();

    public GameDirectoryProvider getGlobalDirectory() {
        return globalDirectory;
    }
}
