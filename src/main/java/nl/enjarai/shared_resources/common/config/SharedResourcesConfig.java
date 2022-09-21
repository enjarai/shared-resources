package nl.enjarai.shared_resources.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nl.enjarai.shared_resources.api.GameResource;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.api.GameResourceRegistry;
import nl.enjarai.shared_resources.common.SharedResources;
import nl.enjarai.shared_resources.common.config.serialization.GameDirectoryProviderAdapter;
import nl.enjarai.shared_resources.common.config.serialization.IdentifierAdapter;
import nl.enjarai.shared_resources.common.gui.DirectoryConfigEntry;
import nl.enjarai.shared_resources.common.registry.GameResources;
import nl.enjarai.shared_resources.common.util.directory.EmptyGameDirectoryProvider;
import nl.enjarai.shared_resources.common.util.directory.GameDirectoryProvider;
import nl.enjarai.shared_resources.common.util.directory.RootedGameDirectoryProvider;
import nl.enjarai.shared_resources.util.GameResourceConfig;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static nl.enjarai.shared_resources.versioned.Versioned.TEXT;

@SuppressWarnings("unused")
public class SharedResourcesConfig implements GameResourceConfig {
    // Make sure we use the default config location instead of our modified one.
    public static final File CONFIG_FILE =
            FabricLoader.getInstance().getGameDir()
                    .resolve(GameResources.CONFIG.getDefaultDirectory()
                            .resolve(SharedResources.MODID + ".json")).toFile();
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Identifier.class, new IdentifierAdapter())
            .registerTypeAdapter(GameDirectoryProvider.class, new GameDirectoryProviderAdapter())
            .setPrettyPrinting() // Makes the json use new lines instead of being a "one-liner"
            .disableHtmlEscaping() // We'll be able to use custom chars without them being saved differently
            .create();

    public static SharedResourcesConfig CONFIG;

    static {
        CONFIG = loadConfigFile(CONFIG_FILE);

        CONFIG.save();
    }

    public void initEnabledResources() {
        Iterator<GameResource> allDirs = GameResourceRegistry.REGISTRY.iterator();
        while (allDirs.hasNext()) {
            GameResource dir = allDirs.next();
            Identifier id = dir.getId();

            if (!enabled.containsKey(id)) {
                setEnabled(id, dir.isDefaultEnabled());
            }
        }
    }

    // Actual saved data
    private GameDirectoryProvider globalDirectory = new RootedGameDirectoryProvider(Paths.get("global_resources"));
    private final HashMap<Identifier, Boolean> enabled = new HashMap<>();


    // Getters, setters and utils
    public GameDirectoryProvider getGlobalDirectory() {
        return globalDirectory;
    }

    public void setGlobalDirectory(@Nullable Path path) {
        if (path == null) {
            globalDirectory = new EmptyGameDirectoryProvider();
        } else {
            globalDirectory = new RootedGameDirectoryProvider(path);
        }

        GameResourceRegistry.REGISTRY.iterator().forEachRemaining(directory -> {
            Path dirPath = GameResourceHelper.getPathFor(directory);
            directory.getUpdateCallback().onUpdate(dirPath);
        });
    }


    public boolean isEnabled(Identifier id) {
        if (globalDirectory instanceof EmptyGameDirectoryProvider) return false;

        return enabled.getOrDefault(id, false);
    }

    public void setEnabled(Identifier id, boolean enabled) {
        this.enabled.put(id, enabled);
    }

    @Override
    public boolean isEnabled(GameResource directory) {
        return isEnabled(directory.getId());
    }

    @Override
    public @Nullable Path getDirectory(GameResource resource) {
        return getGlobalDirectory().getDirectory(resource);
    }

    public void setEnabled(GameResource directory, boolean enabled) {
        setEnabled(directory.getId(), enabled);
    }



    public Screen getScreen(Screen parent) {

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(TEXT.translatable("config.shared_resources.title"))
                .setSavingRunnable(this::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        Path globalDir = null;
        if (this.getGlobalDirectory() instanceof RootedGameDirectoryProvider) {
            globalDir = ((RootedGameDirectoryProvider) this.getGlobalDirectory()).getRoot();
        }

        ConfigCategory generalCategory = builder
                .getOrCreateCategory(TEXT.translatable("config.shared_resources.general"))
                .addEntry(
                        entryBuilder.startTextDescription(
                                TEXT.translatable("config.shared_resources.general.directory")
                        ).build()
                )
                .addEntry(new DirectoryConfigEntry(
                        TEXT.translatable("config.shared_resources.general.directory"),
                        globalDir,
                        Paths.get("global_resources"),
                        this::setGlobalDirectory
                ))
                .addEntry(
                        entryBuilder.startTextDescription(
                                TEXT.translatable("config.shared_resources.general.enabled")
                        ).build()
                );

        enabled.forEach((id, enabled) -> {
            GameResource directory = GameResourceRegistry.REGISTRY.get(id);
            if (directory == null) return;

            List<Text> description = new ArrayList<>(directory.getDescription());
            if (directory.isExperimental()) {
                if (description.size() > 0) description.add(Text.of(" "));
                description.add(TEXT.translatable("config.shared_resources.experimental[0]"));
                description.add(TEXT.translatable("config.shared_resources.experimental[1]"));
            }

            BooleanListEntry entry = entryBuilder.startBooleanToggle(directory.getDisplayName(), enabled)
                    .setDefaultValue(directory.isDefaultEnabled())
                    .setSaveConsumer(newEnabled -> {
                        setEnabled(id, newEnabled);
                        directory.getUpdateCallback().onUpdate(GameResourceHelper.getPathFor(directory));
                    })
                    .setTooltip(description.toArray(new Text[0]))
                    .build();
            entry.setRequiresRestart(directory.isRequiresRestart());
            generalCategory.addEntry(entry);
        });

        return builder.build();
    }


    public void save() {
        saveConfigFile(CONFIG_FILE);
    }

    /**
     * Loads config file.
     *
     * @param file file to load the config file from.
     * @return SharedResourcesConfig object
     */
    private static SharedResourcesConfig loadConfigFile(File file) {
        SharedResourcesConfig config = null;

        if (file.exists()) {
            // An existing config is present, we should use its values
            try (BufferedReader fileReader = new BufferedReader(
                    new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8)
            )) {
                // Parses the config file and puts the values into config object
                config = GSON.fromJson(fileReader, SharedResourcesConfig.class);
            } catch (IOException e) {
                throw new RuntimeException("Problem occurred when trying to load config: ", e);
            }
        }
        // gson.fromJson() can return null if file is empty
        if (config == null) {
            config = new SharedResourcesConfig();
        }

        // Saves the file in order to write new fields if they were added
        config.saveConfigFile(file);
        return config;
    }

    /**
     * Saves the config to the given file.
     *
     * @param file file to save config to
     */
    private void saveConfigFile(File file) {
        file.getParentFile().mkdirs();
        try (Writer writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
