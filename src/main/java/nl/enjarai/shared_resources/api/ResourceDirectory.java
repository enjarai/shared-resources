package nl.enjarai.shared_resources.api;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Represents a directory in <code>.minecraft</code> where a certain resource is stored.
 * Should be registered in {@link ResourceDirectoryRegistry} and probably stored in a constant for later use.
 */
public interface ResourceDirectory {
    @Nullable
    default Identifier getId() {
        return ResourceDirectoryRegistry.REGISTRY.getId(this);
    }

    /**
     * The subdirectory of <code>.minecraft</code> where this resource usually resides.
     */
    Path getDefaultDirectory();

    /**
     * The display name of the resource directory, used in the config menu.
     */
    default Text getDisplayName() {
        var id = getId();
        Objects.requireNonNull(id, "Resource directory should be registered.");

        return Text.of(id.toString());
    }

    /**
     * Whether this directory requires a restart to be changed.
     */
    boolean requiresRestart();

    /**
     * Whether this directory completely overrides the default directory for this resource.
     */
    boolean overridesDefaultDirectory();

    /**
     * If this directory should be enabled by default.
     */
    boolean defaultEnabled();

    /**
     * If this directory change is experimental and should be used with caution.
     */
    boolean isExperimental();

    /**
     * The callback to be called whenever this directory is changed
     */
    default Consumer<Path> getUpdateCallback() {
        return (path) -> {};
    }
}
