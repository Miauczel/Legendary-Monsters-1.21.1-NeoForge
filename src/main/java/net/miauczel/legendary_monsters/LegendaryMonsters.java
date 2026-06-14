package net.miauczel.legendary_monsters;

import net.miauczel.legendary_monsters.Message.*;
import net.miauczel.legendary_monsters.Network.LMClientPayLoadHandler;
import net.miauczel.legendary_monsters.Network.LMServerPayLoadHandler;
import net.miauczel.legendary_monsters.Particle.ModParticles;
import net.miauczel.legendary_monsters.block.ModBlockEntity;
import net.miauczel.legendary_monsters.block.ModBlocks;
import net.miauczel.legendary_monsters.effect.ModEffects;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.miauczel.legendary_monsters.entity.ProjectileEntityRenderer.*;
import net.miauczel.legendary_monsters.entity.client.Model.beamSpawner;
import net.miauczel.legendary_monsters.entity.client.Render.*;
import net.miauczel.legendary_monsters.entity.client.Render.BlockEntity.EnderAnchorRenderer;
import net.miauczel.legendary_monsters.entity.client.Render.BlockEntity.SomberTrapdoor.SomberTrapdoorRenderer;
import net.miauczel.legendary_monsters.entity.client.Render.BlockEntity.SwingingAxeRenderer;
import net.miauczel.legendary_monsters.entity.client.Render.BlockEntity.TeleportMachineRenderer;
import net.miauczel.legendary_monsters.item.ModCreativeModTabs;
import net.miauczel.legendary_monsters.item.ModItemProperties;
import net.miauczel.legendary_monsters.item.ModItems;
import net.miauczel.legendary_monsters.item.custom.customArmor.ModArmorMaterials;
import net.miauczel.legendary_monsters.sound.ModSounds;
import net.miauczel.legendary_monsters.util.structure.Processors.ModProcessors;
import net.miauczel.legendary_monsters.worldgen.Structure.ModStructures;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.LogManager;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(LegendaryMonsters.MOD_ID)
public class LegendaryMonsters {
    public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();
    private static final String PROTOCOL_VERSION = Integer.toString(1);

