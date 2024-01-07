package com.example;


import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandWaypoint extends CommandBase {
    private final Storage store;


    public CommandWaypoint(Storage storage) {
        this.store = storage;

    }

    @Override
    public String getCommandName() {
        return "waypointcal";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/waypointcal";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        System.out.println("CommandWaypoint.processCommand");

        if (sender instanceof EntityPlayer){
            if (args.length > 0) {
                String action = args[0];

                switch (action) {
                    case "add":
                        if (args.length >= 1) {
                            String position = args[1];
                            String id;
                            if (args.length >= 2) {
                                id = args[2];
                            }
                            else {
                                id = "0";
                            }
                            //position = "x,y,z";
                            String[] parts = position.split(",");
                            int x = Integer.parseInt(parts[0]);
                            int y = Integer.parseInt(parts[1]);
                            int z = Integer.parseInt(parts[2]);
                            BlockPos pos = new BlockPos(x, y, z);
                            if (id.equals("0")) {
                                id = store.arrayWaypoint.size() + "";
                                Waypoint waypoint = new Waypoint(id, pos);
                                store.addWaypoint(waypoint);
                            }
                            else {
                                store.addWaypointAtIndex(Integer.parseInt(id), new Waypoint(id, pos));
                            }
                        }
                        break;

                    case "remove":
                        if (args.length >= 1) {
                            String id = args[1];
                            store.removeWaypoint(id);
                        }
                        break;

                    case "liste":
                        String list = args[2];
                        String[] parts = list.split(";");
                        int id = 0;
                        for (String part : parts) {
                            String[] coords = part.split(",");
                            if (coords.length == 3) {
                                int x = Integer.parseInt(coords[0]);
                                int y = Integer.parseInt(coords[1]);
                                int z = Integer.parseInt(coords[2]);
                                BlockPos pos = new BlockPos(x, y, z);
                                String idString = id + "";
                                Waypoint waypoint = new Waypoint(idString, pos);
                                store.addWaypoint(waypoint);
                                id++;
                            }
                        }
                        break;

                    default:
                        System.out.println("Unknown action: " + action);
                        break;
                }
            }

        }

    }
}