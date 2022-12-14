package austeretony.alternateui.screen.button;

import austeretony.alternateui.screen.core.GUIAdvancedElement;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;


public class GUICheckBoxButton extends GUIAdvancedElement<GUICheckBoxButton> {

    private int innerBoxSize;

    private int 
    enabledInnerBoxColor = 0xFF303030,
    disabledInnerBoxColor = 0xFF202020,
    hoveredInnerBoxColor = 0xFF858585;

    /**
     * Конструктор для создания автономной кнопки (вне панели).
     * 
     * @param xPosition позиция по x
     * @param yPosition позиция по y
     * @param sideSize размер стороны кнопки (кнопка квадратная)
     */
    public GUICheckBoxButton(int xPosition, int yPosition, int sideSize) {
        this.setPosition(xPosition, yPosition);
        this.setSize(sideSize, sideSize);
        this.setInnerBoxSize(sideSize / 2);
        this.enableFull();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (this.isVisible()) {
            GlStateManager.pushMatrix();           
            GlStateManager.translate(this.getX(), this.getY(), 0.0F);            
            GlStateManager.scale(this.getScale(), this.getScale(), 0.0F);
            if (this.isTextureEnabled()) {
                int u = this.getTextureU();
                this.mc.getTextureManager().bindTexture(this.getTexture());          
                if (this.isHovered() || this.isToggled())
                    u += this.getTextureWidth() * 2;
                else
                    u += this.getTextureWidth();
                GlStateManager.enableBlend(); 
                drawCustomSizedTexturedRect((this.getWidth() - this.getTextureWidth()) / 2, (this.getHeight() - this.getTextureHeight()) / 2, u, this.getTextureV(), this.getTextureWidth(), this.getTextureHeight(), this.getImageWidth(), this.getImageHeight());
                drawCustomSizedTexturedRect((this.getWidth() - this.getTextureWidth()) / 2, (this.getHeight() - this.getTextureHeight()) / 2, u + this.getTextureWidth() * 3, this.getTextureV(), this.getTextureWidth(), this.getTextureHeight(), this.getImageWidth(), this.getImageHeight());
                GlStateManager.disableBlend(); 
            }
            if (this.isDynamicBackgroundEnabled()) {
                int backgroundColor, innerBoxColor;
                if (!this.isEnabled()) {
                    backgroundColor = this.getDisabledBackgroundColor();
                    innerBoxColor = this.getDisabledInnerBoxColor();
                } else if (this.isHovered() || this.isToggled()) {
                    backgroundColor = this.getHoveredBackgroundColor();
                    innerBoxColor = this.getHoveredInnerBoxColor();
                } else {
                    backgroundColor = this.getEnabledBackgroundColor();
                    innerBoxColor = this.getEnabledInnerBoxColor();
                }     		
                drawRect(0, 0, this.getWidth(), this.getHeight(), backgroundColor);
                drawRect((this.getWidth() - this.getInnerBoxSize()) / 2, (this.getWidth() - this.getInnerBoxSize()) / 2, this.getWidth() - (this.getWidth() - this.getInnerBoxSize()) / 2, this.getHeight() - (this.getWidth() - this.getInnerBoxSize()) / 2, innerBoxColor);
            }
            GlStateManager.popMatrix();
        }
    }

    /**
     * Определяет, сделан ли клик по элементу.
     * 
     * @param mouseX 
     * @param mouseY 
     * @return true если клик совершён
     */
    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean flag = super.mouseClicked(mouseX, mouseY, mouseButton);
        if (flag && mouseButton == 0) {
            this.setToggled(!this.isToggled());
            this.screen.handleElementClick(this.screen.getWorkspace().getCurrentSection(), this);
            this.screen.getWorkspace().getCurrentSection().handleElementClick(this.screen.getWorkspace().getCurrentSection(), this, mouseButton);
            if (this.screen.getWorkspace().getCurrentSection().hasCurrentCallback())
                this.screen.getWorkspace().getCurrentSection().getCurrentCallback().handleElementClick(this.screen.getWorkspace().getCurrentSection(), this, mouseButton);
        }
        return flag;
    }

    public int getInnerBoxSize() {
        return this.innerBoxSize;
    }

    public GUICheckBoxButton setInnerBoxSize(int value) {
        this.innerBoxSize = value;
        return this;
    }

    public int getEnabledInnerBoxColor() {
        return this.enabledInnerBoxColor;
    }

    public GUICheckBoxButton setEnabledInnerBoxColor(int colorHex) {
        this.enabledInnerBoxColor = colorHex;
        return this;
    }

    public int getDisabledInnerBoxColor() {
        return this.disabledInnerBoxColor;
    }

    public GUICheckBoxButton setDisabledInnerBoxColor(int colorHex) {
        this.disabledInnerBoxColor = colorHex;
        return this;
    }

    public int getHoveredInnerBoxColor() {
        return this.hoveredInnerBoxColor;
    }

    public GUICheckBoxButton setHoveredInnerBoxColor(int colorHex) {
        this.hoveredInnerBoxColor = colorHex;
        return this;
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
    public GUICheckBoxButton setTexture(ResourceLocation texture, int textureWidth, int textureHeight) {
        this.setTexture(texture);
        this.setTextureSize(textureWidth, textureHeight);
        this.setImageSize(textureWidth * 6, textureHeight);
        return this;
    }
}
