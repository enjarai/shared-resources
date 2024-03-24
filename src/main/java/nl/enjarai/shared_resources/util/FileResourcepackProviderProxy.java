package nl.enjarai.shared_resources.util;

import java.nio.file.Path;

public interface FileResourcepackProviderProxy {
    void sharedresources$setPacksFolder(Path folder);

    Path sharedresources$getPacksFolder();
}
