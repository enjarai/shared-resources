package nl.enjarai.shared_resources.mc19_2.mixin.resourcepacks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackProvider;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import nl.enjarai.shared_resources.mc19_2.util.ExternalFileResourcePackProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/resource/ResourcePackManager.<init>(Lnet/minecraft/resource/ResourcePackProfile$Factory;[Lnet/minecraft/resource/ResourcePackProvider;)V"
            )
    )
    private ResourcePackProvider[] sharedresources$addResourcePackProvider(ResourcePackProvider[] providers) {
        return ArrayUtils.add(providers, new ExternalFileResourcePackProvider(
                () -> GameResourceHelper.getPathFor(GameResources.RESOURCEPACKS)
        ));
    }
}
