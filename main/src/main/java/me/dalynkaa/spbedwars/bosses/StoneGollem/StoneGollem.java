//package me.dalynkaa.spbedwars.bosses.StoneGollem;
//
//import me.dalynkaa.spbedwars.bosses.AbstractBoss.ArenaBoss;
//import me.dalynkaa.spbedwars.utils.dataclasses.game.GameLocation;
//
//import javax.swing.text.html.parser.Entity;
//
//public class StoneGollem extends ArenaBoss {
//    private Boolean isSummoned = false;
//    private Entity entity;
//
//    @Override
//    public String getName() {
//        return "Голем";
//    }
//
//    @Override
//    public Double getHealth() {
//        return 200.0;
//    }
//
//    @Override
//    public Boolean isSummoned() {
//        return isSummoned;
//    }
//
//    @Override
//    public void move(GameLocation destination, boolean withAnimation) {
//        if (canTeleport()) {
//            if (withAnimation) {
//                performTeleportWithAnimation(destination);
//            } else {
//                teleportToDestination(destination);
//            }
//        }
//    }
//
//    private boolean canTeleport() {
//        return isSummoned && entity != null;
//    }
//
////    private void performTeleportWithAnimation(GameLocation destination) {
////        entity.playAnimation("teleportIn", () -> {
////            teleportToDestination(destination);
////            entity.playAnimation("teleportOut");
////        });
////    }
//
////    private void teleportToDestination(GameLocation destination) {
////        entity.teleport(destination.getLocation());
////    }
////
////    @Override
////    public void summon(GameLocation location) {
////        entity = CustomEntity.spawn("bedwars:golem", location.getLocation());
////    }
//}
