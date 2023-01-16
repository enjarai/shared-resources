package nl.enjarai.shared_resources.mc19_3.mixin.resourcepacks;

import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FileResourcePackProvider.class)
public interface FileResourcePackProviderAccessor {
    @Accessor("type")
    ResourceType sharedresources$getResourceType();
}
