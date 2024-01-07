package com.example;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
//math random
import java.lang.Math;
import java.util.ArrayDeque;
import com.example.Storage;


import java.util.*;

public class CommandeFind extends CommandBase {

    public static Storage store;

    public CommandeFind(Storage store){

        this.store = store;
    }

    public static int tickcount = 0;


    private Minecraft minecraft = Minecraft.getMinecraft();
    private PlayerControllerMP playerControllerMP = minecraft.playerController;

    private static Map<BlockPos, Boolean> breakingBlocks = new HashMap<>();

    boolean startBreaking = false;




    //fille
    private static ArrayDeque<BlockPos> arrayblock = new ArrayDeque<BlockPos>();

    //private static ArrayList<BlockPos> arrayblock = new ArrayList<BlockPos>();

    BlockPos blockPosplayer = null;
    private static final Vec3[] BLOCK_SIDE_MULTIPLIERS = new Vec3[]{
            new Vec3(0.5, 0, 0.5), // Down
            new Vec3(0.5, 1, 0.5), // Up
            new Vec3(0.5, 0.5, 0), // North
            new Vec3(0.5, 0.5, 1), // South
            new Vec3(0, 0.5, 0.5), // West
            new Vec3(1, 0.5, 0.5)  // East
    };

    static Block  blocktocheck = net.minecraft.init.Blocks.log;

    static Block blocktocheck2 = net.minecraft.init.Blocks.stained_glass_pane;

    Block blockplayer = null;
    static int metadata = 14;

    float range = 5.0f;

    private static boolean findInArray(BlockPos blockPos, ArrayDeque<BlockPos> arrayblock){
        for(BlockPos blockPos1 : arrayblock){
            if(blockPos1.getX() == blockPos.getX() && blockPos1.getY() == blockPos.getY() && blockPos1.getZ() == blockPos.getZ()){
                return true;
            }
        }
        return false;
    }





    //mc


    @Override
    public String getCommandName() {
        return "find";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/find";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        // Vérifier si l'émetteur est un joueur

            // Logique de la commande

            if (sender instanceof EntityPlayer) {
                arrayblock.clear();
                breakingBlocks.clear();

                EntityPlayer player = (EntityPlayer) sender;

                // Récupérer les coordonnées du joueur
                int playerX = (int) player.posX;
                int playerY = (int) player.posY;
                int playerZ = (int) player.posZ;


                int temoinc = -2;
                int temoinb = -3;
                int temoina = -3;

               for(int a = 0; a < 7; a++) {
                   for (int b = 0; b < 7; b++) {
                       for (int c = 0; c < 5; c++) {


                           if(temoina == 0 && temoinb == 0 && temoinc == -1){
                               continue;
                           }
                           if (temoina == 0 && temoinb == 0  && temoinc == -2) {
                               continue;
                           }
                           if ((temoina == -1 || temoina == 0 || temoina == 1) && (temoinb == -1 || temoinb == 0 || temoinb == 1)  && temoinc == -2) {
                               continue;
                           }

                           int coordX = playerX + temoina;
                            int coordY = playerY + temoinc;
                            int coordZ = playerZ + temoinb;

                            BlockPos pos = new BlockPos(coordX, coordY, coordZ);
                            IBlockState blockState = player.worldObj.getBlockState(pos);

                            Block block = blockState.getBlock();
                            int metadata = block.getMetaFromState(blockState);
                            if(block == blocktocheck || (block == blocktocheck2 && metadata == 14)) {
                                if (!findInArray(pos, arrayblock)) {
                                    System.out.println("block trouve");
                                    System.out.println(coordX + " " + coordY + " " + coordZ);
                                    // mettre sur la pille
                                    arrayblock.addFirst(pos);
                                }

                            }

                           if(c%2 == 1){
                               temoinc =temoinc -1;
                           }
                           temoinc = -temoinc;

                       }
                       if(b%2 == 1){
                           temoinb =temoinb -1;
                       }
                          temoinb = -temoinb;

                   }
                   if(a%2 == 1){
                      temoina =temoina -1;
                   }
                   temoina = -temoina;
               }
        }
    }

