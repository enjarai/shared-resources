package nl.enjarai.shared_resources.mc16.mixin.datapacks;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.resource.ResourcePackProvider;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.common.registry.GameResources;
import nl.enjarai.shared_resources.mc16.util.ExternalFileResourcePackProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyArg(
            method = "method_30296",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resource/ResourcePackManager;<init>([Lnet/minecraft/resource/ResourcePackProvider;)V"
            )
    )
    private ResourcePackProvider[] sharedresources$addDataPackProvider(ResourcePackProvider[] providers) {
        return ArrayUtils.add(providers, new ExternalFileResourcePackProvider(
                () -> GameResourceHelper.getPathOrDefaultFor(GameResources.DATAPACKS)
        ));
    }
}
