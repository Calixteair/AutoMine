package com.example;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import com.example.Storage;

public class WaypointRenderer extends Gui {

    private final Storage store;
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fontRenderer = mc.fontRendererObj;
    private final ResourceLocation waypointIcon = new ResourceLocation("textures/items/compass.png");

    WaypointRenderer(Storage storage) {
        this.store = storage;
    }

    public void renderWaypoints() {



        for (Waypoint waypoint : store.arrayWaypoint) {
            BlockPos pos = waypoint.getPosition();
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            int color = 0x00FF00;
            String name = waypoint.getName();
            drawWaypoint(x, y, z, color, name);
        }
    }

    public void drawWaypoint(int x, int y, int z, int color, String name) {
        // Sauvegarde la matrice actuelle
        GlStateManager.pushMatrix();

        // Translate, rotate et scale pour positionner correctement le waypoint
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, 1, 0, 0);
        GlStateManager.scale(-0.025, -0.025, 0.025);

        // Désactive l'éclairage, le masque de profondeur et active le mélange
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        // Active la texture et dessine la texture du waypoint
        GlStateManager.enableTexture2D();
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(waypointIcon);
        drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 16, 16, 16, 16);

        // Réactive la profondeur, l'éclairage, et désactive le mélange
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();

        // Restore la matrice précédente
        GlStateManager.popMatrix();

        // Nouvelle sauvegarde de la matrice pour afficher le texte
        GlStateManager.pushMatrix();

        // Translate, rotate et scale pour positionner correctement le texte
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, 1, 0, 0);
        GlStateManager.scale(-0.025, -0.025, 0.025);

        // Désactive l'éclairage, le masque de profondeur et active le mélange
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        // Active la texture
        GlStateManager.enableTexture2D();
        GlStateManager.color(1, 1, 1, 1);

        // Dessine le texte (décommentez la ligne suivante si vous souhaitez utiliser fontRenderer)
        // fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, 10, color);

        // Réactive la profondeur, l'éclairage, et désactive le mélange
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();

        // Restore la matrice précédente
        GlStateManager.popMatrix();
    }





}
