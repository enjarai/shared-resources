package nl.enjarai.shared_resources.api;

import net.minecraft.text.Text;

import java.nio.file.Path;
import java.util.function.Consumer;

public class ResourceDirectoryBuilder {
    private final Path defaultDirectory;
    private Text displayName;
    private boolean requiresRestart = false;
    private boolean overridesDefaultDirectory = false;
    private boolean defaultEnabled = true;
    private boolean experimental = false;
    private Consumer<Path> updateCallback = (path) -> {};

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
        this(Path.of(defaultDirectory));
    }

    /**
     * Sets the display name of the resource directory to be used in the config menu.
     */
    public ResourceDirectoryBuilder setDisplayName(Text displayName) {
        this.displayName = displayName;
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
     * Set the callback to be called whenever this directory is changed.
     */
    public ResourceDirectoryBuilder setUpdateCallback(Consumer<Path> updateCallback) {
        this.updateCallback = updateCallback;
        return this;
    }

    public ResourceDirectory build() {
        return new ResourceDirectory() {
            @Override
            public Path getDefaultDirectory() {
                return defaultDirectory;
            }

            @Override
            public Text getDisplayName() {
                if (displayName == null) return ResourceDirectory.super.getDisplayName();

                return displayName;
            }

            @Override
            public boolean requiresRestart() {
                return requiresRestart;
            }

            @Override
            public boolean overridesDefaultDirectory() {
                return overridesDefaultDirectory;
            }

            @Override
            public boolean defaultEnabled() {
                return defaultEnabled;
            }

            @Override
            public boolean isExperimental() {
                return experimental;
            }

            @Override
            public Consumer<Path> getUpdateCallback() {
                return updateCallback;
            }
        };
    }
}
