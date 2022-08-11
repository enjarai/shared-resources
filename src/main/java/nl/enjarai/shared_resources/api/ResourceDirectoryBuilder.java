package nl.enjarai.shared_resources.api;

import java.util.Objects;

public class ResourceDirectoryBuilder {
    private String defaultSubdirectory;

    public ResourceDirectoryBuilder setDefaultSubdirectory(String defaultSubdirectory) {
        this.defaultSubdirectory = defaultSubdirectory;
        return this;
    }

    public ResourceDirectory build() {
        Objects.requireNonNull(defaultSubdirectory, "defaultSubdirectory must be set");

        return new ResourceDirectory() {
            @Override
            public String getDefaultSubdirectory() {
                return defaultSubdirectory;
            }
        };
    }
}
