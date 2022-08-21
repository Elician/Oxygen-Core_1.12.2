package austeretony.oxygen_core.server.event;

import austeretony.oxygen_core.common.main.OxygenMain;
import austeretony.oxygen_core.server.OxygenManagerServer;
import austeretony.oxygen_core.server.api.CurrencyHelperServer;
import austeretony.oxygen_core.server.api.event.OxygenPrivilegesLoadedEvent;
import austeretony.oxygen_core.server.currency.CurrencyProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class OxygenEventsServer {

    @SubscribeEvent
    public void onPrivilegesLoaded(OxygenPrivilegesLoadedEvent event) {
        OxygenMain.addDefaultPrivileges();
    }

    @SubscribeEvent
    public void onPlayerLogIn(PlayerLoggedInEvent event) {        
        OxygenManagerServer.instance().playerLoggedIn((EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void onPlayerLogOut(PlayerLoggedOutEvent event) {
        OxygenManagerServer.instance().playerLoggedOut((EntityPlayerMP) event.player);
    }
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event){
        MinecraftServer server=FMLCommonHandler.instance().getMinecraftServerInstance().getServer();
        World w=server.worlds[0];
        if(w.getWorldTime()%20==0){
            for(EntityPlayer pl:server.getPlayerList().getPlayers()){
                CurrencyHelperServer.setCurrency(pl.getUniqueID(), CurrencyHelperServer.getCurrency(pl.getUniqueID(),0), 0);
            }
        }
    }
    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
        OxygenManagerServer.instance().playerChangedDimension((EntityPlayerMP) event.player, event.fromDim, event.toDim);
    }

    @SubscribeEvent
    public void onPlayerStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof EntityPlayer)//TODO 0.10 - make it applicable for all EntityLivingBase when an Damage Overlay module will be created
            OxygenManagerServer.instance().playerStartTracking((EntityPlayerMP) event.getEntityPlayer(), event.getTarget());
    }

    @SubscribeEvent
    public void onPlayerStopTracking(PlayerEvent.StopTracking event) {
        if (event.getTarget() instanceof EntityPlayer)//TODO 0.10 - make it applicable for all EntityLivingBase when an Damage Overlay module will be created
            OxygenManagerServer.instance().playerStopTracking((EntityPlayerMP) event.getEntityPlayer(), event.getTarget());
    }
}
