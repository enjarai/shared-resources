package nl.enjarai.shared_resources.versioned;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public interface ScreenElementsBuilder {
    ButtonWidget buildButton(int x, int y, int width, int height, Text text, ButtonWidget.PressAction onPress);
    ButtonWidget buildLongButton(int x, int y, int width, int height, Text text, ButtonWidget.PressAction onPress);
}
