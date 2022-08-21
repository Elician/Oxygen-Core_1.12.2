package austeretony.alternateui.screen.core;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class GUIBaseSection extends AbstractGUISection {

    public GUIBaseSection(AbstractGUIScreen screen) {
        super(screen);
    }

    @Override
    public void init() {}

    @Override
    public void handleElementClick(AbstractGUISection section, GUIBaseElement element, int mouseButton) {}
}
