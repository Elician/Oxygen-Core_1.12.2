package austeretony.alternateui.screen.core;

import java.util.LinkedHashSet;
import java.util.Set;

import austeretony.alternateui.util.EnumGUIAlignment;
import net.minecraft.client.renderer.GlStateManager;


public class GUIWorkspace extends GUIAdvancedElement<GUIWorkspace> {

    private boolean enableDefaultBackground;

    private final Set<AbstractGUISection> sectionsList = new LinkedHashSet<AbstractGUISection>(3);

    private AbstractGUISection currentSection;

    private int xAlignment, yAlignment;

    private int defBackgroundColor = 0x78101010;


    public GUIWorkspace(AbstractGUIScreen screen, int width, int height) {		
        this.initScreen(screen);		
        this.setSize(width, height);		
        this.setPosition((screen.width - width) / 2, (screen.height - height) / 2);
        this.setVisible(true);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (this.isDefaultBackgroundEnabled())
            this.drawDefaultBackground();
        super.draw(mouseX, mouseY);
        if (this.isTextureEnabled()) {   		
            GlStateManager.disableLighting();     
            GlStateManager.enableDepth();      
            GlStateManager.enableBlend();           
            this.mc.getTextureManager().bindTexture(this.getTexture());           
            drawCustomSizedTexturedRect(this.getX() + this.getTextureOffsetX(), this.getY() + this.getTextureOffsetY(), 0, 0, this.getTextureWidth(), this.getTextureHeight(), this.getTextureWidth(), this.getTextureHeight());             
            GlStateManager.disableBlend();      
            GlStateManager.disableDepth();      
            GlStateManager.enableLighting();       
        }   
    }

    public void drawDefaultBackground() {
        drawRect(0, 0, this.mc.displayWidth, this.mc.displayHeight, this.getDefaultBackgroundColor());
    }

    public Set<AbstractGUISection> getSections() {		
        return this.sectionsList;
    }

    public AbstractGUISection getCurrentSection() {		
        return this.currentSection;
    }


    public AbstractGUISection createSection() {    	
        AbstractGUISection section = new GUIBaseSection(this.screen);   	   	
        section.setPosition(this.getX(), this.getY());
        section.setSize(this.getWidth(), this.getHeight());   	
        this.getSections().add(section);   	
        return section;
    }


    public AbstractGUISection initSection(AbstractGUISection section) {   	
        section.setPosition(this.getX(), this.getY());
        section.setSize(this.getWidth(), this.getHeight());    	
        this.getSections().add(section);    	
        return section;
    }


    public void setCurrentSection(AbstractGUISection section) {		
        this.currentSection = section;		
    }

    public int getXAlignment() {		
        return this.xAlignment;
    }

    public int getYAlignment() {		
        return this.yAlignment;
    }


    public GUIWorkspace setAlignment(EnumGUIAlignment alignment, int xOffset, int yOffset) {			
        this.xAlignment = xOffset;
        this.yAlignment = yOffset;	        
        int 
        xPos = 0, 
        yPos = 0;
        switch (alignment) {
        case BOTTOM:
            xPos = (this.screen.width - this.getWidth()) / 2;
            yPos = (this.screen.height - this.getHeight());
            break;
        case BOTTOM_LEFT:
            xPos = 0;
            yPos = (this.screen.height - this.getHeight());
            break;
        case BOTTOM_RIGHT:
            xPos = 0;
            yPos = (this.screen.height - this.getHeight());
            break;
        case CENTER:
            xPos = (this.screen.width - this.getWidth()) / 2;
            yPos = (this.screen.height - this.getHeight()) / 2;
            break;
        case LEFT:
            xPos = 0;
            yPos = (this.screen.height - this.getHeight()) / 2;
            break;
        case RIGHT:
            xPos = (this.screen.width - this.getWidth());
            yPos = (this.screen.height - this.getHeight()) / 2;
            break;
        case TOP:
            xPos = (this.screen.width - this.getWidth()) / 2;
            yPos = 0;
            break;
        case TOP_LEFT:
            xPos = 0;
            yPos = 0;
            break;
        case TOP_RIGHT:
            xPos = (this.screen.width - this.getWidth());
            yPos = 0;
            break;
        }
        this.setPosition(xPos + xOffset, yPos + yOffset);           
        return this;
    }

    public boolean isDefaultBackgroundEnabled() {
        return this.enableDefaultBackground;
    }

    public GUIWorkspace enableDefaultBackground() {
        this.enableDefaultBackground = true;
        return this;
    }

    public GUIWorkspace enableDefaultBackground(int colorHex) {
        this.enableDefaultBackground();
        this.defBackgroundColor = colorHex;
        return this;
    }

    public int getDefaultBackgroundColor() {
        return this.defBackgroundColor;
    }

    public GUIWorkspace setDefaultBackgroundColor(int colorHex) {
        this.defBackgroundColor = colorHex;
        return this;
    }
}
