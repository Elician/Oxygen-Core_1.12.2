package austeretony.alternateui.container.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import austeretony.alternateui.screen.browsing.GUIScroller;
import austeretony.alternateui.screen.text.GUITextField;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class GUIContainerSlots {

    private final GUISlotsFramework framework;


    public final List<Slot> visibleSlots = new ArrayList<Slot>();
    public final List<Integer> visibleSlotsIndexes = new ArrayList<Integer>();


    public final List<Slot> slotsBuffer = new ArrayList<Slot>();
    public final List<Integer> indexesBuffer = new ArrayList<Integer>();


    public final List<Slot> searchSlots = new ArrayList<Slot>();
    public final List<Integer> searchIndexes = new ArrayList<Integer>();

    public static final int SLOT_SIZE = 16;

    private boolean hasScroller, hasSearchField, hasContextMenu, hasSlotRenderer;


    public static final AbstractGUISorter BASE_SORTER = new GUIBaseSorter();

    private AbstractGUISorter currentSorter;

    private GUIScroller scroller;

    private GUITextField searchField;

    public final Map<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();   

    public GUIContainerSlots(GUISlotsFramework framework) {
        this.framework = framework;
        this.currentSorter = BASE_SORTER;
    }

    public boolean hasSlotRenderer() {
        return this.hasSlotRenderer;
    }

    public int getSlotWidth() {
        return SLOT_SIZE;
    }

    public int getSlotHeight() {
        return SLOT_SIZE;
    }

    public void setCurrentSorter(AbstractGUISorter sorter) {
        this.currentSorter = sorter;
    }

    public AbstractGUISorter getCurrentSorter() {
        return this.currentSorter;
    }

    public boolean hasScroller() {
        return this.hasScroller;
    }


    public void initScroller(GUIScroller scroller) {
        this.scroller = this.scroller == null ? scroller : this.scroller;
        this.hasScroller = true;
    }

    public GUIScroller getScroller() {
        return this.scroller;
    }

    public boolean hasSearchField() {
        return this.hasSearchField;
    }


    public void initSearchField(GUITextField searchField) {
        this.searchField = this.searchField == null ? searchField : this.searchField;
        this.hasSearchField = true;
    }	

    public GUITextField getSearchField() {
        return this.searchField;
    }

    public boolean hasContextMenu() {
        return this.hasContextMenu;
    }
}
