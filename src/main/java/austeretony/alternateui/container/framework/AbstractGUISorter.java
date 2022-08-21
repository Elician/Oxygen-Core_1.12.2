package austeretony.alternateui.container.framework;

import net.minecraft.inventory.Slot;


public abstract class AbstractGUISorter {


    public abstract boolean isSlotValid(Slot slot);


    public abstract boolean shouldAddEmptySlotsAfter();
}
