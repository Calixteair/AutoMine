package com.example;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;

public class CustomGuiIngame extends GuiIngame {

    private WaypointRenderer waypointRenderer;

    public CustomGuiIngame(Minecraft mcIn, WaypointRenderer waypointRenderer) {
        super(mcIn);
        this.waypointRenderer = waypointRenderer;
    }

    @Override
    public void renderGameOverlay(float partialTicks) {
        super.renderGameOverlay(partialTicks);

        // Appeler la méthode renderWaypoints pour afficher les waypoints
        waypointRenderer.renderWaypoints();
    }
}