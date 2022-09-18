package nl.enjarai.shared_resources.common.compat.iris;

import nl.enjarai.shared_resources.common.compat.CompatMixinPlugin;

import java.util.HashSet;
import java.util.Set;

public class IrisMixinPlugin implements CompatMixinPlugin {

    @Override
    public Set<String> getRequiredMods() {
        HashSet<String> set = new HashSet<>();
        set.add("iris");
        return set;
    }
}
