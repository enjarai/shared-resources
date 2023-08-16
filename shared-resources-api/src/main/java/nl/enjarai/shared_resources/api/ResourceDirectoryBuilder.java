package nl.enjarai.shared_resources.api;

import net.minecraft.text.Text;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class ResourceDirectoryBuilder {
    private final Path defaultDirectory;
    private Text displayName;
    private List<Text> description = Collections.emptyList();
    private boolean requiresRestart = false;
    private boolean overridesDefaultDirectory = false;
    private boolean defaultEnabled = true;
    private boolean experimental = false;
    private GameResource.ResourceUpdateCallback updateCallback = (path) -> {};
    private List<String> mixinPackages = new ArrayList<>();

    /**
     * A simple builder for {@link ResourceDirectory}s, providing easy access to all settings and defaults.
     * @param defaultDirectory The subdirectory of <code>.minecraft</code> where this resource usually resides.
     */
    public ResourceDirectoryBuilder(Path defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }

    /**
     * A simple builder for {@link ResourceDirectory}s, providing easy access to all settings and defaults.
     * (Alternative constructor)
     * @param defaultDirectory The subdirectory of <code>.minecraft</code> where this resource usually resides.
     */
    public ResourceDirectoryBuilder(String defaultDirectory) {
        this(Paths.get(defaultDirectory));
    }

    /**
     * Sets the display name of the resource directory to be used in the config menu.
     */
    public ResourceDirectoryBuilder setDisplayName(Text displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Set the description of this resource directory, with each Text element representing a new line.
     */
    public ResourceDirectoryBuilder setDescription(Text... description) {
        this.description = Arrays.asList(description);
        return this;
    }

    /**
     * Set whether this directory requires a restart to be changed.
     */
    public ResourceDirectoryBuilder requiresRestart() {
        requiresRestart = true;
        return this;
    }

    /**
     * Set this if the default directory will be fully overridden by the custom one.
     * Try to avoid this if possible by merging entries.
     */
    public ResourceDirectoryBuilder overridesDefaultDirectory() {
        overridesDefaultDirectory = true;
        return this;
    }

    /**
     * Set this if this directory should be enabled by default.
     */
    public ResourceDirectoryBuilder defaultEnabled(boolean enabled) {
        defaultEnabled = enabled;
        return this;
    }

    /**
     * Set this if this directory is experimental and should be used with caution.
     */
    public ResourceDirectoryBuilder isExperimental() {
        experimental = true;
        return this;
    }

    /**
     * Set the callback to be called whenever this directory is changed, may be called even when the directory is not changed.
     */
    public ResourceDirectoryBuilder setUpdateCallback(GameResource.ResourceUpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
        return this;
    }

    /**
     * Add a mixin package that this directory needs to function.
     */
    public ResourceDirectoryBuilder addMixinPackage(String mixinPackage) {
        mixinPackages.add(mixinPackage);
        return this;
    }

    public ResourceDirectory build() {
        return new ResourceDirectory() {
            @Override
            public Path getDefaultPath() {
                return defaultDirectory;
            }

            @Override
            public Text getDisplayName() {
                if (displayName == null) return ResourceDirectory.super.getDisplayName();

                return displayName;
            }

            @Override
            public List<Text> getDescription() {
                return description;
            }

            @Override
            public boolean isRequiresRestart() {
                return requiresRestart;
            }

            @Override
            public boolean isOverridesDefaultDirectory() {
                return overridesDefaultDirectory;
            }

            @Override
            public boolean isDefaultEnabled() {
                return defaultEnabled;
            }

            @Override
            public boolean isExperimental() {
                return experimental;
            }

            @Override
            public GameResource.ResourceUpdateCallback getUpdateCallback() {
                return updateCallback;
            }

            @Override
            public List<String> getMixinPackages() {
                return mixinPackages;
            }
        };
    }
}
