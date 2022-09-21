package nl.enjarai.shared_resources.mc19;

import nl.enjarai.shared_resources.mc19.impl.RenderSystemProxyImpl;
import nl.enjarai.shared_resources.versioned.RenderSystemProxy;
import nl.enjarai.shared_resources.versioned.SRVersionedEntryPoint;
import nl.enjarai.shared_resources.versioned.TextBuilder;
import nl.enjarai.shared_resources.mc19.impl.TextBuilderImpl;

public class SREntryPoint implements SRVersionedEntryPoint {

    @Override
    public TextBuilder getTextBuilder() {
        return new TextBuilderImpl();
    }

    @Override
    public RenderSystemProxy getRenderSystemProxy() {
        return new RenderSystemProxyImpl();
    }
}
