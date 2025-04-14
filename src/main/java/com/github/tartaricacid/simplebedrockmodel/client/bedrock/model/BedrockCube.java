package com.github.tartaricacid.simplebedrockmodel.client.bedrock.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public interface BedrockCube {
    /**
     * Compiles the cube's vertices and adds them to the provided vertex consumer
     * <p>
     * 编译 Cube 的顶点并将它们添加到提供的 VertexConsumer 中
     *
     * @param pose     the current pose of the rendering context
     * @param consumer the vertex consumer to which the compiled vertices are added
     * @param texU     the U coordinate for texture mapping
     * @param texV     the V coordinate for texture mapping
     * @param red      the red color component (0.0 to 1.0)
     * @param green    the green color component (0.0 to 1.0)
     * @param blue     the blue color component (0.0 to 1.0)
     * @param alpha    the alpha (transparency) component (0.0 to 1.0)
     */
    void compile(PoseStack.Pose pose, VertexConsumer consumer, int texU, int texV, float red, float green, float blue, float alpha);

    /**
     * Get bedrock polygon data for the bedrock model
     * <p>
     * 获取基岩版模型的顶点数据
     *
     * @return an array of {@link BedrockPolygon} representing the polygons of the cube
     */
    BedrockPolygon[] getPolygons();
}
