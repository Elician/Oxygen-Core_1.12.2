package austeretony.alternateui.screen.core;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;


public class GUIAdvancedElement<T extends GUISimpleElement> extends GUISimpleElement<T> {

    private ResourceLocation texture;

    private int textureWidth, textureHeight, textureU, textureV, imageWidth, imageHeight, textureOffsetX, textureOffsetY;

    private boolean isTextureEnabled;

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        if (this.isVisible()) {        	
            GlStateManager.pushMatrix();           
            GlStateManager.translate(this.getX(), this.getY(), 0.0F);           
            GlStateManager.scale(this.getScale(), this.getScale(), 0.0F);    
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (this.isTextureEnabled()) {  
                int u = this.getTextureU();        		
                this.mc.getTextureManager().bindTexture(this.getTexture());
                if (!this.isEnabled())                  
                    u = this.getTextureWidth();   
                else if (this.isHovered() || this.isToggled())              	
                    u += this.getTextureWidth() * 2;      
                GlStateManager.enableBlend(); 
                drawCustomSizedTexturedRect((this.getWidth() - this.getTextureWidth()) / 2, 
                        (this.getHeight() - this.getTextureHeight()) / 2, u, this.getTextureV(), this.getTextureWidth(), 
                        this.getTextureHeight(), this.getImageWidth(), this.getImageHeight());        	
                GlStateManager.disableBlend(); 
            }       	             
            GlStateManager.popMatrix();
        }
    }

    public ResourceLocation getTexture() {   	
        return this.texture;
    }


    public T setTexture(ResourceLocation texture, int textureWidth, int textureHeight, int u, int v, int imageWidth, int imageHeight) {   	  	
        this.setTexture(texture, textureWidth, textureHeight, imageWidth, imageHeight);
        this.textureU = u;
        this.textureV = v;    	 	
        return (T) this;
    }


    public T setTexture(ResourceLocation texture, int textureWidth, int textureHeight, int imageWidth, int imageHeight) {   
        this.setTexture(texture, textureWidth, textureHeight);
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;   	
        return (T) this;
    }


    public T setTexture(ResourceLocation texture, int textureWidth, int textureHeight) {   	
        this.setTexture(texture);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;   	
        this.imageWidth = textureWidth;
        this.imageHeight = textureHeight;    	
        this.enableTexture();   	
        return (T) this;
    }


    public T setTexture(ResourceLocation texture) {    	
        this.texture = texture;   	
        this.enableTexture();   	
        return (T) this;
    }  

    public int getTextureWidth() {  	
        return this.textureWidth;
    }

    public int getTextureHeight() {  	
        return this.textureHeight;
    }

    /**
     * ?????????????????? ?????????????? ????????????????.
     * 
     * @param textureWidth
     * @param textureHeight
     * 
     * @return ???????????????????? ????????????
     */
    public T setTextureSize(int textureWidth, int textureHeight) {   	
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        return (T) this;
    }

    public int getTextureU() {   	
        return this.textureU;
    }

    public int getTextureV() {    	
        return this.textureV;
    }

    /**
     * ?????????????????? UV ?????????????????? ?????? ????????????????.
     * 
     * @param u
     * @param v
     * 
     * @return ???????????????????? ????????????
     */
    public T setTextureUV(int u, int v) {   	
        this.textureU = u;
        this.textureV = v;

        return (T) this;
    }

    public int getImageWidth() {  	
        return this.imageWidth;
    }

    public int getImageHeight() {   	
        return this.imageHeight;
    }

    /**
     * ?????????????????? ?????????????? ?????????????????????? ?? ??????????????????.
     * 
     * @param width
     * @param height
     * 
     * @return ???????????????????? ????????????
     */
    public T setImageSize(int width, int height) {   	
        this.imageWidth = width;
        this.imageHeight = height;

        return (T) this;
    }

    public int getTextureOffsetX() {    	
        return this.textureOffsetX;
    }

    public int getTextureOffsetY() {   	
        return this.textureOffsetY;
    }

    /**
     * ???????????????????????? ???????????? ?????? ???????????????? (???? ???????????? ???????????????? ???????? ????????????????).
     * 
     * @param xOffset ???????????? ???? ??????????????????????
     * @param yOffset ???????????? ???? ??????????????????
     * 
     * @return ???????????????????? ????????????
     */
    public T setTextureOffset(int offsetX, int offsetY) {		
        this.textureOffsetX = offsetX;
        this.textureOffsetY = offsetY;		
        return (T) this;
    }

    public boolean isTextureEnabled() {   	
        return this.isTextureEnabled;
    }


    public T enableTexture() {   	
        this.isTextureEnabled = true;   	
        return (T) this;
    }

    public static void drawCustomSizedTexturedRect(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {  	
        float 
		f = 1.0F / (float) textureWidth,
        f1 = 1.0F / (float) textureHeight;        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);    
        bufferbuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) width) * f), (double) (v * f1)).endVertex();
        bufferbuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();    
        tessellator.draw();
    }
}

