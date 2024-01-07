package com.example;

import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class Waypoint {
    private String name;
    private BlockPos position;

    public Waypoint(String name, BlockPos position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public BlockPos getPosition() {
        return position;
    }
}

