package com.example;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
//math random
import java.lang.Math;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.example.Storage;

public class CommandeGem extends CommandBase{


    private static Storage store;

    public CommandeGem(Storage storage){
        store = storage;
    }

    ArrayList<BlockPos> arrayBlockPostion = new ArrayList<BlockPos>();

    public static int tickcount = 0;


    public static int waintingtick = 0;
    public static boolean waintingbool = false;


    private static Minecraft minecraft = Minecraft.getMinecraft();
    private PlayerControllerMP playerControllerMP = minecraft.playerController;

    private static Map<BlockPos, Boolean> breakingBlocks = new HashMap<>();

    boolean startBreaking = false;


    private static int displayTime = 0;
    private static String displayMessage = "";
    
    




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

    static Block  blocktocheck = Blocks.stained_glass;

    static Block blocktocheck2 = Blocks.stained_glass_pane;

    Block blockplayer = null;

    int indexPos = 0;

    public static boolean running = false;


    public static boolean checkedposition1 = false;
    public static boolean checkedposition2 = false;

    float range = 5.0f;

    static boolean next = false;

    public static ArrayList<BlockPos> arrayOfPosition = new ArrayList<BlockPos>();

    private static boolean findInArray(BlockPos blockPos, ArrayDeque<BlockPos> arrayblock){
        for(BlockPos blockPos1 : arrayblock){
            if(blockPos1.getX() == blockPos.getX() && blockPos1.getY() == blockPos.getY() && blockPos1.getZ() == blockPos.getZ()){
                return true;
            }
        }
        return false;
    }


    @Override
    public String getCommandName() {
        return "gem";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/gem";
    }


