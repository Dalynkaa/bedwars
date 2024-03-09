package me.dalynkaa.spbedwars.utils.dataclasses.game;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.dataclasses.another.CustomSkin;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameShopType;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("Shop")
public class GameShop implements ConfigurationSerializable {
    private GameLocation shopPosition;
    private GameShopType shopType;
    private net.citizensnpcs.api.npc.NPC npc;
    private String title;
    private CustomSkin skin;
    private String headItem;

    public GameShop(GameLocation shopPosition, GameShopType shopType, String skinName, String headItemName) {
        this.shopPosition = shopPosition;
        this.shopType = shopType;
        if (shopType.equals(GameShopType.NORMAL)){
            this.title = "Магазин предметов";
        }else {
            this.title = "Магазин улучшений";
        }
        this.skin = CustomSkin.getSkin(skinName, CustomSkin.getSkin("default"));
        this.headItem = headItemName;
    }

    public GameLocation getShopPosition() {
        return shopPosition;
    }

    public void setShopPosition(GameLocation shopPosition) {
        this.shopPosition = shopPosition;
    }

    public GameShopType getShopType() {
        return shopType;
    }

    public void setShopType(GameShopType shopType) {
        this.shopType = shopType;
    }

    public net.citizensnpcs.api.npc.NPC getNpc() {
        return npc;
    }

    public void setNpc(net.citizensnpcs.api.npc.NPC npc) {
        this.npc = npc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getHeadItemName() {
        return headItem;
    }

    public CustomSkin getSkin() {
        return skin;
    }

    public void setSkin(CustomSkin skin) {
        this.skin = skin;
    }

//    public void spawn(){
//        npc = NPCLib.getInstance().generateGlobalNPC(SPBedWars.getInstance(), getShopType().name()+"_"+UUID.randomUUID(), getShopPosition().getLocation().add(0.5,0,0.5));
//        npc.setShowOnTabList(false);
//        npc.setText(getTitle());
//        npc.show();
//        npc.forceUpdate();
//        npc.setGazeTrackingType(NPC.GazeTrackingType.PLAYER);
//        npc.setSkin(getSkin().getValue(), getSkin().getSignature());
//        if (getHeadItemName()!=null){
//            if (ItemsAdder.areItemsLoaded() == false){
//                Bukkit.getScheduler().runTaskLater(SPBedWars.getInstance(), ()->{
//                    CustomStack customStack = CustomStack.getInstance("bedwars:"+getHeadItemName());
//                    npc.setItem(NPC.Slot.HEAD, customStack.getItemStack());
//                }, 20*20);
//            }
//        }
//        npc.addCustomClickAction(((npc1, player) -> {
//            if (getShopType().equals(GameShopType.NORMAL)){
//                TeamPlayer teamPlayer = TeamPlayer.fromPlayer(BPlayer.getByUUID(player.getUniqueId()));
//                new ShopGui(teamPlayer);
//            }else {
//                TeamPlayer teamPlayer = TeamPlayer.fromPlayer(BPlayer.getByUUID(player.getUniqueId()));
//                new UpgradeGui(teamPlayer);
//            }
//        }));
//    }
    public void spawn(){
        net.citizensnpcs.api.npc.NPC npc = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore()).createNPC(EntityType.PLAYER, getShopType().name()+"_"+UUID.randomUUID());
        npc.spawn(getShopPosition().getLocation().add(0.5,0,0.5));
        npc.setName(getTitle());
        npc.setProtected(true);
        npc.getOrAddTrait(SkinTrait.class).setSkinPersistent(getSkin().getName(), getSkin().getSignature(), getSkin().getValue());
        npc.getEntity().getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, getShopType().name());
        npc.data().set("type", getShopType());
        if (getHeadItemName()!=null){
            if (ItemsAdder.areItemsLoaded() == false){
                Bukkit.getScheduler().runTaskLater(SPBedWars.getInstance(), ()->{
                    CustomStack customStack = CustomStack.getInstance("bedwars:"+getHeadItemName());
                    Equipment equipment = npc.getOrAddTrait(Equipment.class);
                    equipment.set(Equipment.EquipmentSlot.HELMET, customStack.getItemStack());
                    Logger.debug("apply head item");
                }, 20*20);
            }
        }
        LookClose lookClose = npc.getOrAddTrait(LookClose.class);
        lookClose.findNewTarget();
        lookClose.lookClose(true);

    }
    public void despawn(){
        if (npc!=null){
            npc.despawn();
            npc.destroy();

        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("location", getShopPosition());
        map.put("type", getShopType().name());
        map.put("skin", getSkin().getName());
        map.put("headItem", getHeadItemName());
        return map;
    }
    public static GameShop deserialize(Map<String, Object> map) {
        GameLocation shopLoc = (GameLocation) map.get("location");
        GameShopType gameShopType = GameShopType.valueOf((String) map.get("type"));
        String skinName = (String) map.get("skin");
        String headItemName = (String) map.getOrDefault("headItem", null);
        Logger.debug("headItemName: " + headItemName);
        return new GameShop(shopLoc, gameShopType, skinName, headItemName);
    }

    @Override
    public String toString() {
        return "GameShop{" +
                "shopPosition=" + shopPosition +
                ", shopType=" + shopType +
                ", npc=" + npc +
                ", title='" + title + '\'' +
                '}';
    }
}
