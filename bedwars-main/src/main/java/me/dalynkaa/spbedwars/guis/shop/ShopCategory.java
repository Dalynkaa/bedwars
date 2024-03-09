package me.dalynkaa.spbedwars.guis.shop;

public enum ShopCategory {
    QUICK(0,9),
    BLOCKS(1,10),
    MELEE(2,11),
    ARMOR(3,12),
    TOOLS(4,13),
    RANGET(5,14),
    POTIONS(6,15),
    UTILITIES(7,16),
    ANOTHER(8,17);

    private Integer id;
    private Integer cursor;
    ShopCategory(Integer id, Integer cursor) {
        this.id = id;
        this.cursor = cursor;
    }
    public Integer getId() {
        return this.id;
    }

    public Integer getCursor() {
        return cursor;
    }
    public static Integer[] getCursors(){
        Integer[] cursors = new Integer[ShopCategory.values().length];
        for (int i = 0; i < ShopCategory.values().length; i++) {
            cursors[i] = ShopCategory.values()[i].getCursor();
        }
        return cursors;
    }
}
