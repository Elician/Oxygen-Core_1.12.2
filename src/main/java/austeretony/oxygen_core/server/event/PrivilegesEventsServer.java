package austeretony.oxygen_core.server.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import austeretony.oxygen_core.common.api.CommonReference;
import austeretony.oxygen_core.common.config.PrivilegesConfig;
import austeretony.oxygen_core.common.main.OxygenMain;
import austeretony.oxygen_core.common.privilege.Role;
import austeretony.oxygen_core.server.OxygenManagerServer;
import austeretony.oxygen_core.server.api.PrivilegesProviderServer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import scala.Int;

public class PrivilegesEventsServer {

    @SubscribeEvent
    public void onChatMessage(ServerChatEvent event) {
        if (PrivilegesConfig.ENABLE_FORMATTED_CHAT.asBoolean()) {
            UUID playerUUID = CommonReference.getPersistentUUID(event.getPlayer());
            Role role = PrivilegesProviderServer.getChatFormattingPlayerRole(playerUUID);
            if (role != null) {
                if (PrivilegesConfig.ENABLE_CUSTOM_FORMATTED_CHAT.asBoolean()) {
                    String prefix = null, username, result;

                    TextFormatting defColor = TextFormatting.values()[PrivilegesConfig.DEFAULT_CHAT_COLOR.asInt()];

                    if (!role.getPrefix().isEmpty())
                        prefix = role.getPrefixColor() + role.getPrefix() + TextFormatting.RESET;

                    username = role.getUsernameColor() + CommonReference.getName(event.getPlayer()) + TextFormatting.RESET;

                    result = !role.getPrefix().isEmpty() ? PrivilegesConfig.FORMATTED_CHAT_PREFIX_PATTERN.asString() : PrivilegesConfig.FORMATTED_CHAT_PATTERN.asString();
                    if (prefix != null)
                        result = defColor + result.replace("@prefix", prefix + defColor);
                    result = result.replace("@username", username + defColor);

                    ITextComponent messageComponent = ForgeHooks.newChatWithLinks(event.getMessage());
                    messageComponent.getStyle().setColor(role.getChatColor());

                    event.setComponent(new TextComponentString(result).appendSibling(messageComponent));
                } else {
                    StringBuilder formattedUsername = new StringBuilder();

                    if (!role.getPrefix().isEmpty()) {
                        formattedUsername.append(role.getPrefixColor());
                        formattedUsername.append("[");
                        formattedUsername.append(role.getPrefix());
                        formattedUsername.append("]");
                        formattedUsername.append(TextFormatting.RESET);
                    }

                    formattedUsername.append(role.getUsernameColor());
                    formattedUsername.append(CommonReference.getName(event.getPlayer()));
                    formattedUsername.append(TextFormatting.RESET);

                    ITextComponent messageComponent = ForgeHooks.newChatWithLinks(event.getMessage());
                    messageComponent.getStyle().setColor(role.getChatColor());

                    event.setComponent(new TextComponentTranslation("chat.type.text", formattedUsername.toString(), messageComponent));
                }
            } else if (PrivilegesConfig.ENABLE_CUSTOM_FORMATTED_CHAT.asBoolean()) {
                ITextComponent messageComponent = new TextComponentString(PrivilegesConfig.FORMATTED_CHAT_PATTERN.asString().replace("@username", CommonReference.getName(event.getPlayer())))
                        .appendSibling(ForgeHooks.newChatWithLinks(event.getMessage()));
                messageComponent.getStyle().setColor(TextFormatting.values()[PrivilegesConfig.DEFAULT_CHAT_COLOR.asInt()]);
                event.setComponent(messageComponent);
            }
        }
    }
    @SubscribeEvent
    void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.player.world.getWorldTime()%100==0){
            if(event.player instanceof EntityPlayerMP) {
                UUID u=event.player.getUUID(event.player.getGameProfile());
                OxygenManagerServer.instance().getPrivilegesContainer().syncPrivilegesData((EntityPlayerMP) event.player);
                LuckPerms api = LuckPermsProvider.get();
                String s=api.getUserManager().getUser(u).getPrimaryGroup();
                ArrayList<String>group_names=new ArrayList<String>();
                Set<Integer> group_ids=OxygenManagerServer.instance().getPrivilegesContainer().getPlayerRolesIds(u);
                if(group_ids!=null) {
                    for (Integer id : group_ids) {
                        group_names.add(OxygenManagerServer.instance().getPrivilegesContainer().getRole(id).getPrefix());
                    }
                }
                if(!group_names.contains(s) && group_names.size()>0){
                    for (Integer id:group_ids) {
                        OxygenMain.LOGGER.info("Removed role {} from {}",id,event.player.getDisplayName().getUnformattedText());
                        OxygenManagerServer.instance().getPrivilegesManager().removeRoleFromPlayer(CommonReference.getPersistentUUID(event.player),id);//.removeRole((EntityPlayerMP) event.player,id);
                    }
                }
                if(!group_names.contains(s)){
                    Collection<Role> rs=OxygenManagerServer.instance().getPrivilegesContainer().getRoles();
                    for(Role r: rs){
                        if(r.getPrefix().equals(s)){
                            OxygenMain.LOGGER.info("Added role {} to {}",r.getId(),event.player.getDisplayName().getUnformattedText());
                            OxygenManagerServer.instance().getPrivilegesManager().addRoleToPlayer(CommonReference.getPersistentUUID(event.player), r.getId());
                        }
                    }
                }
            }
        }
    }
}
