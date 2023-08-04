package nl.enjarai.shared_resources.common.compat.skinshuffle;

import com.google.common.collect.ImmutableSet;
import nl.enjarai.shared_resources.common.compat.CompatMixinPlugin;

import java.util.Set;

public class SkinShuffleMixinPlugin implements CompatMixinPlugin {
    @Override
    public Set<String> getRequiredMods() {
        return ImmutableSet.of("skinshuffle");
    }
}
