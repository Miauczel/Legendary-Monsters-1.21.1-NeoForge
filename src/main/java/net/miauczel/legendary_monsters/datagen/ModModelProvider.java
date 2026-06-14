package net.miauczel.legendary_monsters.datagen;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.block.ModBlocks;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModModelProvider extends ItemModelProvider {

    private static final Map<ResourceKey<TrimMaterial>, Float> TRIM_MATERIALS = new LinkedHashMap<>();

    static {
        TRIM_MATERIALS.put(TrimMaterials.QUARTZ, 0.1F);
        TRIM_MATERIALS.put(TrimMaterials.IRON, 0.2F);
        TRIM_MATERIALS.put(TrimMaterials.NETHERITE, 0.3F);
        TRIM_MATERIALS.put(TrimMaterials.REDSTONE, 0.4F);
        TRIM_MATERIALS.put(TrimMaterials.COPPER, 0.5F);
        TRIM_MATERIALS.put(TrimMaterials.GOLD, 0.6F);
        TRIM_MATERIALS.put(TrimMaterials.EMERALD, 0.7F);
        TRIM_MATERIALS.put(TrimMaterials.DIAMOND, 0.8F);
        TRIM_MATERIALS.put(TrimMaterials.LAPIS, 0.9F);
        TRIM_MATERIALS.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LegendaryMonsters.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        trimmedArmorItem((DeferredItem<? extends Item>) ModItems.ATMOSPHERIC_BOOTS);
        trimmedArmorItem((DeferredItem<? extends Item>) ModItems.MOSSY_CHESTPLATE);
        trimmedArmorItem((DeferredItem<? extends Item>) ModItems.FIERY_BOOTS);
        trimmedArmorItem((DeferredItem<? extends Item>) ModItems.BLASTPROOF_HELMET);
        trimmedArmorItem((DeferredItem<? extends Item>) ModItems.CHORUS_MASK);
        trimmedArmorItem((DeferredItem<? extends Item>) ModItems.WITHERED_RIBCAGE);

        wallItem((DeferredBlock<? extends Block>) ModBlocks.ANCIENT_DRIPSTONE_BRICK_WALL, (DeferredBlock<? extends Block>) ModBlocks.ANCIENT_DRIPSTONE_BRICKS);
        evenSimplerBlockItem((DeferredBlock<? extends Block>) ModBlocks.ANCIENT_DRIPSTONE_BRICK_STAIRS);
        evenSimplerBlockItem((DeferredBlock<? extends Block>) ModBlocks.ANCIENT_DRIPSTONE_BRICK_SLAB);

        wallItem((DeferredBlock<? extends Block>) ModBlocks.ANCIENT_DRIPSTONE_TILE_WALL, (DeferredBlock<? extends Block>) ModBlocks.ANCIENT_DRIPSTONE_TILES);
        wallItem((DeferredBlock<? extends Block>) ModBlocks.ENDERSTONE_BRICK_WALL, (DeferredBlock<? extends Block>) ModBlocks.ENDERSTONE_BRICKS);
        wallItem((DeferredBlock<? extends Block>) ModBlocks.SMOOTH_ENDERSTONE_WALL, (DeferredBlock<? extends Block>) ModBlocks.SMOOTH_ENDERSTONE);
        wallItem((DeferredBlock<? extends Block>) ModBlocks.COBBLED_ENDERSTONE_WALL, (DeferredBlock<? extends Block>) ModBlocks.COBBLED_ENDERSTONE);
        wallItem((DeferredBlock<? extends Block>) ModBlocks.ENDERSTONE_WALL, (DeferredBlock<? extends Block>) ModBlocks.ENDERSTONE);

        evenSimplerBlockItem((DeferredBlock<? extends Block>) ModBlocks.ANCIENT_DRIPSTONE_TILE_STAIRS);
        evenSimplerBlockItem((DeferredBlock<? extends Block>) ModBlocks.ANCIENT_DRIPSTONE_TILE_SLAB);
        evenSimplerBlockItem((DeferredBlock<? extends Block>) ModBlocks.ENDERSTONE_BRICK_SLAB);
        evenSimplerBlockItem((DeferredBlock<? extends Block>) ModBlocks.ENDERSTONE_BRICK_STAIRS);
    }

    private void trimmedArmorItem(DeferredItem<? extends Item> itemRegistryObject) {
        Item item = itemRegistryObject.get();

        if (!(item instanceof ArmorItem armorItem)) {
            return;
        }

        String itemName = itemRegistryObject.getId().getPath();

        String armorType = switch (armorItem.getEquipmentSlot()) {
            case HEAD -> "helmet";
            case CHEST -> "chestplate";
            case LEGS -> "leggings";
            case FEET -> "boots";
            default -> throw new IllegalStateException(
                    "Unsupported armor slot for trim model: " + armorItem.getEquipmentSlot()
            );
        };

        ItemModelBuilder baseModel = this.withExistingParent(itemName, mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + itemName));

        for (Map.Entry<ResourceKey<TrimMaterial>, Float> entry : TRIM_MATERIALS.entrySet()) {
            ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
            float trimValue = entry.getValue();

            String trimMaterialName = trimMaterial.location().getPath();

            String trimModelName = itemName + "_" + trimMaterialName + "_trim";

            ResourceLocation trimTexture = ResourceLocation.fromNamespaceAndPath(
                    "minecraft",
                    "trims/items/" + armorType + "_trim_" + trimMaterialName
            );

            ResourceLocation trimModelLocation = modLoc("item/" + trimModelName);

            existingFileHelper.trackGenerated(
                    trimTexture,
                    PackType.CLIENT_RESOURCES,
                    ".png",
                    "textures"
            );

            getBuilder(trimModelName)
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", modLoc("item/" + itemName))
                    .texture("layer1", trimTexture);

            baseModel.override()
                    .predicate(mcLoc("trim_type"), trimValue)
                    .model(new ModelFile.UncheckedModelFile(trimModelLocation))
                    .end();
        }
    }

    private ItemModelBuilder simpleItem(DeferredItem<? extends Item> item) {
        String itemName = item.getId().getPath();

        return withExistingParent(itemName, mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + itemName));
    }

    public void evenSimplerBlockItem(DeferredBlock<? extends Block> block) {
        String blockName = block.getId().getPath();

        this.withExistingParent(blockName, modLoc("block/" + blockName));
    }

    public void trapdoorItem(DeferredBlock<? extends Block> block) {
        String blockName = block.getId().getPath();

        this.withExistingParent(blockName, modLoc("block/" + blockName + "_bottom"));
    }

    public void fenceItem(
            DeferredBlock<? extends Block> block,
            DeferredBlock<? extends Block> baseBlock
    ) {
        String blockName = block.getId().getPath();
        String baseBlockName = baseBlock.getId().getPath();

        this.withExistingParent(blockName, mcLoc("block/fence_inventory"))
                .texture("texture", modLoc("block/" + baseBlockName));
    }

    public void buttonItem(
            DeferredBlock<? extends Block> block,
            DeferredBlock<? extends Block> baseBlock
    ) {
        String blockName = block.getId().getPath();
        String baseBlockName = baseBlock.getId().getPath();

        this.withExistingParent(blockName, mcLoc("block/button_inventory"))
                .texture("texture", modLoc("block/" + baseBlockName));
    }

    public void wallItem(
            DeferredBlock<? extends Block> block,
            DeferredBlock<? extends Block> baseBlock
    ) {
        String blockName = block.getId().getPath();
        String baseBlockName = baseBlock.getId().getPath();

        this.withExistingParent(blockName, mcLoc("block/wall_inventory"))
                .texture("wall", modLoc("block/" + baseBlockName));
    }

    private ItemModelBuilder simpleBlockItem(DeferredBlock<? extends Block> block) {
        String blockName = block.getId().getPath();

        return withExistingParent(blockName, mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + blockName));
    }
}