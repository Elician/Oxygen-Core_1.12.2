package austeretony.alternateui.container.framework;

import net.minecraft.inventory.Slot;


public class GUIBaseSorter extends AbstractGUISorter {

    @Override
    public boolean isSlotValid(Slot slot) {
        return true;
    }

    @Override
    public boolean shouldAddEmptySlotsAfter() {
        return false;
    }
}
