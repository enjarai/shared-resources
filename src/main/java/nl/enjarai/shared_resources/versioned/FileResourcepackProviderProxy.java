package nl.enjarai.shared_resources.versioned;

import java.nio.file.Path;

public interface FileResourcepackProviderProxy {
    void sharedresources$setPacksFolder(Path folder);

    Path sharedresources$getPacksFolder();
}
