package nl.enjarai.shared_resources.api;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;

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
    String getDefaultDirectory();

    /**
     * The display name of the resource directory, used in the config menu.
     */
    Text getDisplayName();

    /**
     * Whether this directory requires a restart to be changed.
     */
    boolean requiresRestart();

    /**
     * Whether this directory completely overrides the default directory for this resource.
     */
    boolean overridesDefaultDirectory();
}
