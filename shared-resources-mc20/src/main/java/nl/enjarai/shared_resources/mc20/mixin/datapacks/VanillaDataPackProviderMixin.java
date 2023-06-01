package nl.enjarai.shared_resources.mc20.mixin.datapacks;

import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.resource.VanillaDataPackProvider;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import nl.enjarai.shared_resources.mc20.util.ExternalFileResourcePackProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VanillaDataPackProvider.class)
public abstract class VanillaDataPackProviderMixin {
    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyArg(
            method = "createManager(Ljava/nio/file/Path;)Lnet/minecraft/resource/ResourcePackManager;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resource/ResourcePackManager;<init>([Lnet/minecraft/resource/ResourcePackProvider;)V"
            )
    )
    private static ResourcePackProvider[] sharedresources$addDataPackProvider(ResourcePackProvider[] providers) {
        return ArrayUtils.add(providers, new ExternalFileResourcePackProvider(
                () -> GameResourceHelper.getPathOrDefaultFor(GameResources.DATAPACKS)
        ));
    }
}
