package nl.enjarai.shared_resources.api;

/**
 * Represents a set of mixins that should be applied to operate a certain resource.
 */
public interface CompatMixinSet {
    static CompatMixinSet of(String modId, String mixinPackage) {
        return new Impl(modId, mixinPackage);
    }

    String getModId();

    String getMixinPackage();

    class Impl implements CompatMixinSet {
        private final String modId;
        private final String mixinPackage;

        public Impl(String modId, String mixinPackage) {
            this.modId = modId;
            this.mixinPackage = mixinPackage;
        }

        @Override
        public String getModId() {
            return this.modId;
        }

        @Override
        public String getMixinPackage() {
            return this.mixinPackage;
        }
    }
}
