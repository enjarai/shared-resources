package nl.enjarai.shared_resources.mc19.util;

import net.minecraft.text.Text;
import nl.enjarai.shared_resources.common.util.TextBuilder;

public class TextBuilderImpl implements TextBuilder {
    @Override
    public Text translatable(String key) {
        return Text.translatable(key);
    }

}