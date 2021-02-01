package com.butukay.shiftsit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import ru.azerusteam.Classes.SirPlayer;

public class EventHandlers implements Listener {

    private final ru.azerusteam.sirmanager.Main sl = (ru.azerusteam.sirmanager.Main) Bukkit.getServer().getPluginManager().getPlugin("SimpleLay");

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Vector velocity = player.getVelocity();

        if (velocity.getY() > 0) {

            double jumpVelocity = 0.42F;
            PotionEffect jumpPotion = player.getPotionEffect(PotionEffectType.JUMP);
            if (jumpPotion != null) {

                jumpVelocity += (double) ((float) jumpPotion.getAmplifier() + 1) * 0.1F;
            }

            if (player.getLocation().getBlock().getType() != Material.LADDER && Double.compare(velocity.getY(), jumpVelocity) == 0) {
                if (player.isSneaking()) {
                    if (!Shiftsit.playerDetails.containsKey(e.getPlayer().getName())) {
                        Shiftsit.playerDetails.put(e.getPlayer().getName(), "false");
                    }

                    final String val = (String) Shiftsit.playerDetails.get(e.getPlayer().getName());

                    if (val.equals("true")) {

                        final long delay = Shiftsit.getPlugin(Shiftsit.class).getConfig().getInt("shift-delay");

                        final double layPitch = Shiftsit.getPlugin(Shiftsit.class).getConfig().getDouble("pitch.lay");
                        final double sitPitch = Shiftsit.getPlugin(Shiftsit.class).getConfig().getDouble("pitch.sit");

                        if (player.getLocation().getPitch() > layPitch) {

                            e.setCancelled(true);

                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(sl, () -> {
                                SirPlayer srp = new SirPlayer(player);

                                player.setSneaking(false);

                                srp.setLay();
                            }, delay);
                        } else if (player.getLocation().getPitch() > sitPitch) {

                            e.setCancelled(true);

                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(sl, () -> {
                                SirPlayer srp = new SirPlayer(player);

                                player.setSneaking(false);

                                srp.setSit(false, player.getLocation(), sl.getConfig().getBoolean("sit.announce.command"));
                            }, delay);
                        }
                    }
                }
            }
        }
    }
}
