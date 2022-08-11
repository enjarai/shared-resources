package nl.enjarai.shared_resources.mixin;

import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

import static nl.enjarai.shared_resources.SharedResources.GLOBAL_RP_PROVIDER;

@Mixin(ResourcePackManager.class)
public abstract class ResourcePackManagerMixin {
	@Mutable
	@Shadow @Final private Set<ResourcePackProvider> providers;

	@Inject(
			method = "<init>(Lnet/minecraft/resource/ResourcePackProfile$Factory;[Lnet/minecraft/resource/ResourcePackProvider;)V",
			at = @At(value = "RETURN")
	)
	private void init(CallbackInfo ci) {
		providers = new HashSet<>(providers);

		providers.add(GLOBAL_RP_PROVIDER);
	}
}
