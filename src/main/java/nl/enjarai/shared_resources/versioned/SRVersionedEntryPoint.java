package nl.enjarai.shared_resources.versioned;

public interface SRVersionedEntryPoint {
    TextBuilder getTextBuilder();

    RenderSystemProxy getRenderSystemProxy();
}
