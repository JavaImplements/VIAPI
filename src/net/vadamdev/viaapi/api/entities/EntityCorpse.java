package net.vadamdev.viaapi.api.entities;

import net.minecraft.server.v1_12_R1.*;
import net.vadamdev.viaapi.tools.packet.Reflection;
import net.vadamdev.viaapi.tools.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

public class EntityCorpse {
    /**
     * @author VadamDev
     * @since 28.08.2021 - Updated 07.09.2021
     */

    private EntityFakePlayer fakePlayer;

    private Player owner;
    private Location loc;
    private boolean equipped;
    private int id;

    public EntityCorpse(Player owner, Location loc, boolean equipped) {
        this.owner = owner;
        this.loc = loc;
        this.equipped = equipped;

        initFakePlayer();
    }

    private void initFakePlayer() {
        String[] skin = Utils.getSkinFromName(owner.getName());
        this.fakePlayer = new EntityFakePlayer(owner.getLocation(), owner.getName(), skin[0], skin[1]);
        this.fakePlayer.setRemovedFromTab(true);

        EntityPlayer entityPlayer = this.fakePlayer.fakePlayer;
        entityPlayer.yaw = 0;
        entityPlayer.pitch = 0;

        this.id = entityPlayer.getId();
    }

    public void sendWithPacket(Player player) {
        PacketPlayOutBed bed = new PacketPlayOutBed();
        Reflection.setField(Reflection.getField(bed.getClass(), "a"), bed, id);
        Reflection.setField(Reflection.getField(bed.getClass(), "b"), bed, new BlockPosition(loc.getBlockX(), 0, loc.getBlockZ()));

        player.sendBlockChange(loc.clone().subtract(0, loc.getY(), 0), Material.BED_BLOCK, (byte) 0);

        fakePlayer.sendWithPacket(player);
        Reflection.sendPacket(player, bed);
        Reflection.sendPacket(player, new PacketPlayOutEntity.PacketPlayOutRelEntityMove(id, (byte) 0, (byte) 4, (byte) 0, false));

        if(equipped) {
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(owner.getEquipment().getHelmet())));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(owner.getEquipment().getChestplate())));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(owner.getEquipment().getLeggings())));
            Reflection.sendPacket(player, new PacketPlayOutEntityEquipment(id, EnumItemSlot.FEET, CraftItemStack.asNMSCopy(owner.getEquipment().getBoots())));
        }
    }

    public void removeWithPacket(Player player) {
        fakePlayer.removeWithPacket(player);
    }

    public Location getLoc() {
        return loc;
    }
}
