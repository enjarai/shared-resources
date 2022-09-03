package nl.enjarai.shared_resources.common.compat.iris;

import nl.enjarai.shared_resources.common.compat.CompatMixinPlugin;

import java.util.Set;

public class IrisMixinPlugin implements CompatMixinPlugin {

    @Override
    public Set<String> getRequiredMods() {
        return Set.of("iris");
    }
}
