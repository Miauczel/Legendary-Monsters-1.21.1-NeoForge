package net.miauczel.legendary_monsters.entity.AnimatedMonster.Effect;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.OriginClasses.INoRendererEntity;
import net.miauczel.legendary_monsters.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DynamicCameraZoomEntity extends INoRendererEntity {
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(DynamicCameraZoomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ZOOM = SynchedEntityData.defineId(DynamicCameraZoomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(DynamicCameraZoomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ZOOM_FREEZE = SynchedEntityData.defineId(DynamicCameraZoomEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> SET_ZOOM_SPEED = SynchedEntityData.defineId(DynamicCameraZoomEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> CAMERA_LOCKED = SynchedEntityData.defineId(DynamicCameraZoomEntity.class, EntityDataSerializers.BOOLEAN);

    public DynamicCameraZoomEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    public DynamicCameraZoomEntity(Level world, Vec3 position, float radius, float magnitude, int duration, int zoomFreeze, float zoomSpeed, boolean cameraLocked, LivingEntity cameraEntity) {
        super(ModEntities.DYNAMIC_CAMERA_ZOOM.get(), world);
        setRadius(radius);
        setMaxZoom(magnitude);
        setDuration(duration);
        setZoomSpeed(zoomSpeed);
        setZoomFreeze(zoomFreeze);
        setCameraEntity(cameraEntity);
        setCameraLocked(cameraLocked);
        setPos(position.x(), position.y(), position.z());
    }
    public DynamicCameraZoomEntity(Level world, Vec3 position, float radius, float magnitude, int duration, int zoomFreeze, float zoomSpeed) {
        super(ModEntities.DYNAMIC_CAMERA_ZOOM.get(), world);
        setRadius(radius);
        setMaxZoom(magnitude);
        setDuration(duration);
        setZoomSpeed(zoomSpeed);
        setZoomFreeze(zoomFreeze);
        setCameraEntity(null);
        setCameraLocked(false);
        setPos(position.x(), position.y(), position.z());
    }

    public LivingEntity cameraEntity;


    public float zoomIncrement;
    public float zoomIncrementOld;

    @Override
    public void tick() {
        super.tick();

        zoomIncrementOld = zoomIncrement;
        if (tickCount < getDuration() && zoomIncrement * 0.1 < getMaxZoom()) {
            zoomIncrement += getZoomSpeed();
        } else if (zoomIncrement > 0 && tickCount > getDuration() + getZoomFreeze()) {
            zoomIncrement -= getZoomSpeed();
        }
        //   System.out.println("getIncrement: " + getZoomIncrement());
        if (tickCount > getDuration() + getZoomFreeze() && zoomIncrement == 0) discard();

    }

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
        builder.define(RADIUS, 10.0f);
        builder.define(ZOOM, 10.0f);
        builder.define(ZOOM_FREEZE, 0);
        builder.define(DURATION, 0);
        builder.define(SET_ZOOM_SPEED, 5f);
        builder.define(CAMERA_LOCKED, false);
    }

    public float getRadius() {
        return getEntityData().get(RADIUS);
    }

    public void setRadius(float radius) {
        getEntityData().set(RADIUS, radius);
    }

    public float getMaxZoom() {
        return getEntityData().get(ZOOM);
    }

    public void setMaxZoom(float magnitude) {
        getEntityData().set(ZOOM, magnitude);
    }

    public int getDuration() {
        return getEntityData().get(DURATION);
    }

    public void setDuration(int duration) {
        getEntityData().set(DURATION, duration);
    }

    public int getZoomFreeze() {
        return getEntityData().get(ZOOM_FREEZE);
    }

    public void setZoomFreeze(int zoomFreeze) {
        getEntityData().set(ZOOM_FREEZE, zoomFreeze);
    }

    public float getZoomSpeed() {
        return getEntityData().get(SET_ZOOM_SPEED);
    }

    public void setZoomSpeed(float fadeDuration) {
        getEntityData().set(SET_ZOOM_SPEED, fadeDuration);
    }

    public void setCameraLocked(boolean cameraLocked) {
        getEntityData().set(CAMERA_LOCKED, cameraLocked);
    }

    public boolean getCameraLocked() {
        return getEntityData().get(CAMERA_LOCKED);
    }

    public void setCameraEntity(LivingEntity cameraEntity) {
        this.cameraEntity = cameraEntity;
    }

    public LivingEntity getCameraEntity() {
        return cameraEntity;
    }

    public float getZoomIncrement() {
        return zoomIncrement * 0.1f;
    }

    public float getZoomIncrementOld() {
        return zoomIncrementOld * 0.1f;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        setRadius(compound.getFloat("radius"));
        setMaxZoom(compound.getFloat("zoom"));
        setDuration(compound.getInt("duration"));
        setZoomSpeed(compound.getInt("zoomSpeed"));
        setCameraLocked(compound.getBoolean("cameraLocked"));
        setZoomFreeze(compound.getInt("zoomFreeze"));
        tickCount = compound.getInt("ticks_existed");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putFloat("radius", getRadius());
        compound.putFloat("magnitude", getMaxZoom());
        compound.putInt("duration", getDuration());
        compound.putFloat("zoomSpeed", getZoomSpeed());
        compound.putInt("zoomFreeze", getZoomFreeze());
        compound.putBoolean("cameraLocked", getCameraLocked());
        compound.putInt("ticks_existed", tickCount);
    }

    public static void dynamicCameraZoom(Level world, Vec3 position, float radius, float maxZoom, int duration, int zoomFreeze, float zoomSpeed, boolean cameraLocked, LivingEntity cameraEntity) {
        if (!world.isClientSide) {
            DynamicCameraZoomEntity zoomEntity = new DynamicCameraZoomEntity(world, position, radius, maxZoom, duration, zoomFreeze, zoomSpeed, cameraLocked, cameraEntity);
            world.addFreshEntity(zoomEntity);
        }
    }
    public static void dynamicCameraZoom(Level world, Vec3 position, float radius, float maxZoom, int duration, int zoomFreeze, float zoomSpeed) {
        if (!world.isClientSide) {
            DynamicCameraZoomEntity zoomEntity = new DynamicCameraZoomEntity(world, position, radius, maxZoom, duration, zoomFreeze, zoomSpeed);
            world.addFreshEntity(zoomEntity);
        }
    }
}

