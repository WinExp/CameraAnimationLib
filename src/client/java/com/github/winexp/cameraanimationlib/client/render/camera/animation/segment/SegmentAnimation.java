package com.github.winexp.cameraanimationlib.client.render.camera.animation.segment;

import com.github.winexp.cameraanimationlib.client.render.camera.animation.Animation;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.DeltaProvider;
import com.github.winexp.cameraanimationlib.client.util.ModMathHelper;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SegmentAnimation implements Animation {
    private final List<Segment> segments;
    private final int totalFrames;
    private int index;
    private int segmentBeginFrame;
    private int animatedFrames;
    private Vec3d prevPos;
    private Vec3d nextPos;
    private Vec2f prevRotation;
    private Vec2f nextRotation;

    public SegmentAnimation(Collection<Segment> segments) {
        if (segments.isEmpty()) throw new IllegalArgumentException("keyframes must not be empty");
        this.segments = ImmutableList.copyOf(segments);
        int totalFrames = 0;
        for (Segment segment : segments) {
            totalFrames += segment.getLength();
        }
        this.totalFrames = totalFrames;
        Segment firstSegment = this.segments.getFirst();
        this.prevPos = this.nextPos = firstSegment.getBeginPos();
        this.prevRotation = this.nextRotation = firstSegment.getBeginRotation();
    }

    public List<Segment> getSegments() {
        return this.segments;
    }

    @Nullable
    public Segment getCurrentSegment() {
        if (this.index >= this.segments.size()) return null;
        else return this.segments.get(this.index);
    }

    public int getTotalFrames() {
        return this.totalFrames;
    }

    @Override
    public void update(Camera camera, float tickDelta) {
        Vec3d pos = this.prevPos.lerp(this.nextPos, tickDelta);
        camera.setPos(pos);
        Vec2f rotation = ModMathHelper.lerp(tickDelta, this.prevRotation, this.nextRotation);
        camera.setRotation(rotation.x, rotation.y);
    }

    @Override
    public void tick(Camera camera) {
        this.animatedFrames++;
        this.prevPos = this.nextPos;
        this.prevRotation = this.nextRotation;
        Segment currentSegment = this.getCurrentSegment();
        if (currentSegment == null) return;
        if (this.animatedFrames - 1 >= this.segmentBeginFrame + currentSegment.getLength()) {
            this.index++;
            currentSegment = this.getCurrentSegment();
            if (currentSegment == null) return;
            this.segmentBeginFrame = this.animatedFrames;
            if (currentSegment instanceof EmptySegment) return;
            this.prevPos = this.nextPos = currentSegment.getBeginPos();
            this.prevRotation = this.nextRotation = currentSegment.getBeginRotation();
        }
        if (currentSegment instanceof EmptySegment) return;
        DeltaProvider deltaProvider = currentSegment.getDeltaProvider();
        float progress = (float) (this.animatedFrames - this.segmentBeginFrame) / currentSegment.getLength();
        this.nextPos = currentSegment.getBeginPos().lerp(currentSegment.getEndPos(), deltaProvider.getDelta(progress));
        this.nextRotation = ModMathHelper.lerp(deltaProvider.getDelta(progress), currentSegment.getBeginRotation(), currentSegment.getEndRotation());
    }

    @Override
    public boolean shouldStop() {
        return this.animatedFrames >= this.totalFrames;
    }

    @Override
    public boolean shouldRenderSelfEntity() {
        return true;
    }

    public static class Builder {
        private final List<Segment> segments = new ArrayList<>();

        public Builder() {
        }

        public Builder add(Segment segment) {
            this.segments.add(segment);
            return this;
        }

        public SegmentAnimation build() {
            return new SegmentAnimation(this.segments);
        }
    }
}
