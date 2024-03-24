package nl.enjarai.shared_resources.compat;

import nl.enjarai.shared_resources.SharedResources;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.List;

public class CompatMixinErrorHandler implements IMixinErrorHandler {
    private static final List<String> failed = new ArrayList<>();

    @Override
    public ErrorAction onPrepareError(IMixinConfig config, Throwable th, IMixinInfo mixin, ErrorAction action) {
        if (mixin.getConfig().getPlugin() instanceof CompatMixinPlugin) {
            onError(mixin.getClassName());
            return ErrorAction.WARN;
        }
        return action;
    }

    @Override
    public ErrorAction onApplyError(String targetClassName, Throwable th, IMixinInfo mixin, ErrorAction action) {
        if (mixin.getConfig().getPlugin() instanceof CompatMixinPlugin) {
            onError(mixin.getClassName());
            return ErrorAction.WARN;
        }
        return action;
    }

    public static void onError(String mixinClassName) {
        String mixinPackage = CompatMixinPlugin.getPackageName(mixinClassName);

        SharedResources.LOGGER.warn("Failed to apply mixin {} for package {}! Compatibility with the corresponding mod will most likely not work!",
                CompatMixinPlugin.getRelativeClassName(mixinClassName), mixinPackage);
        failed.add(mixinPackage);
    }

    public static boolean hasFailed(String mixinPackage) {
        return failed.contains(mixinPackage);
    }
}