    public static void lookAt2(double px , double py, double pz , EntityPlayer me , boolean prefect)
    {
        tickcount++;
        double dirx = px - me.posX;
        double diry = me.posY+ me.getEyeHeight() - py;
        double dirz = pz - me.posZ;

        double len = Math.sqrt(dirx*dirx + diry*diry + dirz*dirz);

        diry /= len;

        double pitch = Math.asin(diry);
        float yaw = (float) -Math.atan2(dirx, dirz) / (float) Math.PI * 180;;

        //to degree
        pitch = pitch * 180.0 / Math.PI;

        float yawStart = me.rotationYaw;
        float pitchStart = me.rotationPitch;

        float yawEnd = yaw;
        float pitchEnd = (float) pitch;














        //float interpolatedYaw = oneMinusT * (oneMinusT * (oneMinusT * yawStart + 3 * t * yawMid1) + 3 * t * (oneMinusT * yawMid2 + t * yawEnd));
        //float interpolatedPitch = oneMinusT * (oneMinusT * (oneMinusT * pitchStart + 3 * t * pitchMid1) + 3 * t * (oneMinusT * pitchMid2 + t * pitchEnd));










        // Utilisation de ReflectionHelper pour définir les champs privés
        me.rotationYaw = yaw;
        me.rotationPitch = (float) pitch;
        //me.rotationYawHead = yaw;
        // update entity
        me.setPositionAndUpdate(me.posX, me.posY, me.posZ);

    }

