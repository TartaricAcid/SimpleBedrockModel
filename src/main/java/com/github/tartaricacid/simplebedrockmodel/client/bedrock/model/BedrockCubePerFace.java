package com.github.tartaricacid.simplebedrockmodel.client.bedrock.model;


import com.github.tartaricacid.simplebedrockmodel.client.bedrock.pojo.FaceItem;
import com.github.tartaricacid.simplebedrockmodel.client.bedrock.pojo.FaceUVsItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.NotImplementedException;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class BedrockCubePerFace implements BedrockCube {
    final float width;
    final float height;
    final float depth;
    final float x;
    final float y;
    final float z;
    final float[][] UVS = new float[6][4];
    private static final Vector3f[] VERTICES = new Vector3f[8];
    private static final Vector3f EDGEX = new Vector3f();
    private static final Vector3f EDGEY = new Vector3f();
    private static final Vector3f EDGEZ = new Vector3f();

    static {
        for (int i = 0; i < VERTICES.length; i++) {
            VERTICES[i] = new Vector3f();
        }
    }

    public BedrockCubePerFace(float x, float y, float z, float width, float height, float depth, float delta, float texWidth, float texHeight, FaceUVsItem faces) {
        this.x = (x - delta) / 16.0f;
        this.y = (y - delta) / 16.0f;
        this.z = (z - delta) / 16.0f;
        this.width = (width + delta * 2) / 16.0f;
        this.height = (height + delta * 2) / 16.0f;
        this.depth = (depth + delta * 2) / 16.0f;

        for (Direction direction : Direction.values()) {
            fillUV(direction, faces, texWidth, texHeight);
        }
    }

    private void fillUV(Direction direction, FaceUVsItem faces, float texWidth, float texHeight) {
        FaceItem face = faces.getFace(direction);
        UVS[direction.ordinal()][0] = face.getUv()[0] / texWidth;
        UVS[direction.ordinal()][1] = (face.getUv()[0] + face.getUvSize()[0]) / texWidth;
        UVS[direction.ordinal()][2] = face.getUv()[1] / texHeight;
        UVS[direction.ordinal()][3] = (face.getUv()[1] + face.getUvSize()[1]) / texHeight;
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
                    r, g, b, a, UVS[i][1], UVS[i][2], texV, texU, NORMALS[i].x, NORMALS[i].y, NORMALS[i].z);

            consumer.vertex(VERTICES[VERTEX_ORDER[i][1]].x, VERTICES[VERTEX_ORDER[i][1]].y, VERTICES[VERTEX_ORDER[i][1]].z,
                    r, g, b, a, UVS[i][0], UVS[i][2], texV, texU, NORMALS[i].x, NORMALS[i].y, NORMALS[i].z);

            consumer.vertex(VERTICES[VERTEX_ORDER[i][2]].x, VERTICES[VERTEX_ORDER[i][2]].y, VERTICES[VERTEX_ORDER[i][2]].z,
                    r, g, b, a, UVS[i][0], UVS[i][3], texV, texU, NORMALS[i].x, NORMALS[i].y, NORMALS[i].z);

            consumer.vertex(VERTICES[VERTEX_ORDER[i][3]].x, VERTICES[VERTEX_ORDER[i][3]].y, VERTICES[VERTEX_ORDER[i][3]].z,
                    r, g, b, a, UVS[i][1], UVS[i][3], texV, texU, NORMALS[i].x, NORMALS[i].y, NORMALS[i].z);
        }
    }

    @Override
    public BedrockPolygon[] getPolygons() {
        throw new NotImplementedException("getPolygons");
    }
}
