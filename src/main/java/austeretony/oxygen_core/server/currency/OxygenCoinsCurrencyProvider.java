package austeretony.oxygen_core.server.currency;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;

import austeretony.oxygen_core.common.main.OxygenMain;
import austeretony.oxygen_core.server.api.OxygenHelperServer;
import me.yic.api.XConomyAPI;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class OxygenCoinsCurrencyProvider implements CurrencyProvider {

    @Override
    public String getDisplayName() {
        return "Oxygen Coins";
    }

    @Override
    public int getIndex() {
        return OxygenMain.COMMON_CURRENCY_INDEX;
    }

    @Override
    public boolean forceSync() {
        return false;
    }

    @Override
    public long getCurrency(UUID playerUUID) {
        XConomyAPI xcapi = new XConomyAPI();
        return xcapi.getbalance(playerUUID).longValue();
    }
    @Override
    public void setCurrency(UUID playerUUID, long value) {
        try {
            XConomyAPI xcapi = new XConomyAPI();
            if (value > xcapi.getbalance(playerUUID).longValue())
                xcapi.changebalance(playerUUID, "", BigDecimal.valueOf(value - xcapi.getbalance(playerUUID).longValue()), true);
            else
                xcapi.changebalance(playerUUID, "", BigDecimal.valueOf(xcapi.getbalance(playerUUID).longValue() - value), false);
            OxygenHelperServer.getOxygenPlayerData(playerUUID).setCurrency(OxygenMain.COMMON_CURRENCY_INDEX, value);
        }catch (Exception e){
                System.out.println("Error with setting "+value+" currency to player with UUID "+playerUUID);
        }
    }
//    @Override
//    public void setCurrency(UUID playerUUID, long value) {
//        try {
//            XConomyAPI xcapi = new XConomyAPI();
//            EntityPlayerMP playerMP = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerUUID);
//            if (playerMP == null) return;
//            String name = playerMP.getName();
//            if (value > xcapi.getbalance(playerUUID).longValue())
//                xcapi.changebalance(playerUUID, name, BigDecimal.valueOf(value - xcapi.getbalance(playerUUID).longValue()), true);
//            else
//                xcapi.changebalance(playerUUID, name, BigDecimal.valueOf(xcapi.getbalance(playerUUID).longValue() - value), false);
//            OxygenHelperServer.getOxygenPlayerData(playerUUID).setCurrency(OxygenMain.COMMON_CURRENCY_INDEX, value);
//        }catch (Exception e){
//            try {
//                EntityPlayerMP playerMP = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerUUID);
//                if (playerMP == null) return;
//                Calendar calendar=Calendar.getInstance();
//                playerMP.sendMessage(new TextComponentString("Ошибка при установке "+value+" денег игроку "+
//                        playerMP.getDisplayName()+". Дата: " + calendar.get(Calendar.DATE)  + "."+
//                        calendar.get(Calendar.MONTH) + "."+  calendar.get(Calendar.YEAR)+
//                        ". Обратитесь к администрации со скриншотом этого сообщения"));
//            }catch (Exception e2){
//                System.out.println("Error with setting "+value+" currency to player with UUID "+playerUUID);
//            }
//        }
//    }
//oxygen_core:oxygenop core -currency -add Goodbird 0 1000
    @Override
    public void updated(UUID playerUUID) {
        OxygenHelperServer.getOxygenPlayerData(playerUUID).setChanged(true);
    }
}
