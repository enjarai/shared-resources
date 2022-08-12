package nl.enjarai.shared_resources.api;

import net.minecraft.text.Text;

public class ResourceDirectoryBuilder {
    private final String defaultDirectory;
    private Text displayName;
    private boolean requiresRestart = false;
    private boolean overridesDefaultDirectory = false;

    /**
     * A simple builder for {@link ResourceDirectory}s, providing easy access to all settings and defaults.
     * @param defaultDirectory The subdirectory of <code>.minecraft</code> where this resource usually resides.
     */
    public ResourceDirectoryBuilder(String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
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

    public ResourceDirectory build() {
        if (displayName == null) {
            displayName = Text.of(defaultDirectory);
        }

        return new ResourceDirectory() {
            @Override
            public String getDefaultDirectory() {
                return defaultDirectory;
            }

            @Override
            public Text getDisplayName() {
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
        };
    }
}
