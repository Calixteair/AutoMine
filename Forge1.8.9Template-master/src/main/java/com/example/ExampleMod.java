package com.example;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.ClientCommandHandler;
import com.example.CommandeFind;
import com.example.CommandeGem;
import com.example.Storage;



@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod {
    public static final String MODID = "testmod";
    public static final String VERSION = "1.2";

    public static Storage store = new Storage();

    public static WaypointRenderer waypointRenderer = new WaypointRenderer(store);

    public static CustomGuiIngame customGuiIngame = new CustomGuiIngame(Minecraft.getMinecraft(), waypointRenderer);




    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Enregistrez votre commande lors de l'initialisation
        ClientCommandHandler.instance.registerCommand(new com.example.CommandeFind(store));
        ClientCommandHandler.instance.registerCommand(new com.example.CommandeGem(store));
        ClientCommandHandler.instance.registerCommand(new com.example.CommandWaypoint(store));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(new CommandeFind(store));
        MinecraftForge.EVENT_BUS.register(new CommandeGem(store));
        MinecraftForge.EVENT_BUS.register(new CommandWaypoint(store));
        MinecraftForge.EVENT_BUS.register(new WaypointRenderer(store));
        MinecraftForge.EVENT_BUS.register(this);

    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        // Appeler la méthode renderWaypoints pour afficher les waypoints
        waypointRenderer.renderWaypoints();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // D'autres post-initialisations
    }
}
