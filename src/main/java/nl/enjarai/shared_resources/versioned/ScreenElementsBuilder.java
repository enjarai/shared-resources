package nl.enjarai.shared_resources.versioned;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import nl.enjarai.shared_resources.common.gui.DirectoryConfigEntry;

import java.nio.file.Path;
import java.util.function.Consumer;

public interface ScreenElementsBuilder {
    ButtonWidget buildButton(int x, int y, int width, int height, Text text, ButtonWidget.PressAction onPress);
    ButtonWidget buildLongButton(int x, int y, int width, int height, Text text, ButtonWidget.PressAction onPress);
    default AbstractConfigListEntry<Path> getDirectoryEntry(Text fieldName, Path initialValue, Path defaultValue, Consumer<Path> saveConsumer) {
        return new DirectoryConfigEntry(fieldName, initialValue, defaultValue, saveConsumer);
    }
}
