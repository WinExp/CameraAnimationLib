package com.github.winexp.cameraanimationlib.client.render.camera.animation;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;

import java.util.Deque;
import java.util.LinkedList;

public class Animator {
    public static final Animator INSTANCE = new Animator(MinecraftClient.getInstance().gameRenderer.getCamera(), Integer.MAX_VALUE);
    private final AnimationHandler handler;
    private final int animationsLimit;
    private final Deque<Animation> animationQueue = new LinkedList<>();

    public Animator(Camera camera, int animationsLimit) {
        this.handler = (AnimationHandler) camera;
        this.animationsLimit = animationsLimit;

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.gameRenderer.getCamera().isReady()) {
                Animation animation = this.handler.cameraAnimationLib$getAnimation();
                if (animation != null && animation.shouldStop()) this.handler.cameraAnimationLib$stopAnimation();
                if (!this.handler.cameraAnimationLib$isAnimating() && !this.animationQueue.isEmpty())
                    this.handler.cameraAnimationLib$startAnimation(this.animationQueue.poll());
                this.handler.cameraAnimationLib$tick();
            }
        });
    }

    public boolean isEmpty() {
        return this.animationQueue.isEmpty();
    }

    public void enqueue(Animation animation) {
        if (this.animationQueue.size() >= this.animationsLimit) throw new IllegalStateException("Too much animations");
        this.animationQueue.add(animation);
    }

    public void clear() {
        this.animationQueue.clear();
    }

    public void stop() {
        this.handler.cameraAnimationLib$stopAnimation();
    }
}
