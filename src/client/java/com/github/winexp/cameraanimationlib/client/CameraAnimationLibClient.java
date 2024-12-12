package com.github.winexp.cameraanimationlib.client;

import com.github.winexp.cameraanimationlib.client.command.CameraCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class CameraAnimationLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(new CameraCommand());
    }
}
