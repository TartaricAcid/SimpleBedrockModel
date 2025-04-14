package com.github.tartaricacid.simplebedrockmodel.client.bedrock.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.NotImplementedException;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class BedrockCubeBox implements BedrockCube {
    final float width;
    final float height;
    final float depth;
    final float x;
    final float y;
    final float z;
    final float[] UVS;
    private static final Vector3f[] VERTICES = new Vector3f[8];
    private static final Vector3f EDGEX = new Vector3f();
    private static final Vector3f EDGEY = new Vector3f();
    private static final Vector3f EDGEZ = new Vector3f();

    static {
        for (int i = 0; i < VERTICES.length; i++) {
            VERTICES[i] = new Vector3f();
        }
    }

    public BedrockCubeBox(float texOffX, float texOffY, float x, float y, float z, float width, float height, float depth, float delta, boolean mirror, float texWidth, float texHeight) {
        float cubeWidth = (width + delta * 2) / 16.0f;
        float cubeX = (x - delta) / 16.0f;
        if (mirror) {
            this.x = cubeX + cubeWidth;
            this.width = -cubeWidth;
        } else {
            this.x = cubeX;
            this.width = cubeWidth;
        }
        this.y = (y - delta) / 16.0f;
        this.z = (z - delta) / 16.0f;
        this.height = (height + delta * 2) / 16.0f;
        this.depth = (depth + delta * 2) / 16.0f;

        float dx = Mth.floor(width);
        float dy = Mth.floor(height);
        float dz = Mth.floor(depth);

        float scaleU = 1.0f / texWidth;
        float scaleV = 1.0f / texHeight;

        this.UVS = new float[9];
        this.UVS[0] = scaleU * texOffX;
        this.UVS[1] = scaleU * (texOffX + dz);
        this.UVS[2] = scaleU * (texOffX + dz + dx);
        this.UVS[3] = scaleU * (texOffX + dz + dx + dx);
        this.UVS[4] = scaleU * (texOffX + dz + dx + dz);
        this.UVS[5] = scaleU * (texOffX + dz + dx + dz + dx);
        this.UVS[6] = scaleV * texOffY;
        this.UVS[7] = scaleV * (texOffY + dz);
        this.UVS[8] = scaleV * (texOffY + dz + dy);
    }

    private void prepareVertices(Matrix4f pose) {
        EDGEX.set(pose.m00(), pose.m01(), pose.m02()).mul(width);
        EDGEY.set(pose.m10(), pose.m11(), pose.m12()).mul(height);
        EDGEZ.set(pose.m20(), pose.m21(), pose.m22()).mul(depth);
        VERTICES[VERTEX_X1_Y1_Z1].set(x, y, z).mulPosition(pose);
        VERTICES[VERTEX_X1_Y1_Z1].add(EDGEX, VERTICES[VERTEX_X2_Y1_Z1]);
        VERTICES[VERTEX_X2_Y1_Z1].add(EDGEY, VERTICES[VERTEX_X2_Y2_Z1]);
        VERTICES[VERTEX_X1_Y1_Z1].add(EDGEY, VERTICES[VERTEX_X1_Y2_Z1]);
        VERTICES[VERTEX_X1_Y1_Z1].add(EDGEZ, VERTICES[VERTEX_X1_Y1_Z2]);
        VERTICES[VERTEX_X2_Y1_Z1].add(EDGEZ, VERTICES[VERTEX_X2_Y1_Z2]);
        VERTICES[VERTEX_X2_Y2_Z1].add(EDGEZ, VERTICES[VERTEX_X2_Y2_Z2]);
        VERTICES[VERTEX_X1_Y2_Z1].add(EDGEZ, VERTICES[VERTEX_X1_Y2_Z2]);
    }

    @Override
    public void compile(PoseStack.Pose pose, Vector3f[] NORMALS, VertexConsumer consumer, int texU, int texV, float r, float g, float b, float a) {
        Matrix4f matrix4f = pose.pose();
        prepareVertices(matrix4f);

        for (int i = 0; i < NUM_CUBE_FACES; i++) {
            consumer.vertex(VERTICES[VERTEX_ORDER[i][0]].x, VERTICES[VERTEX_ORDER[i][0]].y, VERTICES[VERTEX_ORDER[i][0]].z,
                    r, g, b, a, UVS[UV_ORDER[i][1]], UV_ORDER[i][2], texV, texU, NORMALS[i].x, NORMALS[i].y, NORMALS[i].z);

            consumer.vertex(VERTICES[VERTEX_ORDER[i][1]].x, VERTICES[VERTEX_ORDER[i][1]].y, VERTICES[VERTEX_ORDER[i][1]].z,
                    r, g, b, a, UVS[UV_ORDER[i][0]], UV_ORDER[i][2], texV, texU, NORMALS[i].x, NORMALS[i].y, NORMALS[i].z);

            consumer.vertex(VERTICES[VERTEX_ORDER[i][2]].x, VERTICES[VERTEX_ORDER[i][2]].y, VERTICES[VERTEX_ORDER[i][2]].z,
                    r, g, b, a, UVS[UV_ORDER[i][0]], UV_ORDER[i][3], texV, texU, NORMALS[i].x, NORMALS[i].y, NORMALS[i].z);

            consumer.vertex(VERTICES[VERTEX_ORDER[i][3]].x, VERTICES[VERTEX_ORDER[i][3]].y, VERTICES[VERTEX_ORDER[i][3]].z,
                    r, g, b, a, UVS[UV_ORDER[i][1]], UV_ORDER[i][3], texV, texU, NORMALS[i].x, NORMALS[i].y, NORMALS[i].z);
        }
    }

    @Override
    public BedrockPolygon[] getPolygons() {
        throw new NotImplementedException("getPolygons");
    }
}
