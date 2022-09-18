package nl.enjarai.shared_resources.common.compat;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/**
 * Mixin config plugin for compat, only applies mixins if specific mod is present.
 */
public interface CompatMixinPlugin extends IMixinConfigPlugin {
    Set<String> getRequiredMods();

    @Override
    default void onLoad(String mixinPackage) {

    }

    @Override
    default String getRefMapperConfig() {
        return null;
    }

    @Override
    default boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return getRequiredMods().stream()
                .allMatch((modId) -> FabricLoader.getInstance().isModLoaded(modId));
    }

    @Override
    default void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    default List<String> getMixins() {
        return null;
    }

    @Override
    default void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    default void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
