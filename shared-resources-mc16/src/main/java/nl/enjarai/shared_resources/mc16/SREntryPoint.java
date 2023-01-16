package nl.enjarai.shared_resources.mc16;

import nl.enjarai.shared_resources.mc16.impl.RenderSystemProxyImpl;
import nl.enjarai.shared_resources.mc16.impl.ScreenElementsBuilderImpl;
import nl.enjarai.shared_resources.versioned.RenderSystemProxy;
import nl.enjarai.shared_resources.versioned.SRVersionedEntryPoint;
import nl.enjarai.shared_resources.versioned.ScreenElementsBuilder;
import nl.enjarai.shared_resources.versioned.TextBuilder;
import nl.enjarai.shared_resources.mc16.impl.TextBuilderImpl;

public class SREntryPoint implements SRVersionedEntryPoint {

    @Override
    public TextBuilder getTextBuilder() {
        return new TextBuilderImpl();
    }

    @Override
    public RenderSystemProxy getRenderSystemProxy() {
        return new RenderSystemProxyImpl();
    }

    @Override
    public ScreenElementsBuilder getScreenElementsBuilder() {
        return new ScreenElementsBuilderImpl();
    }
}