    private static int packetsRegistered;
    public static CommonProxy proxy = new CommonProxy();


    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath("legendarymonsters", name.toLowerCase(Locale.ROOT));
    }

    @SubscribeEvent
    public void onLivingHurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getMainHandItem().getItem() == ModItems.AXE_OF_LIGHTNING.get()) {
                DamageSource source = event.getEntity().getLastDamageSource();
                if (source != null && source.is(DamageTypes.LIGHTNING_BOLT)) {

                    event.setAmount(event.getAmount() * 0f);
                }
            }
        }

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
            if (boots.getItem() == ModItems.ATMOSPHERIC_BOOTS.get()) {
                DamageSource source1 = event.getSource();
                if (source1 != null && source1.is(DamageTypes.FALL)) {
                    event.setAmount(event.getAmount() * 0.5f);
                }
            }
        }
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack boots = player.getItemBySlot(EquipmentSlot.HEAD);
            if (boots.getItem() == ModItems.BLASTPROOF_HELMET.get()) {
                DamageSource source1 = event.getSource();
                if (source1 != null && source1.is(DamageTypes.EXPLOSION) || source1.is(DamageTypes.PLAYER_EXPLOSION)) {
                    event.setAmount(event.getAmount() * 0.65f);
                }
            }
        }


    }


    public static final String MOD_ID = "legendary_monsters";
    public static DamageSource EXTRA_DAMAGE;

    public LegendaryMonsters(IEventBus modEventBus, ModContainer modContainer) {
        proxy = FMLLoader.getDist().isClient() ? new ClientProxy() : new CommonProxy();


        proxy.init(modEventBus);
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);
        ModArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        modEventBus.addListener(LegendaryMonsters::setupMessages);
        modEventBus.addListener(this::addCreative);
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModEffects.register(modEventBus);
        ModSounds.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModParticles.register(modEventBus);
        ModProcessors.register(modEventBus);
        ModBlockEntity.register(modEventBus);
        ModStructures.register(modEventBus);
        modContainer.registerConfig(
                ModConfig.Type.COMMON,
                net.miauczel.legendary_monsters.config.ModConfig.MOB_CONFIG_SPEC
        );

    }

    private void commonSetup(final FMLCommonSetupEvent event) {


    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }


    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        public ClientModEvents() {
            proxy = new ClientProxy();
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            proxy.clientInit();
            LegendaryMonsters.LOGGER.info(
                    "[LM NET] Registering payload handlers"
            );
            System.out.println("Payloads loaded");
            ModItemProperties.addCustomItemProperties();
            //  NeoForge.EVENT_BUS.register(ClientEvent.class);
            EntityRenderers.register(ModEntities.Skeletosaurus.get(), SkeletosaurusRenderer::new);
            EntityRenderers.register(ModEntities.Ancient_Guardian.get(), Ancient_GuardianRenderer::new);

            EntityRenderers.register(ModEntities.Overgrown_colossus.get(), Overgrown_colossusRenderer::new);
            EntityRenderers.register(ModEntities.Endersent.get(), EndersentRenderer::new);
            EntityRenderers.register(ModEntities.Warped_Fungussus.get(), Warped_FungussusRenderer::new);
            EntityRenderers.register(ModEntities.Lava_eater.get(), Lava_eaterRenderer::new);
            EntityRenderers.register(ModEntities.Frostbitten_Golem.get(), Frostbitten_GolemRenderer::new);

            //EntityRenderers.register(ModEntities.Shulker_Mimic.get(), Shulker_MimicRenderer::new);
            EntityRenderers.register(ModEntities.Shulker_Mimic.get(), Shulker_MimicRenderer::new);

            EntityRenderers.register(ModEntities.Chorusling.get(), ChoruslingRenderer::new);
            EntityRenderers.register(ModEntities.Withered_Abomination.get(), Withered_AbominationRenderer::new);
            EntityRenderers.register(ModEntities.Ambusher.get(), AmbusherRenderer::new);
            EntityRenderers.register(ModEntities.Spiky_bug.get(), SpikebugRenderer::new);
            EntityRenderers.register(ModEntities.CHORUS_BOMB.get(), ChorusBombRenderer::new);
            EntityRenderers.register(ModEntities.FALLING_CLOUD.get(), ThrownItemRenderer::new);
            EntityRenderers.register(ModEntities.FALLING_CLOUD_SMALL.get(), ThrownItemRenderer::new);
            EntityRenderers.register(ModEntities.SPEAR.get(), SpearRenderer::new);
            EntityRenderers.register(ModEntities.Cloud_golem.get(), Cloud_GolemRenderer::new);
            EntityRenderers.register(ModEntities.Haunted_Knight.get(), LivingArmorRenderer::new);
            EntityRenderers.register(ModEntities.Knights_Armor.get(), FLivingArmorRenderer::new);
            EntityRenderers.register(ModEntities.Guard.get(), FHauntedGuardRenderer::new);
            EntityRenderers.register(ModEntities.Haunted_Guard.get(), HauntedGuardRenderer::new);
            EntityRenderers.register(ModEntities.Posessed_Paladin.get(), PossessedPaladinRenderer::new);
            EntityRenderers.register(ModEntities.GOLDEN_HALBERT.get(), GoldenHalbertRenderer::new);

            EntityRenderers.register(ModEntities.CAMERA_SHAKE.get(), CameraShakeRenderer::new);
            EntityRenderers.register(ModEntities.Mossy_Golem.get(), MossyGolemRenderer::new);
            EntityRenderers.register(ModEntities.Skeleraptor.get(), SkeloraptorRenderer::new);
            ;
            EntityRenderers.register(ModEntities.BlastCannon.get(), DuneSentinelRenderer::new);
            EntityRenderers.register(ModEntities.Bomber.get(), BomberRenderer::new);
            EntityRenderers.register(ModEntities.BOMB.get(), BombRenderer::new);
            EntityRenderers.register(ModEntities.LM_FALLING_BLOCK.get(), LMFallingBlockRenderer::new);

            EntityRenderers.register(ModEntities.C.get(), CloudRender::new);
            EntityRenderers.register(ModEntities.ICE_SPIKE_ENTITY.get(), IceSpikeRenderer::new);
            EntityRenderers.register(ModEntities.SHOCKWAVE.get(), NoRendererEntityRenderer::new);
            EntityRenderers.register(ModEntities.POISONOUS_SHOCKWAVE.get(), NoRendererEntityRenderer::new);
            EntityRenderers.register(ModEntities.SHOCKWAVE_SPAWNER.get(), NoRendererEntityRenderer::new);
            EntityRenderers.register(ModEntities.POISONOUS_SHOCKWAVE_SPAWNER.get(), NoRendererEntityRenderer::new);
            EntityRenderers.register(ModEntities.FIRE.get(), NoRendererEntityRenderer::new);
            EntityRenderers.register(ModEntities.FIRE_B.get(), NoRendererEntityRenderer::new);

            EntityRenderers.register(ModEntities.DYNAMIC_CAMERA_ZOOM.get(), NoRendererEntityRenderer::new);
            EntityRenderers.register(ModEntities.ELectr.get(), beamSpawner::new);

            EntityRenderers.register(ModEntities.LightningBeam.get(), context -> new LightningBeamRenderer(context, 0.8f));

            EntityRenderers.register(ModEntities.Stratling.get(), StratlingRenderer::new);

            EntityRenderers.register(ModEntities.HoverinngHurricane.get(), HoveringHurricaneRenderer::new);
            EntityRenderers.register(ModEntities.TORNADO.get(), TornadoRender::new);
            EntityRenderers.register(ModEntities.S.get(), SoulHandRenderer::new);
            EntityRenderers.register(ModEntities.L.get(), context -> new LightningRenderer(context, 0.8f));


            EntityRenderers.register(ModEntities.ENERGY_BEAM.get(), EnergyBeamRender2d::new);
            EntityRenderers.register(ModEntities.SS.get(), soul::new);

            EntityRenderers.register(ModEntities.PLAYER_TORNADO.get(), PlayerTornadoRender::new);
            EntityRenderers.register(ModEntities.SMALL_CLOUD.get(), SmallCloudRender::new);
            EntityRenderers.register(ModEntities.THUNDER_CLOUD.get(), AngryCloudRender::new);


            EntityRenderers.register(ModEntities.CHORUS_BULLET.get(), ChorusEnergyBulletRenderer::new);

            EntityRenderers.register(ModEntities.CHORUS_BREATH.get(), NoRendererEntityRenderer::new);

            EntityRenderers.register(ModEntities.SOUL_PILLAR.get(), NoRendererEntityRenderer::new);

            EntityRenderers.register(ModEntities.SOUL_BLADE_UNDERGROUND.get(), SoulBladeRenderer::new);

            EntityRenderers.register(ModEntities.BIG_SHULKER_BULLET.get(), BigShulkerBulletRenderer::new);
            EntityRenderers.register(ModEntities.FALLING_SOUL_BLADE.get(), FallingSoulBladeRenderer::new);
            EntityRenderers.register(ModEntities.THE_OBLITERATOR.get(), TheObliteratorRenderer::new);

            EntityRenderers.register(ModEntities.ANNIHILATION_BOMB_ENTITY.get(), PowerBallBombRenderer::new);

            EntityRenderers.register(ModEntities.SMALL_ANNIHILATION_BOMB_ENTITY.get(), SmallPowerBallBombRenderer::new);

            EntityRenderers.register(ModEntities.THE_WARPED_ONE_DUPLICATE.get(), TheObliteratorCloneRenderer::new);

            EntityRenderers.register(ModEntities.THE_WARPED_ONE_DUPLICATE_ARMED.get(), TheObliteratorArmedCloneRenderer::new);

            EntityRenderers.register(ModEntities.GREEN_FLAME_STRIKE.get(), NoRendererEntityRenderer::new);
            EntityRenderers.register(ModEntities.ENERGY_DISC.get(), PlasmaBallRenderer::new);

            EntityRenderers.register(ModEntities.ENERGY_EXPLOSION.get(), NoRendererEntityRenderer::new);
            EntityRenderers.register(ModEntities.ENERGY_LASER.get(), NoRendererProjectileRenderer::new);

            EntityRenderers.register(ModEntities.TRACKING_BOMB.get(), TrackingEnergyBombRenderer::new);

            EntityRenderers.register(ModEntities.ANNIHILATION_BEAM.get(), AnnihilationBeamRenderer::new);

            EntityRenderers.register(ModEntities.ANNIHILATION_PORTAL.get(), AnnihilationPortalRenderer::new);

            EntityRenderers.register(ModEntities.FLYING_ARMOR.get(), FlyingArmorRenderer::new);
            EntityRenderers.register(ModEntities.FLAME_ROCKET.get(), FlameRocketRenderer::new);


            EntityRenderers.register(ModEntities.FLAMEBORN_GUARD.get(), FlamebornGuardRenderer::new);
            EntityRenderers.register(ModEntities.ANNIHILATION_GEYSER.get(), NoRendererEntityRenderer::new);

            EntityRenderers.register(ModEntities.FLAME_DRIFTER.get(), FlameDrifterRenderer::new);
            EntityRenderers.register(ModEntities.GROUND_ANNIHILATION_NUKE_STRIKE.get(), NoRendererEntityRenderer::new);

            EntityRenderers.register(ModEntities.GRAVITY_BIG_SHULKER_BULLET.get(), GravityBigShulkerBulletRenderer::new);

            EntityRenderers.register(ModEntities.ANNIHILATION_PURSUER.get(), AnnihilationPursuerRenderer::new);
            EntityRenderers.register(ModEntities.ENTITY_THROWN.get(), EntityThrownRenderer::new);

            EntityRenderers.register(ModEntities.BOTTLE_OF_ANNIHILATION.get(), ThrownItemRenderer::new);

            EntityRenderers.register(ModEntities.WANDERING_EYE.get(), WanderingEyeRenderer::new);

            EntityRenderers.register(ModEntities.FLAMEBORN_WARRIOR.get(), FlamebornWarriorRenderer::new);

            EntityRenderers.register(ModEntities.THROWN_PHANTOM_DAGGER.get(), ThrownPhantomDaggerRenderer::new);

            EntityRenderers.register(ModEntities.SOUL_SHIELD.get(), SoulShieldRenderer::new);

            EntityRenderers.register(ModEntities.BEHEADED_KNIGHT.get(), BeheadedKnightRenderer::new);

            EntityRenderers.register(ModEntities.SOUL_JAVELIN.get(), SoulJavelinRenderer::new);

            EntityRenderers.register(ModEntities.RESURRECTED_JAVELIN.get(), ResurrectedJavelinRenderer::new);

            EntityRenderers.register(ModEntities.THROWN_RESURRECTED_KNIGHT.get(), ThrownResurrectedKnightRenderer::new);

            EntityRenderers.register(ModEntities.SOUL_PILLAR_EXPLOSION.get(), NoRendererEntityRenderer::new);

            EntityRenderers.register(ModEntities.RESURRECTED_KNIGHT.get(), ResurrectedKnightRenderer::new);

            EntityRenderers.register(ModEntities.SOUL_TRIDENT.get(), SoulTridentRenderer::new);

            EntityRenderers.register(ModEntities.FRACTURED_APOSTLE.get(), FracturedApostleRenderer::new);

            EntityRenderers.register(ModEntities.FRACTURED.get(), FracturedRenderer::new);


            BlockEntityRenderers.register(ModBlockEntity.ENDER_ANCHOR.get(), EnderAnchorRenderer::new);
            BlockEntityRenderers.register(ModBlockEntity.TELEPORT_MACHINE.get(), TeleportMachineRenderer::new);
            BlockEntityRenderers.register(ModBlockEntity.SWINGING_AXE.get(), SwingingAxeRenderer::new);
            BlockEntityRenderers.register(ModBlockEntity.SOMBER_TRAPDOOR.get(), SomberTrapdoorRenderer::new);

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SMALL_SPIKE_TRAP_BLOCK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BIG_SPIKE_TRAP_BLOCK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Block.byItem(ModItems.SOUL_GREAT_SWORD.get()), RenderType.translucent());

            {
            }
        }
    }

    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void sendMSGToServer(CustomPacketPayload packetPayload) {
        PacketDistributor.sendToServer(packetPayload);
    }


    public static void queueServerWork(int tick, Runnable action) {
        workQueue.add(new AbstractMap.SimpleEntry(action, tick));
    }

    public static void sendMSGToAll(CustomPacketPayload packetPayload) {
        PacketDistributor.sendToAllPlayers(packetPayload);
       /* for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }

        */
    }

    public static void sendNonLocal(CustomPacketPayload packetPayload, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packetPayload);
        //  NETWORKWRAPPER.sendTo(msg, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }

    @SubscribeEvent
    public void tick(ServerTickEvent.Post event) {
        //if (event.phase == TickEvent.Phase.END) {
        List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
        workQueue.forEach(work -> {
            work.setValue(work.getValue() - 1);
            if (work.getValue() == 0)
                actions.add(work);
        });
        actions.forEach(e -> e.getKey().run());
        workQueue.removeAll(actions);
        // }
    }

    public static void setupMessages(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(MessageArmorKey.TYPE, MessageArmorKey.STREAM_CODEC, MessageArmorKey::handleMessageArmorKey);

        registrar.playToClient(
                PlayBossMusicMessage.TYPE,
                PlayBossMusicMessage.STREAM_CODEC,
                LMServerPayLoadHandler::handleMusic
        );
        registrar.playToClient(
                MessageUpdateBossBar.TYPE,
                MessageUpdateBossBar.STREAM_CODEC,
                LMClientPayLoadHandler::handleUpdateBossBar
        );

        registrar.playToServer(
                SkeloraptorRoarKeyMessage.TYPE,
                SkeloraptorRoarKeyMessage.STREAM_CODEC,
                LMServerPayLoadHandler::handleSkeloraptorRoar
        );
        registrar.playToServer(
                SkeloraptorTailAttackMessage.TYPE,
                SkeloraptorTailAttackMessage.STREAM_CODEC,
                LMServerPayLoadHandler::handleSkeloraptorTail
        );

        registrar.playToServer(
                AnnihilatorHelmetAbilityMessage.TYPE,
                AnnihilatorHelmetAbilityMessage.STREAM_CODEC,
                AnnihilatorHelmetAbilityMessage::handleAnnihilatorHelmetAbility
        );
    }
}

