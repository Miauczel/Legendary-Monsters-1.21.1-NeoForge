package net.miauczel.legendary_monsters.block;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.block.custom.*;
import net.miauczel.legendary_monsters.block.custom.SoulCandle.SoulCandleBlock;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final net.neoforged.neoforge.registries.DeferredRegister<Block> BLOCKS =
            net.neoforged.neoforge.registries.DeferredRegister.createBlocks(LegendaryMonsters.MOD_ID);

    public static final Supplier<Block> ANCIENT_DRIPSTONE_BRICKS = registerBlock("ancient_dripstone_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)
                    .strength(2f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> CHISELED_ANCIENT_DRIPSTONE_BRICKS = registerBlock("chiseled_ancient_dripstone_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)
                    .strength(2f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> ANCIENT_DRIPSTONE_BRICK_PILLAR = registerBlock("ancient_dripstone_brick_pillar",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)
                    .strength(2f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> ANCIENT_DRIPSTONE_BLOCK = registerBlock("ancient_dripstone_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)
                    .strength(2f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> ANCIENT_DRIPSTONE_TILES = registerBlock("ancient_dripstone_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)
                    .strength(2f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> ANCIENT_DRIPSTONE_BRICK_WALL = registerBlock("ancient_dripstone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)
                    .strength(2f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> ANCIENT_DRIPSTONE_BRICK_SLAB = registerBlock("ancient_dripstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)
                    .strength(2f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> ANCIENT_DRIPSTONE_BRICK_STAIRS = registerBlock("ancient_dripstone_brick_stairs",
            () -> new StairBlock(ModBlocks.ANCIENT_DRIPSTONE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)));

    public static final Supplier<Block> ANCIENT_DRIPSTONE_TILE_SLAB = registerBlock("ancient_dripstone_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)
                    .strength(2f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> ANCIENT_DRIPSTONE_TILE_STAIRS = registerBlock("ancient_dripstone_tile_stairs",
            () -> new StairBlock(ModBlocks.ANCIENT_DRIPSTONE_TILES.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)));

    public static final Supplier<Block> SKELETOSAURUS_EGG = registerBlock("skeletosaurus_egg",
            () -> new SkeletosaurusEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.5F).sound(SoundType.METAL).noOcclusion()));

    public static final Supplier<Block> ANCIENT_DRIPSTONE_TILE_WALL = registerBlock("ancient_dripstone_tile_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK)
                    .strength(2f).requiresCorrectToolForDrops()));

    public static final Supplier<Block> CLOUD_BLOCK = registerBlock("cloud_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).sound(SoundType.WOOL)
                    .strength(1f)));

    public static final Supplier<Block> CLOUD_BRICKS = registerBlock("cloud_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).sound(SoundType.WOOL)
                    .strength(1f)));

    public static final Supplier<Block> POLISHED_DIORITE_PILLAR = registerBlock("polished_diorite_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE).sound(SoundType.STONE)
                    .strength(1f)));

    public static final Supplier<Block> CHISELED_DIORITE = registerBlock("chiseled_diorite",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DIORITE).sound(SoundType.STONE)
                    .strength(1f)));


    public static float enderstoneBlockStrenght = 2.5f;
    public static final Supplier<Block> ENDERITIUM_BLOCK = registerBlock("enderitium_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)
                    .strength(50)));

    public static final Supplier<Block> ENDERSTONE_BRICKS = registerBlock("enderstone_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> ENDERSTONE_BRICK_STAIRS = registerBlock("enderstone_brick_stairs",
            () -> new StairBlock(ModBlocks.ENDERSTONE_BRICKS.get().defaultBlockState(),
                    (BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                            .strength(enderstoneBlockStrenght))));

    public static final Supplier<Block> ENDERSTONE_BRICK_SLAB = registerBlock("enderstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> ENDERSTONE_BRICK_WALL = registerBlock("enderstone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> CHISELED_ENDERSTONE_BRICKS = registerBlock("chiseled_enderstone_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> CHISELED_ENDERSTONE_PILLAR = registerBlock("chiseled_enderstone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> ENDERSTONE_PILLAR = registerBlock("enderstone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));
    public static final Supplier<Block> ENDERITIUM_ORE = registerBlock("enderitium_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));
    public static final Supplier<Block> ENDERSTONE = registerBlock("enderstone",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));
    public static final Supplier<Block> COBBLED_ENDERSTONE = registerBlock("cobbled_enderstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> SMOOTH_ENDERSTONE = registerBlock("smooth_enderstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));


    public static final Supplier<Block> SMOOTH_ENDERSTONE_STAIRS = registerBlock("smooth_enderstone_stairs",
            () -> new StairBlock(ModBlocks.ENDERSTONE_BRICKS.get().defaultBlockState(),
                    (BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                            .strength(enderstoneBlockStrenght))));

    public static final Supplier<Block> SMOOTH_ENDERSTONE_SLAB = registerBlock("smooth_enderstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> SMOOTH_ENDERSTONE_WALL = registerBlock("smooth_enderstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> COBBLED_ENDERSTONE_STAIRS = registerBlock("cobbled_enderstone_stairs",
            () -> new StairBlock(ModBlocks.ENDERSTONE_BRICKS.get().defaultBlockState(),
                    (BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                            .strength(enderstoneBlockStrenght))));

    public static final Supplier<Block> COBBLED_ENDERSTONE_SLAB = registerBlock("cobbled_enderstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> COBBLED_ENDERSTONE_WALL = registerBlock("cobbled_enderstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> ENDERSTONE_STAIRS = registerBlock("enderstone_stairs",
            () -> new StairBlock(ModBlocks.ENDERSTONE_BRICKS.get().defaultBlockState(),
                    (BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                            .strength(enderstoneBlockStrenght))));

    public static final Supplier<Block> ENDERSTONE_SLAB = registerBlock("enderstone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> ENDERSTONE_WALL = registerBlock("enderstone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));
    public static final Supplier<Block> ANNIHILATION_LANTERN = registerBlock("annihilation_lantern",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SEA_LANTERN).sound(SoundType.STONE)
                    .strength(enderstoneBlockStrenght)));

    public static final Supplier<Block> ENDER_ANCHOR = registerBlock("ender_anchor",
            () -> new EnderAnchorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RESPAWN_ANCHOR).noOcclusion().sound(SoundType.NETHERITE_BLOCK)));
    public static final Supplier<Block> TELEPORT_MACHINE = registerBlock("teleport_machine",
            () -> new TeleportMachineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_PORTAL_FRAME).noOcclusion().sound(SoundType.NETHERITE_BLOCK)));

    public static final Supplier<Block> SWINGING_AXE = registerBlock("swinging_axe",
            () -> new SwingingAxeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_PORTAL_FRAME).noOcclusion().sound(SoundType.NETHERITE_BLOCK)));

    public static final Supplier<Block> SOMBER_TRAPDOOR = registerBlock("somber_trapdoor",
            () -> new SomberTrapdoorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).sound(SoundType.NETHERITE_BLOCK)));

    public static final Supplier<Block> INDESTRUCTIBLE_BLOCK = registerBlock("indestructible_block",
            () -> new IndestructibleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).sound(SoundType.STONE)));

    public static final Supplier<Block> SMALL_SPIKE_TRAP_BLOCK = registerBlock("small_spike_trap_block",
            () -> new SmallSpikeTrapBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_WOOD).sound(SoundType.WOOD)));

    public static final Supplier<Block> BIG_SPIKE_TRAP_BLOCK = registerBlock("big_spike_trap_block",
            () -> new BigSpikeTrapBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_WOOD).sound(SoundType.WOOD)));

    public static final Supplier<Block> SOUL_CANDLE = registerBlock("soul_candle",
            () -> new SoulCandleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE).sound(SoundType.CANDLE)));


    private static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        Supplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> Supplier<Item> registerBlockItem(String name, Supplier<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
