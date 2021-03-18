package de.legoshi.parkour.util.fakeplayer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;


public class NPC extends Reflections {

      private int entityID;
      private GameProfile gameProfile;
      private Location location;
      private EntityPlayer npc;

      public NPC(Player player, String name, Location location) {

            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
            this.gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.WHITE + "" + ChatColor.BOLD + name);
            this.npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
            npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
            this.entityID = npc.getId();

            changeSkin();
            this.location = location.clone();

      }

      public void changeSkin() {

            String value = "eyJ0aW1lc3RhbXAiOjE0NDI4MzY1MTU1NzksInByb2ZpbGVJZCI6IjkwZWQ3YWY0NmU4YzRkNTQ4MjRkZTc0YzI1MTljNjU1IiwicHJvZmlsZU5hbWUiOiJDb25DcmFmdGVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xMWNlZDMzMjNmYjczMmFjMTc3MTc5Yjg5NWQ5YzJmNjFjNzczZWYxNTVlYmQ1Y2M4YzM5NTZiZjlhMDlkMTIifX19";
            String signature = "tFGNBQNpxNGvD27SN7fqh3LqNinjJJFidcdF8LTRHOdoMNXcE5ezN172BnDlRsExspE9X4z7FPglqh/b9jrLFDfQrdqX3dGm1cKjYbvOXL9BO2WIOEJLTDCgUQJC4/n/3PZHEG2mVADc4v125MFYMfjzkznkA6zbs7w6z8f7pny9eCWNXPOQklstcdc1h/LvflnR+E4TUuxCf0jVsdT5AZsUYIsJa6fvr0+vItUXUdQ3pps0zthObPEnBdLYMtNY3G6ZLGVKcSGa/KRK2D/k69fmu/uTKbjAWtniFB/sdO0VNhLuvyr/PcZVXB78l1SfBR88ZMiW6XSaVqNnSP+MEfRkxgkJWUG+aiRRLE8G5083EQ8vhIle5GxzK68ZR48IrEX/JwFjALslCLXAGR05KrtuTD3xyq2Nut12GCaooBEhb46sipWLq4AXI9IpJORLOW8+GvY+FcDwMqXYN94juDQtbJGCQo8PX670YjbmVx7+IeFjLJJTZotemXu1wiQmDmtAAmug4U5jgMYIJryXMitD7r5pEop/cw42JbCO2u0b5NB7sI/mr4OhBKEesyC5usiARzuk6e/4aJUvwQ9nsiXfeYxZz8L/mh6e8YPJMyhVkFtblbt/4jPe0bs3xSUXO9XrDyhy9INC0jlLT22QjNzrDkD8aiGAopVvfnTTAug=";
            gameProfile.getProperties().put("textures", new Property("textures", value, signature));

      }

      public void spawn(Player player) {

            PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));

      }

      public void teleport(Location location, Player player) {

            PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook packetMoveLook = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(
                entityID,
                (short)((location.getX() * 32 - this.location.getX() * 32)*128),
                (short)((location.getY() * 32 - this.location.getY() * 32)*128),
                (short)((location.getZ() * 32 - this.location.getZ() * 32)*128),
                getFixRotation(this.location.getYaw()),
                getFixRotation(this.location.getPitch()),
                true);

            PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation(this.npc, getFixRotation(this.location.getYaw()));

            PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;

            playerConnection.sendPacket(packetMoveLook);
            playerConnection.sendPacket(packetHead);
            this.location = location.clone();

      }

      public void destroy(Player player) {

            PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutEntityDestroy(entityID));

      }

      public int getFixLocation(double pos) {
            return MathHelper.floor(pos * 32.0D);
      }

      public byte getFixRotation(float yawpitch) {
            return (byte) ((int) (yawpitch * 256.0F / 360.0F));
      }

}
