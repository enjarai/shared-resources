package nl.enjarai.shared_resources.common.compat.litematica;

import nl.enjarai.shared_resources.common.compat.CompatMixinPlugin;

import java.util.HashSet;
import java.util.Set;

public class LitematicaMixinPlugin implements CompatMixinPlugin {
    @Override
    public Set<String> getRequiredMods() {
        HashSet<String> set = new HashSet<>();
        set.add("litematica");
        return set;
    }
}
