package nl.enjarai.shared_resources.common.compat.xaeroworldmap;

import com.google.common.collect.ImmutableSet;
import nl.enjarai.shared_resources.common.compat.CompatMixinPlugin;

import java.util.Set;

public class XaeroWorldMapMixinPlugin implements CompatMixinPlugin {
    @Override
    public Set<String> getRequiredMods() {
        return ImmutableSet.of("xaeroworldmap");
    }
}
