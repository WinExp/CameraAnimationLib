package com.github.winexp.cameraanimationlib.client;

import com.github.winexp.cameraanimationlib.client.command.CameraCommand;
import com.github.winexp.cameraanimationlib.client.render.camera.animation.AnimationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class CameraAnimationLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.gameRenderer.getCamera().isReady()) {
                ((AnimationHandler) client.gameRenderer.getCamera()).cameraAnimationLib$tick();
            }
        });
        ClientCommandRegistrationCallback.EVENT.register(new CameraCommand());
    }
}
