package nl.enjarai.shared_resources.api;

import net.minecraft.text.Text;

import java.util.Objects;

public class ResourceDirectoryBuilder {
    private String defaultDirectory;
    private Text displayName;

    public ResourceDirectoryBuilder setDefaultDirectory(String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
        return this;
    }

    public ResourceDirectoryBuilder setDisplayName(Text displayName) {
        this.displayName = displayName;
        return this;
    }

    public ResourceDirectory build() {
        Objects.requireNonNull(defaultDirectory, "defaultSubdirectory must be set");
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
        };
    }
}
