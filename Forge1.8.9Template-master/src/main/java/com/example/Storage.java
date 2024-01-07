package com.example;

import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;



public class Storage {



    public ArrayList<BlockPos> arrayBlockPostion = new ArrayList<BlockPos>();

    public ArrayList<Waypoint> arrayWaypoint = new ArrayList<Waypoint>();


    public void addWaypoint(Waypoint waypoint){
        arrayWaypoint.add(waypoint);
    }

    public void addWaypointAtIndex(int index, Waypoint waypoint){
        arrayWaypoint.add(index, waypoint);
    }

    public void removeWaypoint(String index){
        int indexInt = Integer.parseInt(index);
        arrayWaypoint.remove(indexInt);
    }

    public void addBlock(BlockPos blockPos){
        arrayBlockPostion.add(blockPos);
    }


    public void removeBlock(BlockPos blockPos){
        arrayBlockPostion.remove(blockPos);
    }

    public void remplirArrayBlock(String liste) {

        System.out.println("liste : " + liste);
        arrayBlockPostion.clear(); // Assurez-vous que l'ArrayList est vide au début

        // Séparez les positions par des points-virgules
        String[] positions = liste.split(";");

        // Parcourez chaque position et ajoutez-la à l'ArrayList
        for (String position : positions) {
            System.out.println("position : " + position);
            String[] coords = position.split(",");
            if (coords.length == 3) {
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                int z = Integer.parseInt(coords[2]);
                BlockPos pos = new BlockPos(x, y, z);
                arrayBlockPostion.add(pos);
                int id = arrayBlockPostion.size() - 1;
                String idString = id + "";
            }
        }
    }




}
