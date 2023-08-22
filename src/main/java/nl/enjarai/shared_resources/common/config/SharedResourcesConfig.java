package nl.enjarai.shared_resources.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import nl.enjarai.shared_resources.api.GameResource;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.api.GameResourceRegistry;
import nl.enjarai.shared_resources.common.SharedResources;
import nl.enjarai.shared_resources.common.SharedResourcesPreLaunch;
import nl.enjarai.shared_resources.common.compat.CompatMixinErrorHandler;
import nl.enjarai.shared_resources.common.compat.CompatMixinPlugin;
import nl.enjarai.shared_resources.common.config.serialization.GameDirectoryProviderAdapter;
import nl.enjarai.shared_resources.common.config.serialization.IdentifierAdapter;
import nl.enjarai.shared_resources.common.registry.GameResources;
import nl.enjarai.shared_resources.common.util.directory.EmptyGameDirectoryProvider;
import nl.enjarai.shared_resources.common.util.directory.GameDirectoryProvider;
import nl.enjarai.shared_resources.common.util.directory.RootedGameDirectoryProvider;
import nl.enjarai.shared_resources.util.GameResourceConfig;
import nl.enjarai.shared_resources.versioned.Versioned;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

import static nl.enjarai.shared_resources.versioned.Versioned.TEXT;

@SuppressWarnings("unused")
public class SharedResourcesConfig implements GameResourceConfig {
    // Make sure we use the default config location instead of our modified one.
    public static final File CONFIG_FILE =
            FabricLoader.getInstance().getGameDir()
                    .resolve(GameResources.CONFIG.getDefaultPath()
                            .resolve(SharedResources.MODID + ".json")).toFile();
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Identifier.class, new IdentifierAdapter())
            .registerTypeAdapter(GameDirectoryProvider.class, new GameDirectoryProviderAdapter())
            .setPrettyPrinting() // Makes the json use new lines instead of being a "one-liner"
            .disableHtmlEscaping() // We'll be able to use custom chars without them being saved differently
            .create();

    public static SharedResourcesConfig CONFIG;

    static {
        boolean isNew = !CONFIG_FILE.exists();

        CONFIG = loadConfigFile(CONFIG_FILE);

        SharedResourcesPreLaunch.initApi();

        // If this is a new config, we're not headless and not on Mac, we'll open the first time setup screen.
        if (isNew && !GraphicsEnvironment.isHeadless() && !System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac")) {
            try {
                FirstStartupWindow.open(CONFIG);
            } catch (Exception e) {
                SharedResources.LOGGER.error("Error opening first startup window, skipping.", e);
            }
        }

        CONFIG.save();
    }

    public static void touch() {
    }

    public void initEnabledResources() {
        for (GameResource dir : GameResourceRegistry.REGISTRY) {
            Identifier id = dir.getId();

            if (!enabled.containsKey(id)) {
                setEnabled(id, dir.isDefaultEnabled());
            }
        }
    }

    // Actual saved data
    private GameDirectoryProvider globalDirectory = new RootedGameDirectoryProvider(Paths.get("."));
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

        for (GameResource resource : GameResourceRegistry.REGISTRY) {
            Path dirPath = GameResourceHelper.getPathOrDefaultFor(resource);
            resource.getUpdateCallback().onUpdate(dirPath);
        }
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

    public HashMap<Identifier, Boolean> getEnabled() {
        return enabled;
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
                .addEntry(Versioned.SCREEN_ELEMENTS.getDirectoryEntry(
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

        List<Identifier> resources = new ArrayList<>(GameResourceRegistry.REGISTRY.getIds());
        Collections.sort(resources);
        for (Identifier id : resources) {
            GameResource resource = GameResourceRegistry.REGISTRY.get(id);
            boolean enabled = isEnabled(id);

            List<Text> description = new ArrayList<>(resource.getDescription());
            if (resource.isExperimental()) {
                if (description.size() > 0) description.add(Text.of(" "));
                description.add(TEXT.translatable("config.shared_resources.experimental[0]"));
                description.add(TEXT.translatable("config.shared_resources.experimental[1]"));
            }

            Text displayName = resource.getDisplayName();
            if (resource.getMixinPackages().stream().anyMatch(CompatMixinErrorHandler::hasFailed)) {
                displayName = MutableText.of(displayName.getContent()).setStyle(Style.EMPTY.withColor(Formatting.RED));
            }

            BooleanListEntry entry = entryBuilder.startBooleanToggle(displayName, enabled)
                    .setDefaultValue(resource.isDefaultEnabled())
                    .setSaveConsumer(newEnabled -> {
                        setEnabled(id, newEnabled);
                        resource.getUpdateCallback().onUpdate(GameResourceHelper.getPathFor(resource));
                    })
                    .setTooltip(description.toArray(new Text[0]))
                    .build();
            entry.setRequiresRestart(resource.isRequiresRestart());
            generalCategory.addEntry(entry);
        }

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
