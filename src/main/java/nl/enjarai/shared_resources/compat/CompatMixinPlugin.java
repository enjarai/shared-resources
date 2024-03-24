package nl.enjarai.shared_resources.compat;

import net.fabricmc.loader.api.FabricLoader;
import nl.enjarai.shared_resources.SharedResources;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompatMixinPlugin implements IMixinConfigPlugin {
    static ArrayList<String> mixinPackages = new ArrayList<>();

    @Override
    public void onLoad(String mixinPackage) {
        mixinPackages.add(mixinPackage);
        Mixins.registerErrorHandlerClass(CompatMixinErrorHandler.class.getName());
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        try {
            ClassNode classNode = MixinService.getService().getBytecodeProvider().getClassNode(mixinClassName);
            AnnotationNode annotationNode = Annotations.getVisible(classNode, CompatMixin.class);

            //noinspection unchecked
            for (String modId : (List<String>) annotationNode.values.get(1)) {
                if (!FabricLoader.getInstance().isModLoaded(modId)) {
                    return false;
                }
            }

            try {
                MixinService.getService().getBytecodeProvider().getClassNode(targetClassName);
            } catch (ClassNotFoundException | IOException ignored) {
                SharedResources.LOGGER.warn("Target class not found: " + targetClassName);
                CompatMixinErrorHandler.onError(mixinClassName);
                return false;
            }

            String packageName = getPackageName(mixinClassName);
            if (CompatMixinErrorHandler.hasFailed(packageName)) {
                SharedResources.LOGGER.warn("Other mixins for package {} have failed, skipping mixin {}!",
                        packageName, getRelativeClassName(mixinClassName));
                return false;
            }

            return true;
        } catch (ClassNotFoundException | IOException ignored) {
            return false;
        }
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    public static String getRelativeClassName(String className) {
        for (String mixinPackage : mixinPackages) {
            if (className.startsWith(mixinPackage)) {
                return className.substring(mixinPackage.length() + 1);
            }
        }
        throw new IllegalStateException("Compat mixin plugin was called for a mixin it wasn't loaded for.");
    }

    public static String getPackageName(String className) {
        String relativeName = getRelativeClassName(className);
        String[] splitRelativeName = relativeName.split("\\.");
        String mixinName = splitRelativeName[splitRelativeName.length - 1];
        return relativeName.substring(0, relativeName.length() - mixinName.length() - 1);
    }

//    public static @Nullable GameResource getMixinInfo(String className) {
//        for (GameResource resource : GameResourceRegistry.REGISTRY) {
//            for (String mixinPackage : resource.getMixinPackages()) {
//                if (isMixinInSet(className, mixinPackage)) {
//                    return resource;
//                }
//            }
//        }
//        return null;
//    }
}
