package nl.enjarai.shared_resources.api;

import net.minecraft.text.Text;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class ResourceFileBuilder {
    private final Path defaultFile;
    private Text displayName;
    private List<Text> description = Collections.emptyList();
    private boolean requiresRestart = false;
    private boolean defaultEnabled = true;
    private boolean experimental = false;
    private GameResource.ResourceUpdateCallback updateCallback = (path) -> {};

    /**
     * A simple builder for {@link ResourceFile}s, providing easy access to all settings and defaults.
     * @param defaultFile The file in <code>.minecraft</code> where this resource usually resides.
     */
    public ResourceFileBuilder(Path defaultFile) {
        this.defaultFile = defaultFile;
    }

    /**
     * A simple builder for {@link ResourceFile}s, providing easy access to all settings and defaults.
     * (Alternative constructor)
     * @param defaultFile The file in <code>.minecraft</code> where this resource usually resides.
     */
    public ResourceFileBuilder(String defaultFile) {
        this(Paths.get(defaultFile));
    }

    /**
     * Sets the display name of the resource to be used in the config menu.
     */
    public ResourceFileBuilder setDisplayName(Text displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Set the description of this resource, with each Text element representing a new line.
     */
    public ResourceFileBuilder setDescription(Text... description) {
        this.description = Arrays.asList(description);
        return this;
    }

    /**
     * Set whether this file requires a restart to be changed.
     */
    public ResourceFileBuilder requiresRestart() {
        requiresRestart = true;
        return this;
    }

    /**
     * Set this if this file should be enabled by default.
     */
    public ResourceFileBuilder defaultEnabled(boolean enabled) {
        defaultEnabled = enabled;
        return this;
    }

    /**
     * Set this if this file is experimental and should be used with caution.
     */
    public ResourceFileBuilder isExperimental() {
        experimental = true;
        return this;
    }

    /**
     * Set the callback to be called whenever this file is changed, may be called even when the file is not changed.
     */
    public ResourceFileBuilder setUpdateCallback(GameResource.ResourceUpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
        return this;
    }

    public ResourceFile build() {
        return new ResourceFile() {
            @Override
            public Path getDefaultDirectory() {
                return defaultFile;
            }

            @Override
            public Text getDisplayName() {
                if (displayName == null) return ResourceFile.super.getDisplayName();

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
            public boolean isDefaultEnabled() {
                return defaultEnabled;
            }

            @Override
            public boolean isExperimental() {
                return experimental;
            }

            @Override
            public ResourceUpdateCallback getUpdateCallback() {
                return updateCallback;
            }
        };
    }
}
