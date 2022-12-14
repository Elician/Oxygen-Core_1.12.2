package austeretony.alternateui.screen.button;

import austeretony.alternateui.screen.core.GUIAdvancedElement;
import net.minecraft.util.ResourceLocation;

/**
 *
 * 
 * @author AustereTony
 */
public class GUIButton extends GUIAdvancedElement<GUIButton> {

    /**
     * Конструктор для кнопки, добавляемой на панель. Все параметры определяются панелью при добавлении кнопки.
     */
    public GUIButton() {        
        this.enableFull();
    }

    /**
     * Конструктор для создания автономной кнопки (вне панели).
     * 
     * @param xPosition позиция по x
     * @param yPosition позиция по y
     * @param buttonWidth ширина кнопки (активной зоны)
     * @param buttonHeight высота кнопки (активной зоны)
     */
    public GUIButton(int xPosition, int yPosition, int buttonWidth, int buttonHeight) {    	
        this();   	
        this.setPosition(xPosition, yPosition);
        this.setSize(buttonWidth, buttonHeight);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {   	
        boolean flag = super.mouseClicked(mouseX, mouseY, mouseButton);   	
        if (flag) {    		
            this.screen.handleElementClick(this.screen.getWorkspace().getCurrentSection(), this);	    	
            this.screen.getWorkspace().getCurrentSection().handleElementClick(this.screen.getWorkspace().getCurrentSection(), this, mouseButton);						
            if (this.screen.getWorkspace().getCurrentSection().hasCurrentCallback())			
                this.screen.getWorkspace().getCurrentSection().getCurrentCallback().handleElementClick(this.screen.getWorkspace().getCurrentSection(), this, mouseButton);
        }			    	
        return flag;
    }

    /**
     * Установка текстуры для кнопки.
     * 
     * @param textureLoctaion путь к текстуре
     * @param textureWidth ширина
     * @param textureHeight высота
     * 
     * @return вызывающий объект
     */
    @Override
    public GUIButton setTexture(ResourceLocation texture, int textureWidth, int textureHeight) {    	
        this.setTexture(texture);   	
        this.setTextureSize(textureWidth, textureHeight);   	
        this.setImageSize(textureWidth * 3, textureHeight);  	
        return this;
    }
}
