package com.github.tartaricacid.simplebedrockmodel.client.bedrock;

import com.github.tartaricacid.simplebedrockmodel.client.bedrock.model.BedrockPart;
import com.github.tartaricacid.simplebedrockmodel.client.bedrock.pojo.BonesItem;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.List;

public interface BedrockModelProvider<T extends BedrockModelProvider<T>> {
    T getModel();

    AABB getRenderBoundingBox();

    HashMap<String, BedrockPart> getModelMap();

    HashMap<String, BonesItem> getIndexBones();

    List<BedrockPart> getShouldRenderList();
}
