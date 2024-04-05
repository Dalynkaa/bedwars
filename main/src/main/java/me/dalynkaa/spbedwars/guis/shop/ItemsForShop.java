package me.dalynkaa.spbedwars.guis.shop;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.guis.shop.item.ShopArmorItem;
import me.dalynkaa.spbedwars.guis.shop.item.ShopItem;
import me.dalynkaa.spbedwars.guis.shop.item.ShopUpgradbleItem;
import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class ItemsForShop {
    public TeamPlayer player;
    public ShopGui gui;
    public ItemsForShop(TeamPlayer player, ShopGui gui){
        this.player = player;
        this.gui = gui;
    }
    public static class Blocks{
        private GuiItem wool;
        private GuiItem teracotta;
        private GuiItem glass;
        private GuiItem end_stone;
        private GuiItem leader;
        private GuiItem planks;
        private GuiItem obsidian;

        public Blocks(TeamPlayer player){
            wool = ShopItem.builder().setMaterial(player.getGameTeam().getTeam().getWoolMaterial(), 16).setCost(Material.IRON_INGOT, 4).setName(Component.text("Шерсть")).setLore(Arrays.asList(Component.text("Стоимость шерсти: 4 железа"))).build();
            teracotta = ShopItem.builder().setMaterial(Material.TERRACOTTA, 16).setCost(Material.IRON_INGOT, 12).setName(Component.text("Теракотта")).setLore(Arrays.asList(Component.text("Стоимость теракотты: 12 железа"))).build();
            glass = ShopItem.builder().setMaterial(Material.GLASS, 4).setCost(Material.IRON_INGOT, 12).setName(Component.text("Защитное стекло")).setLore(Arrays.asList(Component.text("Стоимость защитного стекла: 12 железа"))).build();
            end_stone = ShopItem.builder().setMaterial(Material.END_STONE, 12).setCost(Material.IRON_INGOT, 24).setName(Component.text("Камень смерти")).setLore(Arrays.asList(Component.text("Стоимость камня смерти: 24 железа"))).build();
            leader = ShopItem.builder().setMaterial(Material.LADDER, 8).setCost(Material.IRON_INGOT, 4).setName(Component.text("Лесница")).setLore(Arrays.asList(Component.text("Стоимость лесницы: 4 железа"))).build();
            planks = ShopItem.builder().setMaterial(Material.OAK_PLANKS, 16).setCost(Material.GOLD_INGOT, 4).setName(Component.text("Доски")).setLore(Arrays.asList(Component.text("Стоимость досок: 4 золота"))).build();
            obsidian = ShopItem.builder().setMaterial(Material.OBSIDIAN, 4).setCost(Material.EMERALD, 4).setName(Component.text("Обсидиан")).setLore(Arrays.asList(Component.text("Стоимость обсидиана: 4 эмеральда"))).build();
        }

        public GuiItem getWool() {
            return wool;
        }

        public GuiItem getTeracotta() {
            return teracotta;
        }

        public GuiItem getGlass() {
            return glass;
        }

        public GuiItem getEnd_stone() {
            return end_stone;
        }

        public GuiItem getLeader() {
            return leader;
        }

        public GuiItem getPlanks() {
            return planks;
        }

        public GuiItem getObsidian() {
            return obsidian;
        }
    }
    public static class Melee{
        private GuiItem stone;
        private GuiItem iron;
        private GuiItem diamond;
        private GuiItem stick;

        public Melee(TeamPlayer player){
            stone = ShopItem.builder().setMaterial(Material.STONE_SWORD, 1).setCost(Material.IRON_INGOT, 10).setName(Component.text("Каменый меч")).setLore(Arrays.asList(Component.text("Стоимость каменого меча: 10 железа")))
                    .setItemType(TeamPlayer.ItemType.SWORD)
                    .setReplace(true)
                    .build();
            iron = ShopItem.builder().setMaterial(Material.IRON_SWORD, 1).setCost(Material.GOLD_INGOT, 7).setName(Component.text("Железный меч")).setLore(Arrays.asList(Component.text("Стоимость железного меча: 7 золота")))
                    .setItemType(TeamPlayer.ItemType.SWORD)
                    .setReplace(true)
                    .build();
            diamond = ShopItem.builder().setMaterial(Material.DIAMOND_SWORD, 1).setCost(Material.EMERALD, 4).setName(Component.text("Алмазный меч")).setLore(Arrays.asList(Component.text("Стоимость алмазного меча: 4 эмеральда")))
                    .setItemType(TeamPlayer.ItemType.SWORD)
                    .setReplace(true)
                    .build();
            stick = ShopItem.builder().setMaterial(Material.STICK, 1).setCost(Material.GOLD_INGOT, 5).setEnchantment(Enchantment.KNOCKBACK, 1).setName(Component.text("Палка")).setLore(Arrays.asList(Component.text("Стоимость лука: 5 золота"))).build();
        }

        public GuiItem getStone() {
            return stone;
        }

        public GuiItem getIron() {
            return iron;
        }

        public GuiItem getDiamond() {
            return diamond;
        }

        public GuiItem getStick() {
            return stick;
        }
        private void swordGive(ShopItem item, Player whoCLicked){
            ItemStack itemToGive = new ItemStack(item.getMaterial(), item.getCount());
            ItemMeta itemMeta = itemToGive.getItemMeta();
            itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, TeamPlayer.ItemType.SWORD.name());
            if (item.getEnchantments() != null){
                for (Map.Entry<Enchantment, Integer> enchantment : item.getEnchantments().entrySet()){
                    itemToGive.addUnsafeEnchantment(enchantment.getKey(), enchantment.getValue());
                }
            }
            itemToGive.setItemMeta(itemMeta);
            ItemStack[] contents = whoCLicked.getInventory().getContents();
            boolean added = false;
            for (int i = 0; i < contents.length; i++) {
                ItemStack itemStack = contents[i];
                if (itemStack!=null){
                    if (itemStack.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("type"), PersistentDataType.STRING)){
                        if (itemStack.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING).equals(TeamPlayer.ItemType.SWORD.name())){
                            contents[i] = itemToGive;
                            added = true;
                            break;
                        }
                    }
                }
            }
            if (!added){
                Logger.debug("not added");
                whoCLicked.getInventory().addItem(itemToGive);
            }else {
                Logger.debug("added");
                whoCLicked.getInventory().setContents(contents);
            }

            if (SPBedWars.getInstance().shopGuiMap.containsKey(whoCLicked.getUniqueId())){
                Logger.debug("update");
                SPBedWars.getInstance().shopGuiMap.get(whoCLicked.getUniqueId()).update();
            }
        }
    }
    public static class Armor{
        private GuiItem stone;
        private GuiItem iron;
        private GuiItem diamond;

        public Armor(TeamPlayer player){
            stone = ShopArmorItem.builder().setArmorType(ShopArmorItem.ArmorType.STONE).setCost(Material.IRON_INGOT, 30).setName(Component.text("Кольчужная броня")).setLore(Arrays.asList(Component.text("Стоимость: 30 железа"))).build();
            iron = ShopArmorItem.builder().setArmorType(ShopArmorItem.ArmorType.IRON).setCost(Material.GOLD_INGOT, 12).setName(Component.text("Железная броня")).setLore(Arrays.asList(Component.text("Стоимость: 12 золота"))).build();
            diamond = ShopArmorItem.builder().setArmorType(ShopArmorItem.ArmorType.DIAMOND).setCost(Material.EMERALD, 6).setName(Component.text("Алмазная броня")).setLore(Arrays.asList(Component.text("Стоимость: 6 эмеральда"))).build();
        }

        public GuiItem getStone() {
            return stone;
        }

        public GuiItem getIron() {
            return iron;
        }

        public GuiItem getDiamond() {
            return diamond;
        }
    }
    public static class Tools{
        private GuiItem shears;
        private ShopUpgradbleItem pickaxe;
        private ShopUpgradbleItem axe;
        private TeamPlayer player;
        private ShopGui gui;


        public Tools(TeamPlayer player, ShopGui gui){
            this.gui = gui;
            this.player = player;
            shears = ShopItem.builder().setMaterial(Material.SHEARS, 1).setCost(Material.IRON_INGOT, 20).setName(Component.text("Ножницы")).setLore(Arrays.asList(Component.text("Стоимость ножниц: 20 железа")))
                    .build();
            ShopItem woodenPickaxe = ShopItem.builder().setMaterial(Material.WOODEN_PICKAXE, 1).setCost(Material.IRON_INGOT, 10).setEnchantment(Enchantment.DIG_SPEED, 1).setName(Component.text("Деревянная кирка")).setLore(Arrays.asList(Component.text("Стоимость деревянной кирки: 10 железа")))
                    .setItemType(TeamPlayer.ItemType.PICKAXE)
                    .setReplace(true)
                    .setGui(gui);
            ShopItem ironPickaxe = ShopItem.builder().setMaterial(Material.IRON_PICKAXE,1).setCost(Material.IRON_INGOT, 10).setEnchantment(Enchantment.DIG_SPEED, 2).setName(Component.text("Железная кирка")).setLore(Arrays.asList(Component.text("Стоимость железной кирки: 10 золота")))
                    .setItemType(TeamPlayer.ItemType.PICKAXE)
                    .setReplace(true)
                    .setGui(gui);
            ShopItem goldenPickaxe = ShopItem.builder().setMaterial(Material.GOLDEN_PICKAXE,1).setCost(Material.GOLD_INGOT, 3).setEnchantment(Map.of(Enchantment.DIG_SPEED, 3,Enchantment.DAMAGE_ALL,2)).setName(Component.text("Золотая кирка")).setLore(Arrays.asList(Component.text("Стоимость золотой кирки: 3 золота")))
                    .setItemType(TeamPlayer.ItemType.PICKAXE)
                    .setReplace(true)
                    .setGui(gui);
            ShopItem diamondPickaxe = ShopItem.builder().setMaterial(Material.DIAMOND_PICKAXE,1).setCost(Material.GOLD_INGOT, 6).setEnchantment(Enchantment.DIG_SPEED, 3).setName(Component.text("Алмазная кирка")).setLore(Arrays.asList(Component.text("Стоимость алмазной кирки: 6 золота")))
                    .setItemType(TeamPlayer.ItemType.PICKAXE)
                    .setReplace(true)
                    .setGui(gui);
            pickaxe = ShopUpgradbleItem.builder(4, TeamPlayer.ItemType.PICKAXE).setLvlItem(1, woodenPickaxe).setLvlItem(2, ironPickaxe).setLvlItem(3, goldenPickaxe).setLvlItem(4, diamondPickaxe);

            ShopItem woodenAxe = ShopItem.builder().setMaterial(Material.WOODEN_AXE, 1).setCost(Material.IRON_INGOT, 10).setEnchantment(Enchantment.DIG_SPEED, 1).setName(Component.text("Деревянный топор")).setLore(Arrays.asList(Component.text("Стоимость деревянного топора: 10 железа")))
                    .setItemType(TeamPlayer.ItemType.AXE)
                    .setReplace(true)
                    .setGui(gui);
            ShopItem stoneAxe = ShopItem.builder().setMaterial(Material.STONE_AXE,1).setCost(Material.IRON_INGOT, 10).setEnchantment(Enchantment.DIG_SPEED, 1).setName(Component.text("Каменный топор")).setLore(Arrays.asList(Component.text("Стоимость ккаменного топора: 10 золота")))
                    .setItemType(TeamPlayer.ItemType.AXE)
                    .setReplace(true)
                    .setGui(gui);
            ShopItem ironAxe = ShopItem.builder().setMaterial(Material.IRON_AXE,1).setCost(Material.GOLD_INGOT, 3).setEnchantment(Map.of(Enchantment.DIG_SPEED, 2)).setName(Component.text("Железный топор")).setLore(Arrays.asList(Component.text("Стоимость железного топора: 3 золота")))
                    .setItemType(TeamPlayer.ItemType.AXE)
                    .setReplace(true)
                    .setGui(gui);
            ShopItem diamondAxe = ShopItem.builder().setMaterial(Material.DIAMOND_AXE,1).setCost(Material.GOLD_INGOT, 6).setEnchantment(Enchantment.DIG_SPEED, 3).setName(Component.text("Алмазный топор")).setLore(Arrays.asList(Component.text("Стоимость алмазного топора: 6 золота")))
                    .setItemType(TeamPlayer.ItemType.AXE)
                    .setReplace(true)
                    .setGui(gui);
            axe = ShopUpgradbleItem.builder(4, TeamPlayer.ItemType.AXE).setLvlItem(1, woodenAxe).setLvlItem(2, stoneAxe).setLvlItem(3, ironAxe).setLvlItem(4, diamondAxe);
        }
        public Tools(){
            ShopItem woodenPickaxe = ShopItem.builder().setMaterial(Material.WOODEN_PICKAXE, 1).setCost(Material.IRON_INGOT, 10).setEnchantment(Enchantment.DIG_SPEED, 1).setName(Component.text("Деревянная кирка")).setLore(Arrays.asList(Component.text("Стоимость деревянной кирки: 10 железа")))
                    .setItemType(TeamPlayer.ItemType.PICKAXE);
            ShopItem ironPickaxe = ShopItem.builder().setMaterial(Material.IRON_PICKAXE,1).setCost(Material.IRON_INGOT, 10).setEnchantment(Enchantment.DIG_SPEED, 2).setName(Component.text("Железная кирка")).setLore(Arrays.asList(Component.text("Стоимость железной кирки: 10 золота")))
                    .setItemType(TeamPlayer.ItemType.PICKAXE);
            ShopItem goldenPickaxe = ShopItem.builder().setMaterial(Material.GOLDEN_PICKAXE,1).setCost(Material.GOLD_INGOT, 3).setEnchantment(Map.of(Enchantment.DIG_SPEED, 3,Enchantment.DAMAGE_ALL,2)).setName(Component.text("Золотая кирка")).setLore(Arrays.asList(Component.text("Стоимость золотой кирки: 3 золота")))
                    .setItemType(TeamPlayer.ItemType.PICKAXE);
            ShopItem diamondPickaxe = ShopItem.builder().setMaterial(Material.DIAMOND_PICKAXE,1).setCost(Material.GOLD_INGOT, 6).setEnchantment(Enchantment.DIG_SPEED, 3).setName(Component.text("Алмазная кирка")).setLore(Arrays.asList(Component.text("Стоимость алмазной кирки: 6 золота")))
                    .setItemType(TeamPlayer.ItemType.PICKAXE);
            pickaxe = ShopUpgradbleItem.builder(4, TeamPlayer.ItemType.PICKAXE).setLvlItem(1, woodenPickaxe).setLvlItem(2, ironPickaxe).setLvlItem(3, goldenPickaxe).setLvlItem(4, diamondPickaxe);

            ShopItem woodenAxe = ShopItem.builder().setMaterial(Material.WOODEN_AXE, 1).setCost(Material.IRON_INGOT, 10).setEnchantment(Enchantment.DIG_SPEED, 1).setName(Component.text("Деревянный топор")).setLore(Arrays.asList(Component.text("Стоимость деревянного топора: 10 железа")))
                    .setItemType(TeamPlayer.ItemType.AXE);
            ShopItem stoneAxe = ShopItem.builder().setMaterial(Material.STONE_AXE,1).setCost(Material.GOLD_INGOT, 3).setEnchantment(Enchantment.DIG_SPEED, 1).setName(Component.text("Каменный топор")).setLore(Arrays.asList(Component.text("Стоимость ккаменного топора: 10 золота")))
                    .setItemType(TeamPlayer.ItemType.AXE);
            ShopItem ironAxe = ShopItem.builder().setMaterial(Material.IRON_AXE,1).setCost(Material.GOLD_INGOT, 10).setEnchantment(Map.of(Enchantment.DIG_SPEED, 2)).setName(Component.text("Железный топор")).setLore(Arrays.asList(Component.text("Стоимость железного топора: 3 золота")))
                    .setItemType(TeamPlayer.ItemType.AXE);
            ShopItem diamondAxe = ShopItem.builder().setMaterial(Material.DIAMOND_AXE,1).setCost(Material.EMERALD, 6).setEnchantment(Enchantment.DIG_SPEED, 3).setName(Component.text("Алмазный топор")).setLore(Arrays.asList(Component.text("Стоимость алмазного топора: 6 золота")))
                    .setItemType(TeamPlayer.ItemType.AXE);
            axe = ShopUpgradbleItem.builder(4, TeamPlayer.ItemType.AXE).setLvlItem(1, woodenAxe).setLvlItem(2, stoneAxe).setLvlItem(3, ironAxe).setLvlItem(4, diamondAxe);
        }

        public GuiItem getShears() {
            return shears;
        }

        public GuiItem getPickaxe() {
            Integer lvl = ShopUpgradbleItem.getPlayerItemLvl(player.getPlayer(), TeamPlayer.ItemType.PICKAXE);
            Logger.debug(lvl.toString());
            if (lvl>=pickaxe.getMaxLvlItem()){
                return ItemBuilder.from(Material.BARRIER).name(Component.text("Максимальный уровень")).asGuiItem();
            }
            return pickaxe.getUpgrades().get(lvl+1).build();
        }
        public ItemStack getToGiveByLvl(Integer lvl, TeamPlayer.ItemType type, TeamPlayer player){
            if (type.equals(TeamPlayer.ItemType.PICKAXE)){
                return pickaxe.getUpgrades().get(lvl).getItemToGive(player);
            } else if (type.equals(TeamPlayer.ItemType.AXE)){
                return axe.getUpgrades().get(lvl).getItemToGive(player);
            }else {
                return null;
            }
        }
        public GuiItem getAxe() {
            Integer lvl = ShopUpgradbleItem.getPlayerItemLvl(player.getPlayer(), TeamPlayer.ItemType.AXE);
            Logger.debug(lvl.toString());
            if (lvl>=axe.getMaxLvlItem()){
                return ItemBuilder.from(Material.BARRIER).name(Component.text("Максимальный уровень")).asGuiItem();
            }
            return axe.getUpgrades().get(lvl+1).build();
        }
        public void givePickaxe(ShopItem item, Player whoCLicked){
            ItemStack itemToGive = new ItemStack(item.getMaterial(), item.getCount());
            ItemMeta itemMeta = itemToGive.getItemMeta();
            if (item.getPersistentDataContainer()!=null){
                for (Map.Entry<String, Object> entry : item.getPersistentDataContainer().entrySet()){
                    if (entry.getValue() instanceof Integer value){
                        itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(entry.getKey())), PersistentDataType.INTEGER, value);
                    } else if (entry.getValue() instanceof String value) {
                        itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(entry.getKey())), PersistentDataType.STRING, value);
                    } else if (entry.getValue() instanceof Boolean value) {
                        itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(entry.getKey())), PersistentDataType.BOOLEAN, value);
                    } else {
                        continue;
                    }
                }
            }
            if (item.getEnchantments() != null){
                for (Map.Entry<Enchantment, Integer> enchantment : item.getEnchantments().entrySet()){
                    itemMeta.addEnchant(enchantment.getKey(), enchantment.getValue(),true);
                }
            }
            itemToGive.setItemMeta(itemMeta);
            ItemStack[] contents = whoCLicked.getInventory().getContents();
            boolean added = false;
            for (int i = 0; i < contents.length; i++) {
                ItemStack itemStack = contents[i];
                if (itemStack!=null){
                    if (itemStack.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("type"), PersistentDataType.STRING)){
                        if (itemStack.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING).equals(TeamPlayer.ItemType.PICKAXE.name())){
                            contents[i] = itemToGive;
                            added = true;
                            break;
                        }
                    }
                }
            }
            if (!added){
                Logger.debug("not added");
                whoCLicked.getInventory().addItem(itemToGive);
            }else {
                Logger.debug("added");
                whoCLicked.getInventory().setContents(contents);
            }
            gui.update();
        }
    }
    public static class Ranget{
        private GuiItem arrow;
        private GuiItem normal_bow;
        private GuiItem power_bow;
        private GuiItem power_punch_bow;

        public Ranget(TeamPlayer player){
            arrow = ShopItem.builder().setMaterial(Material.ARROW, 6).setCost(Material.GOLD_INGOT,2).setName(Component.text("Стрелы")).setLore(Arrays.asList(Component.text("Стоимость 6 стрел: 2 золота"))).build();
            normal_bow = ShopItem.builder().setMaterial(Material.BOW, 1).setCost(Material.GOLD_INGOT, 12).setName(Component.text("Лук")).setLore(Arrays.asList(Component.text("Стоимость лука: 12 золота"))).build();
            power_bow = ShopItem.builder().setMaterial(Material.BOW, 1).setCost(Material.GOLD_INGOT, 20).setEnchantment(Enchantment.ARROW_DAMAGE, 1).setName(Component.text("Лук с мощью")).setLore(Arrays.asList(Component.text("Стоимость лука с мощью: 20 золота"))).build();
            power_punch_bow = ShopItem.builder().setMaterial(Material.BOW, 1).setCost(Material.EMERALD, 6).setEnchantment(Map.of(Enchantment.ARROW_DAMAGE, 1, Enchantment.ARROW_KNOCKBACK, 1)).setName(Component.text("Лук с мощью и отбрасыванием")).setLore(Arrays.asList(Component.text("Стоимость лука с мощью и отбрасыванием: 6 эмеральда"))).build();
        }

        public GuiItem getArrow() {
            return arrow;
        }

        public GuiItem getNormal_bow() {
            return normal_bow;
        }

        public GuiItem getPower_bow() {
            return power_bow;
        }

        public GuiItem getPower_punch_bow() {
            return power_punch_bow;
        }
    }
    public static class Potions{
        private GuiItem speed2;
        private GuiItem jump5;
        private GuiItem invincibility;

        public Potions(TeamPlayer player){
            ItemStack speed2Stack = new ItemStack(Material.POTION);
            PotionMeta speed2StackItemMeta = (PotionMeta) speed2Stack.getItemMeta();
            int duration = 45 * 20;
            speed2StackItemMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, duration, 2), true);
            speed2Stack.setItemMeta(speed2StackItemMeta);

            ItemStack jump5Stack = new ItemStack(Material.POTION);
            PotionMeta jump5StackItemMeta = (PotionMeta) jump5Stack.getItemMeta();
            jump5StackItemMeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, duration, 5), true);
            jump5Stack.setItemMeta(jump5StackItemMeta);

            ItemStack invisStack = new ItemStack(Material.POTION);
            PotionMeta invisStackItemMeta = (PotionMeta) invisStack.getItemMeta();
            duration = 30 * 20;
            invisStackItemMeta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, duration, 1), true);
            invisStack.setItemMeta(invisStackItemMeta);

            speed2 = ShopItem.builder().setItemStack(speed2Stack).setCost(Material.EMERALD, 1).setName(Component.text("Скорость 2")).setLore(Arrays.asList(Component.text("Длительность скорости 2: 45 секунд"),Component.text("Стоимость скорости 2: 1 эмеральд"))).build();
            jump5 = ShopItem.builder().setItemStack(jump5Stack).setCost(Material.EMERALD, 1).setName(Component.text("Пригучесть 5")).setLore(Arrays.asList(Component.text("Длительность Прыгучести 5: 45 секунд"),Component.text("Стоимость Прыгучести 5: 1 эмеральд"))).build();
            invincibility = ShopItem.builder().setItemStack(invisStack).setCost(Material.EMERALD, 2).setName(Component.text("Невидимость")).setLore(Arrays.asList(Component.text("Длительность невидимости: 30 секунд"),Component.text("Стоимость невидимости: 2 эмеральда"))).build();
        }

        public GuiItem getSpeed2() {
            return speed2;
        }

        public GuiItem getJump5() {
            return jump5;
        }

        public GuiItem getInvincibility() {
            return invincibility;
        }
    }
    public static class Utils{
        private GuiItem goldApple;
        private GuiItem enderPearl;
        private GuiItem waterBuket;
        private GuiItem tnt;
        private GuiItem fireball;

        public Utils(TeamPlayer player){
            goldApple = ShopItem.builder().setMaterial(Material.GOLDEN_APPLE, 1).setCost(Material.GOLD_INGOT, 3).setName(Component.text("Золотой яблоко")).setLore(Arrays.asList(Component.text("Стоимость золотого яблока: 3 золота"))).build();
            enderPearl = ShopItem.builder().setMaterial(Material.ENDER_PEARL, 1).setCost(Material.EMERALD,4).setName(Component.text("Перл")).setLore(Arrays.asList(Component.text("Стоимость: 4 эмеральда"))).build();
            waterBuket = ShopItem.builder().setMaterial(Material.WATER_BUCKET, 1).setCost(Material.GOLD_INGOT, 6).setName(Component.text("Ведро воды")).setLore(Arrays.asList(Component.text("Стоимость: 6 золота"))).build();
            tnt = ShopItem.builder().setMaterial(Material.TNT, 1).setCost(Material.GOLD_INGOT, 8).addPercistData("tnt",true).setName(Component.text("ТНТ")).setLore(Arrays.asList(Component.text("Стоимость: 8 золота"))).build();
            fireball = ShopItem.builder().setMaterial(Material.FIRE_CHARGE, 1).setCost(Material.IRON_INGOT, 40).setName(Component.text("Фаербол")).addPercistData("fireball", true).build();
        }

        public GuiItem getGoldApple() {
            return goldApple;
        }

        public GuiItem getEnderPearl() {
            return enderPearl;
        }

        public GuiItem getWaterBuket() {
            return waterBuket;
        }

        public GuiItem getTnt() {
            return tnt;
        }
        public GuiItem getFireball() {return fireball;}
    }

}