    private void mouseClick() {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft.gameSettings.keyBindAttack.isKeyDown()) { // if 'mouse 1' is being pressed down
            minecraft.thePlayer.swingItem(); // just the visual part
            switch (minecraft.objectMouseOver.typeOfHit) {
                case ENTITY: // attack the entity internally
                    minecraft.playerController.attackEntity(minecraft.thePlayer, minecraft.objectMouseOver.entityHit);
                    break;
                case BLOCK: // don't spam attack the block, break it instead
                    minecraft.playerController.clickBlock(minecraft.objectMouseOver.getBlockPos(), minecraft.objectMouseOver.sideHit);
                    break;
            }
        }
    }


    public EnumFacing faceOfBlock(BlockPos blockPos, EntityPlayer player){
        //get the block
        IBlockState blockState = player.worldObj.getBlockState(blockPos);
        Block block = blockState.getBlock();
        int metadata = block.getMetaFromState(blockState);

        //get the block side
        EnumFacing side = EnumFacing.DOWN;
        for (int i = 0; i < 6; i++) {
            Vec3 vec = BLOCK_SIDE_MULTIPLIERS[i];
            BlockPos offset = blockPos.add(vec.xCoord, vec.yCoord, vec.zCoord);
            if (offset.equals(blockPos)) {
                side = EnumFacing.getFront(i);
                break;
            }
        }
        return side;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        // Permettre à tous les joueurs d'utiliser la commande
        return true;
    }


    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            //message tick

            if(!arrayblock.isEmpty()) {
                //prendre le premiere block de la pille
                BlockPos blockPos = arrayblock.getFirst();






                if(!breakingBlocks.containsKey(blockPos)){
                    // Regardez le bloc








                    if (tickcount > 5){
                        lookAt2(blockPos.getX()+  0.5 , blockPos.getY() + 0.5 , blockPos.getZ() + 0.5 , minecraft.thePlayer, true);
                    }
                    else {
                        double randomOffsetX = (Math.random() * 0.4) - 0.2;

                        double randomOffsetY = (Math.random() * 0.4) - 0.2;

                        double randomOffsetZ = (Math.random() * 0.4) - 0.2;
                        lookAt2(blockPos.getX()+  0.5 + randomOffsetX, blockPos.getY() + 0.5 + randomOffsetY, blockPos.getZ() + 0.5 + randomOffsetZ, minecraft.thePlayer, false);
                    }


                    // Trouver la face du bloc la plus proche du joueur

                    //keybind attack


                    // casse le block et gerer le visuel

                    EnumFacing face = faceOfBlock(blockPos, minecraft.thePlayer);

                    //recuperer le block que le joueur regarde
                    Vec3 playerPos = new Vec3(minecraft.thePlayer.posX, minecraft.thePlayer.posY + minecraft.thePlayer.getEyeHeight(), minecraft.thePlayer.posZ);

                    // Obtenez la direction vers laquelle le joueur regarde
                    Vec3 lookDir = minecraft.thePlayer.getLook(1.0f);
                    Vec3 end = playerPos.addVector(lookDir.xCoord * range, lookDir.yCoord * range, lookDir.zCoord * range);

                    // Effectuez le rayon et obtenez le résultat
                    MovingObjectPosition target = minecraft.theWorld.rayTraceBlocks(playerPos, end);

                    // Vérifiez si le résultat est un bloc
                    if (target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                        blockPosplayer = target.getBlockPos();
                        IBlockState blockState = minecraft.theWorld.getBlockState(blockPosplayer);
                        blockplayer = blockState.getBlock();
                    }

                    System.out.println(blockPosplayer);
                    System.out.println(blockplayer);
                    System.out.println(blockPos);









                    if(blockPosplayer != null && blockplayer != null && blockPosplayer.getX() == blockPos.getX() && blockPosplayer.getY() == blockPos.getY() && blockPosplayer.getZ() == blockPos.getZ()) {

                        breakingBlocks.put(blockPos, true);
                        minecraft.gameSettings.keyBindAttack.setKeyBindState(minecraft.gameSettings.keyBindAttack.getKeyCode(),true);
                        // Cassez le block
                        System.out.println("start breaking");
                        minecraft.playerController.clickBlock(blockPos, face);
                        tickcount = 0;
                        startBreaking = true;
                    }
                    else {
                        //message fin
                        // Si le bloc n'est plus là, arrêtez de casser et retirez-le de la liste

                        if(tickcount > 10){
                            System.out.println("add block to array");
                            // Si le bloc n'est plus là, arrêtez de casser et retirez-le de la liste
                            //retirer le premeir block de la pille
                            if (!findInArray(blockPosplayer, arrayblock)) {

                                //ajouter le block a la pille
                                arrayblock.addFirst(blockPosplayer);

                                breakingBlocks.remove(blockPos);
                                startBreaking = false;
                                System.out.println("block add " + arrayblock);
                                System.out.println(breakingBlocks);

                                minecraft.gameSettings.keyBindAttack.setKeyBindState(minecraft.gameSettings.keyBindAttack.getKeyCode(), false);
                            }
                            tickcount = 0;



                        }




                        //si il n'est pas dans la encore dans la liste


                    }
                }
                if (startBreaking) {
                    minecraft.playerController.onPlayerDamageBlock(blockPos, faceOfBlock(blockPos, minecraft.thePlayer));
                    tickcount = 0;
                }

                // egale a air ou bedrock
                if (minecraft.theWorld.getBlockState(blockPos).getBlock() == net.minecraft.init.Blocks.air || minecraft.theWorld.getBlockState(blockPos).getBlock() == net.minecraft.init.Blocks.bedrock) {
                    //message fin
                    System.out.println("fin breaking");
                    // Si le bloc n'est plus là, arrêtez de casser et retirez-le de la liste
                    //retirer le premeir block de la pille
                    arrayblock.removeFirst();

                    breakingBlocks.remove(blockPos);
                    startBreaking = false;
                    tickcount = 0;
                    System.out.println(arrayblock);
                    System.out.println(breakingBlocks);
                    minecraft.gameSettings.keyBindAttack.setKeyBindState(minecraft.gameSettings.keyBindAttack.getKeyCode(), false);
                }
            }
        }
    }
}