    @Override
    public void processCommand(ICommandSender sender, String[] args){

        if (sender instanceof EntityPlayer) {
            if(args.length < 1){
                return;
            }
            arrayblock.clear();
            breakingBlocks.clear();

            EntityPlayer player = (EntityPlayer) sender;
            EntityPlayerSP player2 = Minecraft.getMinecraft().thePlayer;

            String liste = args[0];

            if(liste.equals("stop")){
                System.out.println("arg == stop");
                running = false;
                player2.setSneaking(false);
                KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindSneak.getKeyCode(), false);
                return;
            }

            store.remplirArrayBlock(liste);

            double playerX = player.posX;
            double playerY = player.posY;
            double playerZ = player.posZ;

            int playerXint = (int) playerX;
            int playerYint = (int) playerY - 1;
            int playerZint = (int) playerZ;

            System.out.println(playerXint + " " + playerYint + " " + playerZint);

            blockPosplayer = new BlockPos(playerXint, playerYint, playerZint);

            indexPos = playerIndexBlock(blockPosplayer);
            System.out.println("index start : " + indexPos);

            if(indexPos == -1){
                System.out.println("index == -1 (player pas sur un block de la liste");
                return;
            }
            else{
                System.out.println("start");
                //setSneak

                player2.setSneaking(true);
                KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindSneak.getKeyCode(), true);
                running = true;
                System.out.println("jksdlqmmmmmmmmmmmmmmmmmmmmmmmmmmmmmqkljdfkqjdklzqjdklzqkldjkzdkjk     :       " + running);
                checkedposition1 = false;
                checkedposition2 = false;
                next = false;
            }
        }
    }


    public static int playerIndexBlock(BlockPos blockPosplayer){
        int index = 0;
        System.out.println(store.arrayBlockPostion);
        System.out.println(blockPosplayer);
        for(BlockPos blockPos : store.arrayBlockPostion){
            if(blockPos.getX() == blockPosplayer.getX() && blockPos.getY() == blockPosplayer.getY() && blockPos.getZ() == blockPosplayer.getZ()){
                return index;
            }
            index++;
        }
        return -1;
    }


    public static void find(EntityPlayer player, int positioncheck){
        int playerX = (int) player.posX;
        int playerY = (int) player.posY;
        int playerZ = (int) player.posZ;


        int temoinc;
        int temoinb;
        int temoina = -3;

        for(int a = 0; a < 7; a++) {
            temoinb = -3;
            for (int b = 0; b < 7; b++) {
                temoinc = -2;
                for (int c = 0; c < 5; c++) {

                    boolean temoinOfBoucle = false;




                    switch (c){
                        case 0:
                            temoinc = -2;
                            break;
                        case 1:
                            temoinc = 2;
                            break;
                        case 2:
                            temoinc = -1;
                            break;
                        case 3:
                            temoinc = 0;
                            break;
                        case 4:
                            temoinc = 1;
                            break;
                    }


                    if(temoina == 0 && temoinb == 0 && temoinc == -1){
                        temoinOfBoucle = true;
                    }
                    if (temoina == 0 && temoinb == 0  && temoinc == -2) {
                        temoinOfBoucle = true;
                    }
                    if ((temoina == -1 || temoina == 0 || temoina == 1) && (temoinb == -1 || temoinb == 0 || temoinb == 1)  && temoinc == -2) {
                        temoinOfBoucle = true;
                    }

                    if (!temoinOfBoucle){



                        int coordX = playerX + temoina;
                        int coordY = playerY + temoinc;
                        int coordZ = playerZ + temoinb;

                        BlockPos pos = new BlockPos(coordX, coordY, coordZ);
                        IBlockState blockState = player.worldObj.getBlockState(pos);

                        Block block = blockState.getBlock();
                        if(block == blocktocheck || block == blocktocheck2 ) {
                            if (!findInArray(pos, arrayblock)) {
                                System.out.println("block trouve");
                                System.out.println(coordX + " " + coordY + " " + coordZ);
                                // mettre sur la pille
                                arrayblock.addFirst(pos);
                            }

                        }
                    }

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
        if (positioncheck == 1) {
            checkedposition1 = true;
        }
        if (positioncheck == 2) {
            if (arrayblock.isEmpty()) {
                checkedposition2 = true;
                next = true;
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
            if (waintingbool){
                waintingtick++;
                if (waintingtick == 30){
                    int slotIndex = 1; // Le deuxième emplacement a l'indice 1 (les indices commencent à partir de 0)
                    minecraft.thePlayer.inventory.currentItem = slotIndex;


                }
                if (waintingtick == 40 ){
                    minecraft.playerController.sendUseItem(minecraft.thePlayer, minecraft.theWorld, minecraft.thePlayer.inventory.getCurrentItem());




                }
                if (waintingtick == 50 ){
                    minecraft.thePlayer.inventory.currentItem = 0;

                }
                if (waintingtick >= 90 ){
                    waintingtick = 0;
                    waintingbool = false;
                }
            }
            else{
                if(running){
                    if (!next){
                        if(!checkedposition1){
                            find(minecraft.thePlayer, 1);
                            System.out.println("arrayblock 1 : " + arrayblock);

                        }
                        if(!checkedposition2 && arrayblock.isEmpty()){
                            find(minecraft.thePlayer, 2);
                            System.out.println("arrayblock 2 : " + arrayblock);
                            System.out.println("sizeindexPos : " + indexPos);
                            System.out.println("arrayBlockPostion.size() : " + store.arrayBlockPostion.size());
                        }
                    }
                    else{
                        int playerX = (int) minecraft.thePlayer.posX;
                        int playerY = (int) minecraft.thePlayer.posY - 1;
                        int playerZ = (int) minecraft.thePlayer.posZ;

                        blockPosplayer = new BlockPos(playerX, playerY, playerZ);

                        indexPos = playerIndexBlock(blockPosplayer);
                        if (indexPos == -1){
                            System.out.println("index == -1 (player pas sur un block de la liste");
                            return;
                        }
                        System.out.println("index  pos befre : " + indexPos);
                        if (indexPos >= store.arrayBlockPostion.size()-1){
                            indexPos = 0;
                        }
                        else {
                            indexPos++;
                        }
                        next = false;
                        checkedposition1 = false;
                        checkedposition2 = false;

                        lookAt2(store.arrayBlockPostion.get(indexPos).getX()+  0.5 , store.arrayBlockPostion.get(indexPos).getY() + 0.5 , store.arrayBlockPostion.get(indexPos).getZ() + 0.5 , minecraft.thePlayer, true);
                        waintingbool = true;
                        waintingtick = 0;
                    }



                }

            }


            //message tick

            if(!arrayblock.isEmpty()) {
                //prendre le premiere block de la pille
                BlockPos blockPos = arrayblock.getFirst();






                if(!breakingBlocks.containsKey(blockPos)){
                    // Regardez le bloc




                    lookAt2(blockPos.getX()+  0.5 , blockPos.getY() + 0.5 , blockPos.getZ() + 0.5 , minecraft.thePlayer, true);

/*

                    if (tickcount > 5){
                        lookAt2(blockPos.getX()+  0.5 , blockPos.getY() + 0.5 , blockPos.getZ() + 0.5 , minecraft.thePlayer, true);
                    }
                    else {
                        double randomOffsetX = (Math.random() * 0.4) - 0.2;

                        double randomOffsetY = (Math.random() * 0.4) - 0.2;

                        double randomOffsetZ = (Math.random() * 0.4) - 0.2;
                        lookAt2(blockPos.getX()+  0.5 + randomOffsetX, blockPos.getY() + 0.5 + randomOffsetY, blockPos.getZ() + 0.5 + randomOffsetZ, minecraft.thePlayer, false);
                    }

 */

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
