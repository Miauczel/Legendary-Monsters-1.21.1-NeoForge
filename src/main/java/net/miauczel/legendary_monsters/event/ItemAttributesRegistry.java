package net.miauczel.legendary_monsters.event;

import net.miauczel.legendary_monsters.LegendaryMonsters;
import net.miauczel.legendary_monsters.config.ModConfig;
import net.miauczel.legendary_monsters.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;
import static net.minecraft.world.item.Item.BASE_ATTACK_SPEED_ID;

@EventBusSubscriber(modid = LegendaryMonsters.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ItemAttributesRegistry {

    @SubscribeEvent
    public static void RegisterItemAttributes(ItemAttributeModifierEvent event) {

        final ResourceLocation BASE_REACH_ID = ResourceLocation.withDefaultNamespace("reach");

        if (event.getItemStack().is(ModItems.SOUL_GREAT_SWORD.get())) {
            double damage = 12D * ModConfig.MOB_CONFIG.TheTesseractDamageMultiplier.get();
            double speed = -2.8D * ModConfig.MOB_CONFIG.TheTesseractSpeedMultiplier.get();
            double range = 1D * ModConfig.MOB_CONFIG.TheTesseractRangeMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_REACH_ID, range, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.MONSTROUS_ANCHOR.get())) {
            double damage = 13D * ModConfig.MOB_CONFIG.MonstrousAnchorDamageMultiplier.get();
            double speed = -3.35D * ModConfig.MOB_CONFIG.MonstrousAnchorSpeedMultiplier.get();
            double range = 0.75D * ModConfig.MOB_CONFIG.MonstrousAnchorRangeMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_REACH_ID, range, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.WITHERED_SCYTHE.get())) {
            double damage = 9 * ModConfig.MOB_CONFIG.WitheredScytheDamageMultiplier.get();
            double speed = -2.8D * ModConfig.MOB_CONFIG.WitheredScytheSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.WAND_OF_CLOUDS.get())) {
            double damage = 10D * ModConfig.MOB_CONFIG.WandOfCloudsDamageMultiplier.get();
            double speed = -2.7D * ModConfig.MOB_CONFIG.WandOfCloudsSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.AXE_OF_LIGHTNING.get())) {
            double damage = 12D * ModConfig.MOB_CONFIG.AxeOfLightningDamageMultiplier.get();
            double speed = -2.8D * ModConfig.MOB_CONFIG.AxeOfLightningSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.FIERY_JAW.get())) {
            double damage = 5 * ModConfig.MOB_CONFIG.FieryJawDamageMultiplier.get();
            double speed = -3D * ModConfig.MOB_CONFIG.FieryJawSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.GOLDEN_HALBERT.get())) {
            double damage = 5 * ModConfig.MOB_CONFIG.FieryJawDamageMultiplier.get();
            double speed = -3D * ModConfig.MOB_CONFIG.FieryJawSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.TESSERACT.get())) {
            double damage = 14D * ModConfig.MOB_CONFIG.TheTesseractDamageMultiplier.get();
            double speed = -2.8D * ModConfig.MOB_CONFIG.TheTesseractSpeedMultiplier.get();
            double range = 1D * ModConfig.MOB_CONFIG.TheTesseractRangeMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_REACH_ID, range, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.SHATTERED_GREATSWORD.get())) {
            double damage = 11 * ModConfig.MOB_CONFIG.ShatteredGreatSwordDamageMultiplier.get();
            double speed = -3D * ModConfig.MOB_CONFIG.ShatteredGreatSwordSpeedMultiplier.get();
            double range = 1D * ModConfig.MOB_CONFIG.ShatteredGreatSwordRangeMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_REACH_ID, range, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.RESURRECTED_JAVELIN.get())) {
            double damage = 9 * ModConfig.MOB_CONFIG.ResurrectedJavelinDamageMultiplier.get();
            double speed = -2.7D * ModConfig.MOB_CONFIG.ResurrectedJavelinSpeedMultiplier.get();
            double range = 1.35D * ModConfig.MOB_CONFIG.ResurrectedJavelinRangeMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_REACH_ID, range, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.MOSSY_HAMMER.get())) {
            double damage = 8 * ModConfig.MOB_CONFIG.MossyHammerDamageMultiplier.get();
            double speed = -3.2D * ModConfig.MOB_CONFIG.MossyHammerSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.SOMBER_KEY.get())) {
            double damage = 6.5 * ModConfig.MOB_CONFIG.SomberKeyDamageMultiplier.get();
            double speed = -2.5D * ModConfig.MOB_CONFIG.SomberKeySpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.KNIGHTS_SWORD.get())) {
            double damage = 5 * ModConfig.MOB_CONFIG.KnightsSwordDamageMultiplier.get();
            double speed = -2.3D * ModConfig.MOB_CONFIG.KnightsSwordSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.CHORUS_BLADE.get())) {
            double damage = 7 * ModConfig.MOB_CONFIG.ChorusBladeDamageMultiplier.get();
            double speed = -2.4D * ModConfig.MOB_CONFIG.ChorusBladeSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);

        }
        if (event.getItemStack().is(ModItems.DINOSAUR_BONE_CLUB.get())) {
            double damage = 8 * ModConfig.MOB_CONFIG.DinosaurBoneClubDamageMultiplier.get();
            double speed = -3D * ModConfig.MOB_CONFIG.DinosaurBoneClubSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.THE_GREAT_FROST.get())) {
            double damage = 6 * ModConfig.MOB_CONFIG.DinosaurBoneClubDamageMultiplier.get();
            double speed = -2.4D * ModConfig.MOB_CONFIG.DinosaurBoneClubSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
        if (event.getItemStack().is(ModItems.ANCHOR_HANDLE.get())) {
            double damage = 5 * ModConfig.MOB_CONFIG.AnchorHandleDamageMultiplier.get();
            double speed = -3D * ModConfig.MOB_CONFIG.AnchorHandleSpeedMultiplier.get();
            event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        }
    }
}
