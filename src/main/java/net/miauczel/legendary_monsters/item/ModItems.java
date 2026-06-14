package net.miauczel.legendary_monsters.item;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.item.custom.*;
import net.miauczel.legendary_monsters.item.custom.Eyes.*;
import net.miauczel.legendary_monsters.item.custom.customArmor.ModArmorMaterials;
import net.miauczel.legendary_monsters.item.custom.customArmor.armorItem.AnnihilatorArmorItem;
import net.miauczel.legendary_monsters.item.custom.customArmor.piece.Annihilator.AnnihilatorChestplateItem;
import net.miauczel.legendary_monsters.item.custom.customArmor.piece.Annihilator.AnnihilatorHelmetItem;
import net.miauczel.legendary_monsters.sound.ModJukeBoxSongs;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Properties;
import java.util.function.Supplier;

public class ModItems {
    public static final net.neoforged.neoforge.registries.DeferredRegister.Items ITEMS = net.neoforged.neoforge.registries.DeferredRegister.createItems(LegendaryMonsters.MOD_ID);

    public static final Supplier<Item> CHORUS_BLADE = ITEMS.register("chorus_blade",
            () -> new ChorusBladeItem(new Item.Properties()));
    public static final Supplier<Item> DINOSAUR_BONE = ITEMS.register("dinosaur_bone",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final Supplier<Item> WITHERED_BONE = ITEMS.register("withered_bone",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final Supplier<Item> INFECTED_CHORUS_FRUIT = ITEMS.register("infected_chorus_fruit",
            () -> new Item(new Item.Properties().food(ModFoods.INFECTED_CHORUS_FRUIT)));
    // public static final Supplier<Item> CHORUS_BOMB_PROJECTILE = ITEMS.register("chorus_bomb_projectile",
    //     () -> new Item(new Item.Properties()));

    public static final Supplier<Item> CHORUS_CRYSTAL = ITEMS.register("chorus_crystal",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FROZEN_RUNE = ITEMS.register("frozen_rune",
            () -> new Item(new Item.Properties()));

    public static final Supplier<Item> THE_GREAT_FROST = ITEMS.register("the_great_frost",
            () -> new TheGreatFrostItem(new Item.Properties().rarity(Rarity.EPIC).durability(750)));
    public static final Supplier<Item> LARGE_SHULKER_SHELL = ITEMS.register("large_shulker_shell",
            () -> new Item(new Item.Properties()));

    public static final Supplier<Item> LAVA_EATERS_SKIN = ITEMS.register("lava_eaters_skin", () -> new LavaEatersSkinItem());
    public static final Supplier<Item> ENTITY_WARPER = ITEMS.register("entity_warper", () -> new EntitywarperItem());
    public static final Supplier<Item> VOID_ENTITY_WARPER = ITEMS.register("void_entity_warper", () -> new VoidEntityWarperItem());

    public static final Supplier<Item> NATURE_CRYSTAL = ITEMS.register("nature_crystal",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> VOID_GEM = ITEMS.register("void_gem",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DINOSAUR_BONE_SHIELD = ITEMS.register("dinosaur_bone_shield",
            () -> new CustomShieldItem());

    public static final Supplier<Item> SHULKER_SHIELD = ITEMS.register("shulker_shield",
            () -> new ShulkerShieldItem());
    public static final Supplier<Item> FROSTBITTEN_SHIELD = ITEMS.register("frostbitten_shield",
            () -> new FrostbittenShieldItem());
    // public static final Supplier<Item> ENDER_HAMMER = ITEMS.register("ender_hammer", () -> new TheEndersentItem());

    public static final Supplier<Item> MOSSY_HAMMER = ITEMS.register("mossy_hammer", () -> new MossyHammerItem(new Item.Properties()));


    public static final Supplier<Item> MOSSY_CHESTPLATE = ITEMS.register("mossy_chestplate",
            () -> new MossyChestplateItem(ModArmorMaterials.MOSSY, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.EPIC)));

    public static final Supplier<Item> WITHERED_RIBCAGE = ITEMS.register("withered_ribcage",
            () -> new WitheredRibcageItem(ModArmorMaterials.WITHER, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant().rarity(Rarity.EPIC)));
    public static final Supplier<Item> SHULKER_HELMET = ITEMS.register("shulker_helmet",
            () -> new ShulkerHelmetItem(ModArmorMaterials.SHULKER, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.EPIC)));

    public static final Supplier<Item> CHORUS_MASK = ITEMS.register("chorus_mask",
            () -> new ChorusHelmetItem(ModArmorMaterials.CHORUS, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.EPIC)));
    public static final Supplier<Item> DINOSAUR_BONE_CLUB = ITEMS.register("dinosaur_bone_club",
            () -> new DinosaurBoneClubItem(new Item.Properties()));

    public static final Supplier<Item> VOID_SWORD = ITEMS.register("void_sword", () -> new VoidSwordItem(ModToolTiers.VOID,new Item.Properties().rarity(Rarity.EPIC)));
    public static final Supplier<Item> FIERY_JAW = ITEMS.register("fiery_jaw", () -> new FieryJawItem(new Item.Properties()));

    public static final Supplier<Item> WARPED_MUSHROOM_CAP_PART = ITEMS.register("warped_mushroom_cap_part",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final Supplier<Item> OVERGROWN_COLOSSUS_SPAWN_EGG = ITEMS.register("overgrown_colossus_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Overgrown_colossus.get(), -6710887, -10053376, new Item.Properties()));
    public static final Supplier<Item> SKELORAPTOR_SPAWN_EGG = ITEMS.register("skeloraptor_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Skeleraptor.get(), -3355444, -10066330, new Item.Properties()));

    public static final Supplier<Item> MOSSY_GOLEM_SPAWN_EGG = ITEMS.register("mossy_golem_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Mossy_Golem.get(), -10066330, -16737997, new Item.Properties()));

    public static final Supplier<Item> ENDERSENT_SPAWN_EGG = ITEMS.register("endersent_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Endersent.get(), -16777216, -6750055, new Item.Properties()));

    public static final Supplier<Item> WARPED_FUNGUSSUS_SPAWN_EGG = ITEMS.register("warped_fungussus_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Warped_Fungussus.get(), -16737895, -26317, new Item.Properties()));

    public static final Supplier<Item> SKELETOSAURUS_SPAWN_EGG = ITEMS.register("skeletosaurus_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Skeletosaurus.get(), -3355444, -6710887, new Item.Properties()));
    public static final Supplier<Item> LAVA_EATER_SPAWN_EGG = ITEMS.register("lava_eater_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Lava_eater.get(), -13421773, -3381760, new Item.Properties()));

    public static final Supplier<Item> FROSTBITTEN_GOLEM_SPAWN_EGG = ITEMS.register("frostbitten_golem_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Frostbitten_Golem.get(), -9993839, -13382401, new Item.Properties()));

    public static final Supplier<Item> SHULKER_MIMIC_SPAWN_EGG = ITEMS.register("shulker_mimic_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Shulker_Mimic.get(), -7255407, -11259820, new Item.Properties()));

    public static final Supplier<Item> WITHERED_ABOMINATION_SPAWN_EGG = ITEMS.register("withered_abomination_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Withered_Abomination.get(), -15592942, -16777216, new Item.Properties()));
    public static final Supplier<Item> CHORUS_CANNON = ITEMS.register("chorus_cannon",
            () -> new ChorusCannonItem());

    public static final Supplier<Item> WITHERED_SCYTHE = ITEMS.register("withered_scythe",
            () -> new WitheredScytheItem(new Item.Properties().durability(700).rarity(Rarity.EPIC).fireResistant()));
    public static final Supplier<Item> CHORUSLING_SPAWN_EGG = ITEMS.register("chorusling_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Chorusling.get(), -13555405, -12111545, new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final Supplier<Item> AMBUSHER_SPAWN_EGG = ITEMS.register("ambusher_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Ambusher.get(), -10793146, -11189959, new Item.Properties()));

    public static final Supplier<Item> SPIKE_BUG_SPAWN_EGG = ITEMS.register("spike_bug_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Spiky_bug.get(), -11385026, -4212555, new Item.Properties().rarity(Rarity.EPIC)));
    public static final Supplier<Item> ANCIENT_GUARDIAN_SPAWN_EGG = ITEMS.register("ancient_guardian_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Ancient_Guardian.get(), -11385026, -8755361, new Item.Properties()));
    public static final Supplier<Item> CLOUD_GOLEM_SPAWN_EGG = ITEMS.register("cloud_golem_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Cloud_golem.get(), -1, -3342337, new Item.Properties()));

    public static final Supplier<Item> SPIKY_SHIELD = ITEMS.register("spiky_shield",
            () -> new SpikyShieldItem());
    public static final Supplier<Item> MONSTROUS_ANCHOR = ITEMS.register("monstrous_anchor",
            () -> new MonstrousAnchorItem(new Item.Properties()));
  //  public static final Supplier<Item> SHARP_BATTLE_AXE = ITEMS.register("sharp_battle_axe",
     //       () -> new SharpBattleAxeItem());
  //  public static final Supplier<Item> SHARP_SAI = ITEMS.register("sharp_sai",
          //  () -> new SharpSaiItem());
    // public static final Supplier<Item> REINFORCED_MONSTROUS_ANCHOR = ITEMS.register("reinforced_monstrous_anchor",
    //   () -> new SpearItem(new Item.Properties().durability(300)));
    public static final Supplier<Item> ANCIENT_SPIKE = ITEMS.register("ancient_spike",
            () -> new Item(new Item.Properties()));

    public static final Supplier<Item> ANCHOR_HANDLE = ITEMS.register("anchor_handle",
            () -> new AnchorHandleItem(new Item.Properties()));
    /**
     * public static final Supplier<Item> TEST_EYE = ITEMS.register("test_eye",
     * () -> new TestEyeItem(new Item.Properties()));
     */
    public static final Supplier<Item> EYE_OF_MANY_RIBS = ITEMS.register("eye_of_many_ribs",
            () -> new EyeOfManyRibsItem(new Item.Properties()));

    public static final Supplier<Item> EYE_OF_FROST = ITEMS.register("eye_of_frost",
            () -> new EyeOfFrostItem(new Item.Properties()));
    public static final Supplier<Item> EYE_OF_AIR = ITEMS.register("eye_of_air",
            () -> new EyeOfAirItem(new Item.Properties().fireResistant()));

    public static final Supplier<Item> EYE_OF_MOSS = ITEMS.register("eye_of_moss",
            () -> new EyeOfMossItem(new Item.Properties()));

    public static final Supplier<Item> EYE_OF_SHULKER = ITEMS.register("eye_of_shulker",
            () -> new EyeOfShulkerItem(new Item.Properties()));
    public static final Supplier<Item> EYE_OF_CHORUS = ITEMS.register("eye_of_chorus",
            () -> new EyeOfChorusItem(new Item.Properties()));
    public static final Supplier<Item> EYE_OF_MAGMA = ITEMS.register("eye_of_magma",
            () -> new EyeOfMagmaItem(new Item.Properties()));

    public static final Supplier<Item> EYE_OF_BONES = ITEMS.register("eye_of_bones",
            () -> new EyeOfBonesItem(new Item.Properties()));


    public static final Supplier<Item> EYE_OF_SOUL = ITEMS.register("eye_of_soul",
            () -> new EyeOfSoulItem(new Item.Properties()));
    public static final Supplier<Item> EYE_OF_GHOST = ITEMS.register("eye_of_ghost",
            () -> new EyeOfGhostItem(new Item.Properties()));

    public static final Supplier<Item> EYE_OF_ANNIHILATION = ITEMS.register("eye_of_annihilation",
            () -> new EyeOfAnnihilation(new Item.Properties()));

    // public static final Supplier<Item> AMBUSHERS_SKIN = ITEMS.register("ambushers_skin",
    //      () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FALLING_CLOUD_PROJECTILE = ITEMS.register("falling_cloud_projectile",
            () -> new Item(new Item.Properties()));

    public static final Supplier<Item> FALLING_CLOUD_SMALL_PROJECTILE = ITEMS.register("falling_cloud_small_projectile",
            () -> new Item(new Item.Properties()));

    public static final Supplier<Item> AIR_RUNE = ITEMS.register("air_rune",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final Supplier<Item> WAND_OF_CLOUDS = ITEMS.register("wand_of_clouds",
            () -> new WandOfCloudsItem(new Item.Properties().durability(2000).rarity(Rarity.EPIC)));//old 270
    public static final Supplier<Item> AXE_OF_LIGHTNING = ITEMS.register("axe_of_lightning",
            () -> new Axe_Of_LightningItem(ModToolTiers.Lightning, new Item.Properties().rarity(Rarity.EPIC)));

    public static final Supplier<Item> MOLTEN_METAL_INGOT = ITEMS.register("molten_metal_ingot",
            () -> new MoltenMetalIngotItem());


    //  public static final Supplier<Item> SPIKY_SPEAR = ITEMS.register("spiky_spear",
    //      () -> new SpearItem(new Item.Properties().durability(270)));
    public static final Supplier<Item> ATMOSPHERIC_BOOTS = ITEMS.register("atmospheric_boots",
            () -> new AtmosphericBootsItem(ModArmorMaterials.CLOUDY, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant().rarity(Rarity.EPIC)));

    public static final Supplier<Item> FIERY_BOOTS = ITEMS.register("fiery_boots",
            () -> new FieryBootsItem(ModArmorMaterials.BURNT, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.EPIC)));
    //   public static final Supplier<Item> DINOSAUR_BONE_HELMET = ITEMS.register("dinosaur_bone_helmet",
    //       () -> new DinosaurBoneHelmet(DinosaurBoneArmorMaterial.DINOSAUR_BONE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final Supplier<Item> HAUNTED_KNIGHT_SPAWN_EGG = ITEMS.register("haunted_knight_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Haunted_Knight.get(), -10066330, -3355444, new Item.Properties()));
    public static final Supplier<Item> HAUNTED_GUARD_SPAWN_EGG = ITEMS.register("haunted_guard_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Haunted_Guard.get(), -10066330, -13261, new Item.Properties()));
    public static final Supplier<Item> POSESSED_PALADIN_SPAWN_EGG = ITEMS.register("posessed_paladin_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Posessed_Paladin.get(), -10066330, -10092391, new Item.Properties()));

    public static final Supplier<Item> BEHEADED_KNIGHT_SPAWN_EGG = ITEMS.register("beheaded_knight_spawn_egg",
            () -> new SpawnEggItem(ModEntities.BEHEADED_KNIGHT.get(), 0x758085, 0xc73333, new Item.Properties()));

    public static final Supplier<Item> RESURRECTED_KNIGHT_SPAWN_EGG = ITEMS.register("resurrected_knight_spawn_egg",
            () -> new SpawnEggItem(ModEntities.RESURRECTED_KNIGHT.get(), 0x758085, 0x349e9c, new Item.Properties()));

    public static final Supplier<Item> GOLDEN_HALBERT = ITEMS.register("golden_halbert",
            () -> new GoldenHalbertItem(new Item.Properties()));
    public static final Supplier<Item> SOUL_GREAT_SWORD = ITEMS.register("soul_great_sword",
            () -> new SoulGreatSwordItem(new Item.Properties()));
    public static final Supplier<Item> KNIGHTS_SWORD = ITEMS.register("knights_sword",
            () -> new KnightsSwordsItem(new Item.Properties()));
    public static final Supplier<Item> CORRUPTED_SOUL = ITEMS.register("corrupted_soul",
            () -> new CorruptedSoulItem(new Item.Properties()));
    public static final Supplier<Item> KNIGHTS_SWORD_BLADE_HALF = ITEMS.register("knights_sword_blade_half",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> LONG_STICK_HALF = ITEMS.register("long_stick_half",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> KNIGHT_SUMMONER = ITEMS.register("knight_summoner",
            () -> new KnightSummonerItem(new Item.Properties()));
    public static final Supplier<Item> DEACTIVATED_KNIGHT_SUMMONER = ITEMS.register("deactivated_knight_summoner",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DEACTIVATED_GUARD_SUMMONER = ITEMS.register("deactivated_guard_summoner",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> GUARD_SUMMONER = ITEMS.register("guard_summoner",
            () -> new GuardSummonerItem(new Item.Properties()));
    public static final Supplier<Item> PRIMAL_ICE_SHARD = ITEMS.register("primal_ice_shard",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> WITHERED_HORN = ITEMS.register("withered_horn",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final Supplier<Item> GUIDE_BOOK = ITEMS.register("guide_book",
            () -> new WritableBookItem(new Item.Properties()));
    public static final Supplier<Item> CHISELED_ANCIENT_DRIPTONE_SHARD = ITEMS.register("chiseled_ancient_dripstone_shard",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HAND_CANNON = ITEMS.register("sand_cannon",
            () -> new HandCannonItem());
    //public static final Supplier<Item> BLASTPROOF_METAL_SCRAP = ITEMS.register("blastproof_metal_scrap",
    //      () -> new Item(new Item.Properties()));
    public static final Supplier<Item> ANCIENT_SANDSTONE_SHARD = ITEMS.register("ancient_sandstone_shard",
            () -> new AncientSandstoneShardItem(new Item.Properties()));
    // public static final Supplier<Item> ANCIENT_DETONATOR = ITEMS.register("ancient_detonator",
    //      () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CRYSTAL_OF_SANDSTORM = ITEMS.register("crystal_of_sandstorm",
            () -> new Item(new Item.Properties()));
    // public static final Supplier<Item> EXPLOSIVE_SAND = ITEMS.register("explosive_sand",
    //       () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BLASTPROOF_HELMET = ITEMS.register("blastproof_helmet",
            () -> new BlastProofHelmet(ModArmorMaterials.BLASTPROOF, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.EPIC)));
    public static final Supplier<Item> BOMBER_SPAWN_EGG = ITEMS.register("bomber_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Bomber.get(), -2240890, -6383260, new Item.Properties()));
    public static final Supplier<Item> DUNE_SENTINEL_SPAWN_EGG = ITEMS.register("dune_sentinel_spawn_egg",
            () -> new SpawnEggItem(ModEntities.BlastCannon.get(), -8160164, -3554434, new Item.Properties()));
    //  public static final Supplier<Item> SICKLE_OF_HARVEST = ITEMS.register("sickle_of_harvest",
    //        () -> new SickleOfHarvestItem(new Item.Properties().durability(300)));
    public static final Supplier<Item> EYE_OF_SANDSTORM = ITEMS.register("eye_of_sandstorm",
            () -> new EyeOfSandstorm(new Item.Properties()));
    public static final Supplier<Item> STRATLING_SPAWN_EGG = ITEMS.register("stratling_spawn_egg",
            () -> new SpawnEggItem(ModEntities.Stratling.get(), -3083521, -8673319, new Item.Properties()));


    public static final Supplier<Item> HOVERING_HURRICANE_SPAWN_EGG = ITEMS.register("hovering_hurricane_spawn_egg",
            () -> new SpawnEggItem(ModEntities.HoverinngHurricane.get(), -6035969, -11635812, new Item.Properties()));
    // public static final Supplier<Item> CRYSTAL_OF_AIR = ITEMS.register("crystal_of_air",
    //        () -> new MoltenMetalIngotItem());
    //public static final Supplier<Item> HEART_OF_TORNADO = ITEMS.register("heart_of_tornado",
    //    () -> new HeartOfTornadoItem());

    public static final Supplier<Item> CLOUD_ROD = ITEMS.register("cloud_rod",
            () -> new Item(new Item.Properties()));
    // public static final Supplier<Item> BRONZITE_INGOT = ITEMS.register("bronzite_ingot",
    //    () -> new Item(new Item.Properties()));
    public static final Supplier<Item> MUSIC_DISC_CLOUD_GOLEM = ITEMS.register("music_disc_advance",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
                    .jukeboxPlayable(ModSounds.CLOUD_GOLEM_MUSIC_KEY)));

    public static final Supplier<Item> MUSIC_DISC_OBLITERATOR = ITEMS.register("music_disc_obliterator",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
                    .jukeboxPlayable(ModSounds.OBLITERATOR_MONO_KEY)));

    public static final Supplier<Item> MUSIC_DISC_POSSESSED_PALADIN = ITEMS.register("music_disc_possessed_paladin",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
                    .jukeboxPlayable(ModSounds.POSSESSED_PALADIN_MONO_KEY)));

    public static final Supplier<Item> LIVING_STONE = ITEMS.register("totem_of_moss",
            () -> new LivingStoneItem(new Item.Properties()));
    public static final Supplier<Item> ENDERITIUM_INGOT = ITEMS.register("enderitium_ingot",
            () -> new Item(new Item.Properties()));
    public static final Supplier<Item> PORTAL_SHARD = ITEMS.register("portal_shard",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final Supplier<Item> ENDIRITIUM_GEM = ITEMS.register("enderitium_gem",
            () -> new Item(new Item.Properties()));
    // public static final Supplier<Item> RAW_ENDIRITIUM = ITEMS.register("raw_enderitium",
    //     () -> new Item(new Item.Properties()));
    public static final Supplier<Item> ENDIRITIUM_SWORD = ITEMS.register("enderitium_sword", () ->
            new SwordItem(ModToolTiers.ENDIRITIUM, (new Item.Properties()).attributes(SwordItem.createAttributes(ModToolTiers.ENDIRITIUM, 3, -2.4F))));

    public static final Supplier<Item> ENDIRITIUM_HOE = ITEMS.register("enderitium_hoe", () ->
            new HoeItem(ModToolTiers.ENDIRITIUM, (new Item.Properties()).attributes(HoeItem.createAttributes(ModToolTiers.ENDIRITIUM, -3, -2.4F))));

    public static final Supplier<Item> ENDIRITIUM_SHOVEL = ITEMS.register("enderitium_shovel", () ->
            new ShovelItem(ModToolTiers.ENDIRITIUM, (new Item.Properties()).attributes(ShovelItem.createAttributes(ModToolTiers.ENDIRITIUM, 1.5f, -3.0f))));
    public static final Supplier<Item> ENDIRITIUM_AXE = ITEMS.register("enderitium_axe", () -> new
            AxeItem(ModToolTiers.ENDIRITIUM, (new Item.Properties()).attributes(AxeItem.createAttributes(ModToolTiers.ENDIRITIUM, 5, -3))));
    public static final Supplier<Item> ENDIRITIUM_PICKAXE = ITEMS.register("enderitium_pickaxe", () ->
            new PickaxeItem(ModToolTiers.ENDIRITIUM, (new Item.Properties()).attributes(SwordItem.createAttributes(ModToolTiers.ENDIRITIUM, 1, -2.8F))));


    // public static final Supplier<Item> BRONZITE_SWORD = ITEMS.register("bronzite_sword", () -> new SwordItem(ModToolTiers.BRONZITE,  5, -2.4F, new Item.Properties().rarity(Rarity.COMMON)));

    //  public static final Supplier<Item> BRONZITE_AXE = ITEMS.register("bronzite_axe", () -> new SwordItem(ModToolTiers.BRONZITE,  7, -2.8F, new Item.Properties().rarity(Rarity.COMMON)));


    public static final Supplier<Item> THE_OBLITERATOR_SPAWN_EGG = ITEMS.register("the_obliterator_spawn_egg",
            () -> new SpawnEggItem(ModEntities.THE_OBLITERATOR.get(), 0x427367, 0xb5e45a, new Item.Properties()));

    public static final Supplier<Item> FLAME_DRIFTER_SPAWN_EGG = ITEMS.register("flame_drifter_spawn_egg",
            () -> new SpawnEggItem(ModEntities.FLAME_DRIFTER.get(), 0xd7da94, 0x88c35a, new Item.Properties()));
    public static final Supplier<Item> FLAMEBORN_GUARD_SPAWN_EGG = ITEMS.register("flameborn_guard_spawn_egg",
            () -> new SpawnEggItem(ModEntities.FLAMEBORN_GUARD.get(), 0x161616, 0x427367, new Item.Properties()));
    public static final Supplier<Item> FLAMEBORN_WARRIOR_SPAWN_EGG = ITEMS.register("flameborn_warrior_spawn_egg",
            () -> new SpawnEggItem(ModEntities.FLAMEBORN_WARRIOR.get(), 0x161616, 0x579143, new Item.Properties()));
    public static final Supplier<Item> ANNIHILATION_PURSUER_SPAWN_EGG = ITEMS.register("annihilation_pursuer_spawn_egg",
            () -> new SpawnEggItem(ModEntities.ANNIHILATION_PURSUER.get(), 0x132e37, 0x579143, new Item.Properties()));
    public static final Supplier<Item> ATOM_SPLITTER = ITEMS.register("atom_splitter",
            () -> new AtomSplitterItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static final Supplier<Item> BOTTLE_OF_ANNIHILATION = ITEMS.register("bottle_of_annihilation",
            () -> new BottleOfAnnihilationItem(new Item.Properties().stacksTo(64).rarity(Rarity.RARE)));

    public static final Supplier<Item> BUCKLER_OF_ANNIHILATION = ITEMS.register("buckler_of_annihilation",
            () -> new BucklerOfAnnihilationItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final Supplier<Item> EYE_CRYSTAL = ITEMS.register("eye_crystal",
            () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.EPIC)));

    public static final Supplier<Item> TESSERACT = ITEMS.register("the_tesseract",
            () -> new TheTesseractItem(new Item.Properties()));

    //public static final Supplier<Item> CLOUD_BREAD = ITEMS.register("cloud_bread",
    //    () -> new Item(new Item.Properties().food(ModFoods.CLOUD_BREAD).stacksTo(64).rarity(Rarity.COMMON)));

    //public static final Supplier<Item> DARK_CLOUD_BREAD = ITEMS.register("dark_cloud_bread",
    //        () -> new Item(new Item.Properties().food(ModFoods.CLOUD_BREAD).stacksTo(64).rarity(Rarity.UNCOMMON)));


    public static final Supplier<Item> ENDERITIUM_UPGRADE_TEMPLATE = ITEMS.register("enderitium_upgrade_smithing_template",
            () -> new EnderitiumSmithingTemplate(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> WANDERING_EYE_SPAWN_EGG = ITEMS.register("wandering_eye_spawn_egg",
            () -> new SpawnEggItem(ModEntities.WANDERING_EYE.get(), 0xd5da94, 0x659b7d, new Item.Properties()));

    public static final Supplier<Item> FRACTURED_APOSTLE_SPAWN_EGG = ITEMS.register("fractured_apostle_spawn_egg",
            () -> new SpawnEggItem(ModEntities.FRACTURED_APOSTLE.get(), 0xc73333, 0xdba213, new Item.Properties()));

    // Map<ArmorItem.Type, Supplier<AnnihilatorArmorItem>> ANNIHILATOR_ARMOR = AbstractArmorItem.createRegistry(ITEMS, "annihilator", AnnihilatorArmorItem::new);
    public static final Supplier<Item> ANNIHILATOR_HELMET = ITEMS.register("annihilator_helmet",
            () -> new AnnihilatorArmorItem(AnnihilatorHelmetItem.Type.HELMET));
    public static final Supplier<Item> ANNIHILATOR_CHESTPLATE = ITEMS.register("annihilator_chestplate",
            () -> new AnnihilatorArmorItem(AnnihilatorChestplateItem.Type.CHESTPLATE));

    public static final Supplier<Item> ANNIHILATOR_LEGGINGS = ITEMS.register("annihilator_leggings",
            () -> new AnnihilatorArmorItem(AnnihilatorArmorItem.Type.LEGGINGS));

    public static final Supplier<Item> ANNIHILATOR_BOOTS = ITEMS.register("annihilator_boots",
            () -> new AnnihilatorArmorItem(AnnihilatorArmorItem.Type.BOOTS));

    public static final Supplier<Item> SOMBER_KEY = ITEMS.register("somber_key",
            ()-> new SomberKeyItem(new Item.Properties()));

    public static final Supplier<Item> SOMBER_KEY_RING = ITEMS.register("somber_key_ring",
            () -> new SomberKeyPart(new Item.Properties()));

    public static final Supplier<Item> SOMBER_KEY_BLADE = ITEMS.register("somber_key_blade",
            () -> new SomberKeyPart(new Item.Properties()));

    //  public static final Supplier<Item> BOTTLED_LIGHTNING = ITEMS.register("bottled_lightning",
    //    () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON)));

    public static final Supplier<Item> ANNIHILATOR_UPGRADE_TEMPLATE = ITEMS.register("annihilator_upgrade_smithing_template",
            () -> new AnnihilatorSmithingTemplate(new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON)));


    public static final Supplier<Item> SHATTERED_GREATSWORD = ITEMS.register("shattered_greatsword",
            () -> new ShatteredGreatSwordItem(new Item.Properties()));


    public static final Supplier<Item> RESURRECTED_JAVELIN = ITEMS.register("resurrected_javelin",
            () -> new ResurrectedJavelinItem(ModToolTiers.SHATTERED_GREATSWORD, 7, -2.8f, new Item.Properties().rarity(Rarity.EPIC)));


    public static final Supplier<Item> METAL_DEBRIS = ITEMS.register("metal_debris",
            () -> new Item(new Item.Properties()));

    public static final Supplier<Item> FRACTURED_SPAWN_EGG = ITEMS.register("fractured_spawn_egg",
            () -> new SpawnEggItem(ModEntities.FRACTURED.get(), 0x28707f, 0x39bec5, new Item.Properties()));

}

