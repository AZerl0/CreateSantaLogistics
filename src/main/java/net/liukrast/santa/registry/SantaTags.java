package net.liukrast.santa.registry;

import net.liukrast.santa.SantaConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SantaTags {

    public static class Items {
        public static final TagKey<Item> SANTA_FOOD_A = create("santa_food_a");
        public static final TagKey<Item> SANTA_FOOD_B = create("santa_food_b");

        private static TagKey<Item> create(String name) {
            return TagKey.create(Registries.ITEM, SantaConstants.id(name));
        }
    }
}
