package net.miauczel.legendary_monsters.util.structure.Processors;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModProcessors {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS =
            DeferredRegister.create(BuiltInRegistries.STRUCTURE_PROCESSOR, LegendaryMonsters.MOD_ID);

    public static final DeferredHolder<
            StructureProcessorType<?>,
            StructureProcessorType<NoWaterInStructureProcessor>
            > NO_WATER_PROCESSOR =
            STRUCTURE_PROCESSORS.register("no_water_processor",
                    () -> () -> NoWaterInStructureProcessor.CODEC
            );

    public static void register(IEventBus eventBus) {
        STRUCTURE_PROCESSORS.register(eventBus);
    }
}