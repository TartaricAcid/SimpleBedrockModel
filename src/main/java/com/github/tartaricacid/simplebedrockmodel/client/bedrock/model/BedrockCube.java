package com.github.tartaricacid.simplebedrockmodel.client.bedrock.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Vector3f;

public interface BedrockCube {
    int NUM_CUBE_FACES = 6;
    int VERTEX_X1_Y1_Z1 = 0,
            VERTEX_X2_Y1_Z1 = 1,
            VERTEX_X2_Y2_Z1 = 2,
            VERTEX_X1_Y2_Z1 = 3,
            VERTEX_X1_Y1_Z2 = 4,
            VERTEX_X2_Y1_Z2 = 5,
            VERTEX_X2_Y2_Z2 = 6,
            VERTEX_X1_Y2_Z2 = 7;
    int[][] VERTEX_ORDER = new int[][]{
            {VERTEX_X2_Y1_Z2, VERTEX_X1_Y1_Z2, VERTEX_X1_Y1_Z1, VERTEX_X2_Y1_Z1},
            {VERTEX_X2_Y2_Z1, VERTEX_X1_Y2_Z1, VERTEX_X1_Y2_Z2, VERTEX_X2_Y2_Z2},
            {VERTEX_X2_Y1_Z1, VERTEX_X1_Y1_Z1, VERTEX_X1_Y2_Z1, VERTEX_X2_Y2_Z1},
            {VERTEX_X1_Y1_Z2, VERTEX_X2_Y1_Z2, VERTEX_X2_Y2_Z2, VERTEX_X1_Y2_Z2},
            {VERTEX_X2_Y1_Z2, VERTEX_X2_Y1_Z1, VERTEX_X2_Y2_Z1, VERTEX_X2_Y2_Z2},
            {VERTEX_X1_Y1_Z1, VERTEX_X1_Y1_Z2, VERTEX_X1_Y2_Z2, VERTEX_X1_Y2_Z1},
    };

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
    void compile(PoseStack.Pose pose, Vector3f[] normals, VertexConsumer consumer, int texU, int texV, float red, float green, float blue, float alpha);
}
