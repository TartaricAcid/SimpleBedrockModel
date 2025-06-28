package com.github.tartaricacid.simplebedrockmodel.client.bedrock;

import com.github.tartaricacid.simplebedrockmodel.client.bedrock.model.BedrockPart;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;

public interface BedrockModelProvider<T extends BedrockModelProvider<T>> {
    T getModel();

    AABB getRenderBoundingBox();

    HashMap<String, BedrockPart> getModelMap();
}
