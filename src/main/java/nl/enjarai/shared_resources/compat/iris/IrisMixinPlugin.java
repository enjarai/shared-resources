package nl.enjarai.shared_resources.compat.iris;

import nl.enjarai.shared_resources.compat.CompatMixinPlugin;

import java.util.Set;

public class IrisMixinPlugin implements CompatMixinPlugin {

    @Override
    public Set<String> getRequiredMods() {
        return Set.of("iris");
    }
}
