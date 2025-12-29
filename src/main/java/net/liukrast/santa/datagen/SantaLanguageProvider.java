package net.liukrast.santa.datagen;

import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.SantaLang;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.registry.SantaItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class SantaLanguageProvider extends LanguageProvider {
    public SantaLanguageProvider(PackOutput output) {
        super(output, SantaConstants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        /* AUTO-GENERATED */
        SantaConstants.getElementEntries(BuiltInRegistries.ITEM)
                .filter(e -> e.getValue() != SantaBlocks.CRYOLITE_BLOCK.get().asItem())
                .forEach(e -> add(e.getValue().getDescriptionId(), autoName(e.getKey())));
        SantaConstants.getElementEntries(BuiltInRegistries.ENTITY_TYPE)
                .forEach(e -> add(e.getValue(), autoName(e.getKey())));
        /* NON-AUTO REGISTRY ENTRIES */
        addReplaced("fluid.%s.cryolite", "Cryolite");
        addReplaced("fluid.%s.molten_sugar", "Molten Sugar");
        add(SantaBlocks.CRYOLITE_BLOCK.get(), "Block of Cryolite");
        addReplaced("itemGroup.%s", "Create: Santa Logistics");

        /* ITEM DESCRIPTIONS */
        addShiftSummary(SantaBlocks.PRIME_CRYOLITE_BLOCK.get(), "Creative-only infinite cryolite generator");
        addShiftSummary(SantaItems.FROSTBURN_CORE.get(), "The power of the sun, in the palm of my hand...");
        addShiftSummary(SantaBlocks.SCHEDULE_CLOCK.get(), "Displays santa claus's schedule time");
        addShiftSummary(SantaBlocks.SHIELDED_STONE.get(), "A bedrock-like stone that protects the entire santa's base");
        for(var pack : SantaItems.PRESENTS) {
            addShiftSummary(pack.get(), "Similar to a normal cardboard package, but only the receiver can open it, and content is hidden");
        }

        /* CONTAINERS */
        addReplaced("container.%s.robo_elf.trust_gain", "How much trust you will gain");
        addReplaced("container.%s.robo_elf.energy_usage", "How much energy the elf will consume");
        addReplaced("container.%s.robo_elf.process_time", "How many seconds it will take to craft this item");
        addReplaced("container.%s.robo_elf.trust_level", "Your current trust level");
        addReplaced("container.%s.santa", "Santa's Sleigh");
        addReplaced("container.%s.robo_elf.enqueue", "Enqueue items");
        addReplaced("container.%s.robo_elf.queue_full", "This robo elf's queue is full");
        addReplaced("container.%s.robo_elf.not_enough_items", "You do not own enough items for this exchange");
        addReplaced("container.%s.robo_elf.no_recipe_selected", "No recipe selected");
        addReplaced("container.%s.robo_elf.scroll_to_change_amount", "Scroll to change amount");

        /* SCROLL VALUE LABELS */
        addPrefixed("kinetics.frostburn_engine.overclock", "Choose an overclock amount");

        /* GOGGLE TOOLTIPS */
        addPrefixed("gui.santa_claus.info_header", "Santa Claus Information:");
        addPrefixed("gui.santa_claus.satisfaction", "Satisfaction level:");
        addPrefixed("gui.santa_claus.progress", "Progress: %s/%s ⭐");
        addPrefixed("gui.santa_claus.result", "Result: %s");

        addPrefixed("tooltip.overclock", "Overclock Amount:");
        addPrefixed("tooltip.temperature", "Temperature:");
        addPrefixed("tooltip.temperature_per_tick", "per tick");

        addPrefixed("gui.robo_elf.info_header", "Robo Elf Information:");
        addPrefixed("gui.robo_elf.title", "Activity Stress:");
        addPrefixed("gui.robo_elf.capacity", "Remaining Charge:");
        addPrefixed("gui.robo_elf.oxidation", "Oxidation level:");
        addPrefixed("gui.robo_elf.oxidation.clean", "Clean");
        addPrefixed("gui.robo_elf.oxidation.exposed", "Exposed");
        addPrefixed("gui.robo_elf.oxidation.weathered", "Weathered");
        addPrefixed("gui.robo_elf.oxidation.oxidized", "Oxidized");

        addPrefixed("gui.santa_dock.info_header", "Santa Dock Information:");
        addPrefixed("gui.santa_dock.status", "Status:");
        addPrefixed("gui.santa_dock.status.connected", "Connected");
        addPrefixed("gui.santa_dock.status.idle", "Idle");
        addPrefixed("gui.santa_dock.status.error", "Error");
        addPrefixed("gui.santa_dock.status.error.info_header", "Reason:");
        addPrefixed("gui.santa_dock.status.error.night", "Santa is already flying");
        addPrefixed("gui.santa_dock.status.error.already_taken", "Address taken");
        addPrefixed("gui.santa_dock.status.error.out_of_bound", "Max docks per world reached");
        addPrefixed("gui.santa_dock.status.error.wrong_dimension", "Dock outside of the overworld");
        addPrefixed("gui.santa_dock.status.connected.info_header", "Santa will pass by this dock tonight");

        addPrefixed("gui.schedule_clock.info_header", "Schedule Clock Information:");
        addPrefixed("gui.schedule_clock.current_time", "Right now it's %s");
        addPrefixed("gui.schedule_clock.schedule", "Schedule:");
        addPrefixed("gui.schedule_clock.wake_up_time", "Wake up at %s");
        addPrefixed("gui.schedule_clock.leave_time", "Leave at %s");
        addPrefixed("gui.schedule_clock.get_back_time", "Get back at %s");


        /* COMMANDS */
        add("commands.santa.get_target", "%s's trust is %s");
        add("commands.santa.set_target", "%s's trust is set to %s");
        add("commands.santa.empty", "§cNo santa dock registered");
        add("commands.santa.title", "Santa docks:");
        add("commands.santa.try_spawn_santa_base.success", "Successfully placed Santa's base at %s (%s blocks away)");
        add("commands.santa.try_spawn_santa_base.already_placed", "Santa's base was already located at %s (%s blocks away)");
        add("commands.santa.try_spawn_santa_base.failed", "Unable to automatically generate santa's base. Please use the manual command");

        add("chat.santa_dock.available", "Santa dock is connected and active");
        add("chat.santa_dock.to_be_removed", "Santa dock will be deleted at the end of the night");

        addReplaced("painting.%s.snowy_night.title", "Snowy Night");
        addReplaced("painting.%s.snowy_night.author", "LiukRast");

        createPonder(SantaBlocks.SANTA_DOCK.asItem(),
                "Exchanging global packages with the santa dock",
                "Santa docks allow global package exchanges",
                "Right click to open the menu, and setup an address...",
                "...if everything went correctly, your dock will say it's connected through the goggle tooltip...",
                "...and now your address is global and unique.",
                "During the night, something special will happen...",
                "...and §6Santa§r will deliver packages.",
                "Otherwise, if you want to send a package, put it in your santa dock...",
                "...and don't forget your receiver's address!"
        );

        createPonder(SantaBlocks.FROSTBURN_ENGINE.asItem(),
                "Generating extreme power through the frostburn engine",
                "Frostburn engine is a very powerful stress generator, with massive amount of SU produced",
                "...and it comes for free!",
                "But there's a catch...",
                "the more you §aoverclock §rit (through the right-click menu), §athe §amore §aSU §ait §aproduces§r...",
                "...and the more it gets unstable.",
                "If temperature reaches a §ccritical §cpoint§r, or it's §cbroken §cwhen §ctemperature §cis §ctoo §chigh§r...",
                "...the entire engine will §ccollapse §cand §cexplode§r.",
                "§6Santa§r, has luckily found a solution for us...",
                "...§bcryolite §ris a very cold and extremely functional liquid to lower your engine's temperature!"
                );

        createPonder("robo_elf",
                "Interacting with Robo-ELF",
                "Robo elves can be found in many cold biomes...",
                "...and will be sleeping oxidized.",
                "You can use an axe to wax them till they are §aclean§r.",
                "Every elf has a Charge which will decrease as he does more activities and gets §6stressed§r.",
                "You can manually charge a robo-elf by holding shift+right-click",
                "When their charge gets lower, they will automatically try to find a charge station, if possible, and auto-charge"
        );

        createPonder("robo_elf_packaging",
                "Creating presents with a Robo-ELF",
                "Robo elves can collect packages dropped on ground...",
                "...and turn them into colorful presents!",
                "Presents can only be opened by a player who's name matches the address, and will hide the content",
                "Be careful, crafting too many presents may stress the robo-elf"
        );

        createPonder("robo_elf_trust",
                "Gaining trust through the robo-ELF",
                "Robo elves are very important to gain §6santa's trust§r...",
                "...by interacting with a robo-elf, you can see a menu opens up.",
                "You can exchange materials with a robo elf to unlock several special items, and gain trust...",
                "...every item requested, will be queued, with a max of 18 items.",
                "Once the robo-elf is done crafting, he will personally hold your item in his inventory, and try to find you in order to deliver it!",
                "Don't mess with the robo-elf, as it will lower your trust"
        );

        createPonder("santa_claus",
                "Interacting with Santa Claus",
                "Santa claus is a very busy hard-worker...",
                "...he has a precise schedule, which can be seen on a §6Schedule §6clock§r.",
                "After sleeping, there is a short period where you can interact with Santa...",
                "...here is where you will use the §6trust§r you gained.",
                "By giving santa different items, and if you have enough trust (§aJEI §arecommended§r)...",
                "...you will start satisfying santa.",
                "Once the progress is full, and santa finished eating, he will hold your reward in his hands!",
                "Interact with santa to obtain the item"
                );

        /* RECIPE */
        add("santa_logistics.recipe.robo_elf_trading", "Trading with Robo-Elf");
        add("santa_logistics.recipe.santa_claus_trading", "Trading with Santa Claus");
    }

    private void createPonder(Item item, String header, String... tooltips) {
        String id = BuiltInRegistries.ITEM.getKey(item).getPath();
        createPonder(id, header, tooltips);
    }

    private void createPonder(String id, String header, String... tooltips) {
        addReplaced("%s.ponder." + id + ".header", header);
        for(int i = 0; i < tooltips.length; i++) {
            addReplaced("%s.ponder." + id + ".text_" + (i+1), tooltips[i]);
        }
    }

    private void addShiftSummary(ItemLike key, String value) {
        add(SantaLang.getTooltip(key), value);
    }

    private void addReplaced(String key, String value) {
        add(String.format(key, SantaConstants.MOD_ID), value);
    }

    private void addPrefixed(String key, String value) {
        addReplaced("%s."+key, value);
    }

    public String autoName(String id) {
        String[] words = id.split("_");
        for(int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
        }
        return String.join(" ", words);
    }
}
