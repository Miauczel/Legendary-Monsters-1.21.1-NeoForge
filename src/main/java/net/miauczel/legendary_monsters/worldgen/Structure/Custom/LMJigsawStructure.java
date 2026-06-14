package net.miauczel.legendary_monsters.worldgen.Structure.Custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.miauczel.legendary_monsters.worldgen.Structure.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.List;
import java.util.Optional;
public class LMJigsawStructure extends Structure {
    public static final DimensionPadding DEFAULT_DIMENSION_PADDING = DimensionPadding.ZERO;
    public static final LiquidSettings DEFAULT_LIQUID_SETTINGS = LiquidSettings.APPLY_WATERLOGGING;

    public static final int MAX_TOTAL_STRUCTURE_RANGE = 500;
    public static final int MIN_DEPTH = 0;
    public static final int MAX_DEPTH = 40;

    public static final MapCodec<LMJigsawStructure> CODEC = RecordCodecBuilder
            .<LMJigsawStructure>mapCodec(instance -> instance.group(
                    Structure.settingsCodec(instance),

                    StructureTemplatePool.CODEC
                            .fieldOf("start_pool")
                            .forGetter((LMJigsawStructure structure) -> structure.startPool),

                    ResourceLocation.CODEC
                            .optionalFieldOf("start_jigsaw_name")
                            .forGetter((LMJigsawStructure structure) -> structure.startJigsawName),

                    Codec.intRange(MIN_DEPTH, MAX_DEPTH)
                            .fieldOf("size")
                            .forGetter((LMJigsawStructure structure) -> structure.maxDepth),

                    HeightProvider.CODEC
                            .fieldOf("start_height")
                            .forGetter((LMJigsawStructure structure) -> structure.startHeight),

                    Codec.BOOL
                            .fieldOf("use_expansion_hack")
                            .forGetter((LMJigsawStructure structure) -> structure.useExpansionHack),

                    Heightmap.Types.CODEC
                            .optionalFieldOf("project_start_to_heightmap")
                            .forGetter((LMJigsawStructure structure) -> structure.projectStartToHeightmap),

                    Codec.intRange(1, MAX_TOTAL_STRUCTURE_RANGE)
                            .fieldOf("max_distance_from_center")
                            .forGetter((LMJigsawStructure structure) -> structure.maxDistanceFromCenter),

                    PoolAliasBinding.CODEC
                            .listOf()
                            .optionalFieldOf("pool_aliases", List.of())
                            .forGetter((LMJigsawStructure structure) -> structure.poolAliases),

                    DimensionPadding.CODEC
                            .optionalFieldOf("dimension_padding", DEFAULT_DIMENSION_PADDING)
                            .forGetter((LMJigsawStructure structure) -> structure.dimensionPadding),

                    LiquidSettings.CODEC
                            .optionalFieldOf("liquid_settings", DEFAULT_LIQUID_SETTINGS)
                            .forGetter((LMJigsawStructure structure) -> structure.liquidSettings)
            ).apply(instance, LMJigsawStructure::new))
            .validate(LMJigsawStructure::verifyRange);

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final List<PoolAliasBinding> poolAliases;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;

    private static DataResult<LMJigsawStructure> verifyRange(LMJigsawStructure structure) {
        int terrainAdaptationRange = switch (structure.terrainAdaptation()) {
            case NONE -> 0;
            case BURY, BEARD_THIN, BEARD_BOX, ENCAPSULATE -> 12;
        };

        if (structure.maxDistanceFromCenter + terrainAdaptationRange > MAX_TOTAL_STRUCTURE_RANGE) {
            return DataResult.error(() ->
                    "Structure size including terrain adaptation must not exceed: "
                            + MAX_TOTAL_STRUCTURE_RANGE
            );
        }

        return DataResult.success(structure);
    }

    public LMJigsawStructure(
            StructureSettings settings,
            Holder<StructureTemplatePool> startPool,
            Optional<ResourceLocation> startJigsawName,
            int maxDepth,
            HeightProvider startHeight,
            boolean useExpansionHack,
            Optional<Heightmap.Types> projectStartToHeightmap,
            int maxDistanceFromCenter,
            List<PoolAliasBinding> poolAliases,
            DimensionPadding dimensionPadding,
            LiquidSettings liquidSettings
    ) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.poolAliases = poolAliases;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
    }

    public LMJigsawStructure(
            StructureSettings settings,
            Holder<StructureTemplatePool> startPool,
            int maxDepth,
            HeightProvider startHeight,
            boolean useExpansionHack,
            Heightmap.Types projectStartToHeightmap
    ) {
        this(
                settings,
                startPool,
                Optional.empty(),
                maxDepth,
                startHeight,
                useExpansionHack,
                Optional.of(projectStartToHeightmap),
                MAX_TOTAL_STRUCTURE_RANGE,
                List.of(),
                DEFAULT_DIMENSION_PADDING,
                DEFAULT_LIQUID_SETTINGS
        );
    }

    public LMJigsawStructure(
            StructureSettings settings,
            Holder<StructureTemplatePool> startPool,
            int maxDepth,
            HeightProvider startHeight,
            boolean useExpansionHack
    ) {
        this(
                settings,
                startPool,
                Optional.empty(),
                maxDepth,
                startHeight,
                useExpansionHack,
                Optional.empty(),
                MAX_TOTAL_STRUCTURE_RANGE,
                List.of(),
                DEFAULT_DIMENSION_PADDING,
                DEFAULT_LIQUID_SETTINGS
        );
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();

        int y = this.startHeight.sample(
                context.random(),
                new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor())
        );

        BlockPos startPos = new BlockPos(
                chunkPos.getMinBlockX(),
                y,
                chunkPos.getMinBlockZ()
        );

        return LMJigsawPlacement.addPieces(
                context,
                this.startPool,
                this.startJigsawName,
                this.maxDepth,
                startPos,
                this.useExpansionHack,
                this.projectStartToHeightmap,
                this.maxDistanceFromCenter
        );
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.LM_JIGSAW.get();
    }
}