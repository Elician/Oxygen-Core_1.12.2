package austeretony.alternateui.container.framework;

import java.util.Iterator;
import java.util.List;

import austeretony.alternateui.container.core.AbstractGUIContainer;
import austeretony.alternateui.screen.core.GUISimpleElement;
import austeretony.alternateui.util.EnumGUISlotsPosition;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class GUISlotsFramework extends GUISimpleElement<GUISlotsFramework> {

    public final EnumGUISlotsPosition slotsPosition;

    public final Container container;

    public final int firstSlotIndex, lastSlotIndex, rows, columns, visibleSlots;

    private int slotsDistanceHorizontal, slotsDistanceVertical;

    private boolean slotBottom, forceUpdate, isTooltipsDisabled;


    public final GUIContainerSlots slots;

    private int 
    slotBottomLayerColor = 0x30000000,
    slotHighlightingColor = 0x30FFFFFF;


    public GUISlotsFramework(EnumGUISlotsPosition slotsPosition, Container container, int firstSlotIndex, int lastSlotIndex, int rows, int columns) {
        this.slots = new GUIContainerSlots(this);
        this.slotsPosition = slotsPosition;	
        this.container = container;
        this.firstSlotIndex = firstSlotIndex;
        this.lastSlotIndex = lastSlotIndex;
        this.rows = rows;
        this.columns = columns;
        this.visibleSlots = rows * columns == 0 ? lastSlotIndex - firstSlotIndex : rows * columns;
        this.slotsDistanceHorizontal = 2;
        this.slotsDistanceVertical = 2;
        this.setSize(this.columns * (this.getSlotWidth() + this.slotsDistanceHorizontal), this.rows * (this.getSlotHeight() + this.slotsDistanceVertical));
        this.enableFull();
    }


    public void updateFramework(AbstractGUISorter sorter) {
        int i, k, size;
        Slot slot, slotCopy;
        Item itemInSlot;
        this.slots.visibleSlots.clear();
        this.slots.visibleSlotsIndexes.clear();
        this.slots.slotsBuffer.clear();
        this.slots.indexesBuffer.clear();
        this.slots.searchSlots.clear();
        this.slots.searchIndexes.clear();
        this.slots.items.clear();
        this.slots.setCurrentSorter(sorter);
        if (this.slots.hasScroller())
            this.slots.getScroller().reset();
        for (i = this.firstSlotIndex; i <= this.lastSlotIndex; i++) {
            if (i < this.container.inventorySlots.size()) {
                slot = (Slot) this.container.inventorySlots.get(i);   
                slotCopy = this.copySlot(slot);  
                size = this.slots.visibleSlots.size();                     
                if (sorter.isSlotValid(slotCopy)) {   
                    if (slotCopy.getHasStack())
                        this.slots.items.put(size, slotCopy.getStack());
                    if (this.slotsPosition == EnumGUISlotsPosition.CUSTOM) {        
                        k = size / this.columns;
                        slotCopy.xPos = this.getX() + size * (this.getSlotWidth() + this.getSlotDistanceHorizontal()) - k * ((this.getSlotWidth() + this.getSlotDistanceHorizontal()) * this.columns);
                        slotCopy.yPos = this.getY() + k * (this.getSlotHeight() + this.getSlotDistanceVertical()) - (size / this.visibleSlots) * (this.rows * (this.getSlotHeight() + this.getSlotDistanceVertical()));
                    }
                    this.slots.visibleSlots.add(slotCopy);
                    this.slots.visibleSlotsIndexes.add(i);  
                    this.slots.slotsBuffer.add(slotCopy);
                    this.slots.indexesBuffer.add(i);                                           
                }
            }
        }
        if (sorter.shouldAddEmptySlotsAfter()) {
            i = this.firstSlotIndex;
            for (i = this.firstSlotIndex; i <= this.lastSlotIndex; i++) {
                if (i < this.container.inventorySlots.size()) {
                    slot = (Slot) this.container.inventorySlots.get(i);   
                    slotCopy = this.copySlot(slot);  
                    if (!slotCopy.getHasStack()) {
                        size = this.slots.visibleSlots.size();
                        if (this.slotsPosition == EnumGUISlotsPosition.CUSTOM) {
                            k = size / this.columns;
                            slotCopy.xPos = this.getX() + size * (this.getSlotWidth() + this.getSlotDistanceHorizontal()) - k * ((this.getSlotWidth() + this.getSlotDistanceHorizontal()) * this.columns);
                            slotCopy.yPos = this.getY() + k * (this.getSlotHeight() + this.getSlotDistanceVertical()) - (size / this.visibleSlots) * (this.rows * (this.getSlotHeight() + this.getSlotDistanceVertical()));
                        }
                        this.slots.visibleSlots.add(slotCopy);
                        this.slots.visibleSlotsIndexes.add(i);  
                        this.slots.slotsBuffer.add(slotCopy);
                        this.slots.indexesBuffer.add(i); 
                    }
                }
            }
        }
    }

    public void updateFramework() {
        this.updateFramework(GUIContainerSlots.BASE_SORTER);
    }

    @Override
    public void mouseOver(int mouseX, int mouseY) {  	       	
        this.setHovered(this.isEnabled() && mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.getWidth() && mouseY < this.getY() + this.getHeight());		
        if (this.slots.hasScroller() && this.slots.getScroller().hasSlider()) {
            if ((this.slots.hasSearchField() && !this.slots.getSearchField().isDragged()) || !this.slots.hasSearchField()) {
                this.slots.getScroller().getSlider().mouseOver(mouseX, mouseY);
                this.slots.getScroller().getSlider().mouseClicked(mouseX, mouseY, 0);
                ((AbstractGUIContainer) this.screen).handleFrameworkSlidebar(this, mouseY);//TODO FIX
            }       
        }	     
        if (this.slots.hasSearchField())
            this.slots.getSearchField().mouseOver(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isEnabled() 
                && mouseButton == 0)
            if (this.slots.hasSearchField())     
                this.slots.getSearchField().mouseClicked(mouseX, mouseY, 1);
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (this.isVisible()) {
            if (this.slots.hasScroller() && this.slots.getScroller().hasSlider())
                this.slots.getScroller().getSlider().draw(mouseX, mouseY);			
            if (this.slots.hasSearchField())
                this.slots.getSearchField().draw(mouseX, mouseY);
        }
    }

    @Override
    public void drawTooltip(int mouseX, int mouseY) {}

    @Override
    public boolean keyTyped(char typedChar, int keyCode) {
        if (this.isEnabled()) {
            if (this.slots.hasSearchField()) {               
                if (this.slots.getSearchField().keyTyped(typedChar, keyCode)) {
                    if (this.slots.getSearchField().getTypedText().toLowerCase().length() > 0)
                        this.updateSearchResult(this);
                    else
                        this.updateFramework(this.slots.getCurrentSorter());
                    if (this.slots.hasScroller())
                        this.slots.getScroller().reset();
                }
            }
        }
        return super.keyTyped(typedChar, keyCode);
    }

    protected void updateSearchResult(GUISlotsFramework framework) {
        int slotIndex, size, k;
        Slot slotCopy;
        ItemStack itemStack;
        String itemName;
        String typedText = framework.slots.getSearchField().getTypedText().toLowerCase();
        Iterator itemsIterator = framework.slots.items.keySet().iterator();
        framework.slots.visibleSlots.clear();
        framework.slots.visibleSlotsIndexes.clear();   
        framework.slots.searchSlots.clear();
        framework.slots.searchIndexes.clear();   
        while (itemsIterator.hasNext()) {
            slotIndex = (Integer) itemsIterator.next();
            itemStack = framework.slots.items.get(slotIndex);
            if (itemStack != null) {
                itemName = itemStack.getDisplayName().toLowerCase();
                if (itemName.startsWith(typedText) || itemName.contains(" " + typedText)) {
                    size = framework.slots.searchSlots.size();
                    slotCopy = this.copySlot(framework.slots.slotsBuffer.get(slotIndex));
                    if (framework.slotsPosition == EnumGUISlotsPosition.CUSTOM) {
                        k = size / framework.columns;
                        slotCopy.xPos = framework.getX() + size * (framework.getSlotWidth() + framework.getSlotDistanceHorizontal()) - k * ((framework.getSlotWidth() + framework.getSlotDistanceHorizontal()) * framework.columns);
                        slotCopy.yPos = framework.getY() + k * (framework.getSlotHeight() + framework.getSlotDistanceVertical()) - (size / framework.visibleSlots) * (framework.rows * (framework.getSlotHeight() + framework.getSlotDistanceVertical()));
                    }
                    framework.slots.visibleSlots.add(slotCopy);
                    framework.slots.visibleSlotsIndexes.add(framework.slots.indexesBuffer.get(slotIndex));  
                    framework.slots.searchSlots.add(slotCopy);
                    framework.slots.searchIndexes.add(framework.slots.indexesBuffer.get(slotIndex));  
                }
            }
        }
    }

    protected final Slot copySlot(Slot slot) {
        return new Slot(slot.inventory, slot.getSlotIndex(), slot.xPos, slot.yPos);     
    }

    @Override
    public void update() {
        if (this.slots.hasSearchField())
            this.slots.getSearchField().updateCursorCounter();
    }

    public GUIContainerSlots getSlots() {
        return this.slots;
    }


    public List<Slot> getSlotsList() {
        return this.slots.slotsBuffer;
    }


    @Override
    public GUISlotsFramework setPosition(int xPosition, int yPosition) {
        if (this.slotsPosition == EnumGUISlotsPosition.CUSTOM)
            super.setPosition(xPosition, yPosition);
        return this;
    }


    public void setSlotDistance(int slotsDistanceHorizontal, int slotsDistanceVertical) {
        this.slotsDistanceHorizontal = slotsDistanceHorizontal;
        this.slotsDistanceVertical = slotsDistanceVertical;
        this.setSize(this.columns * (this.getSlotWidth() + slotsDistanceHorizontal), this.rows * (this.getSlotHeight() + slotsDistanceVertical));
    }

    public int getSlotDistanceHorizontal() {
        return this.slotsDistanceHorizontal;
    }

    public int getSlotDistanceVertical() {
        return this.slotsDistanceVertical;
    }

    public boolean isSlotBottomLayerEnabled() {
        return this.slotBottom;
    }


    public GUISlotsFramework enableSlotBottomLayer() {
        this.slotBottom = true;
        return this;
    }

    public int getSlotBottomLayerColor() {
        return this.slotBottomLayerColor;
    }


    public GUISlotsFramework setSlotBottomLayerColor(int colorHex) {
        this.slotBottomLayerColor = colorHex;
        return this;
    }

    public int getSlotHighlightingColor() {
        return this.slotHighlightingColor;
    }


    public GUISlotsFramework setSlotHighlightingColor(int colorHex) {
        this.slotHighlightingColor = colorHex;
        return this;
    }

    public int getSlotWidth() {
        return GUIContainerSlots.SLOT_SIZE;
    }

    public int getSlotHeight() {
        return GUIContainerSlots.SLOT_SIZE;
    }

    public boolean getForcedToUpdate() {
        return this.forceUpdate;
    }		


    public GUISlotsFramework forceUpdateOnEveryClick() {
        this.forceUpdate = true;
        return this;
    }

    public boolean getTooltipsDisabled() {

        return this.isTooltipsDisabled;
    }		


    public GUISlotsFramework disableTooltips() {
        this.isTooltipsDisabled = true;
        return this;
    }
}