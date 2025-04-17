package com.github.tartaricacid.simplebedrockmodel.client.compat.embeddium;

import com.github.tartaricacid.simplebedrockmodel.client.bedrock.model.BedrockCubePerFace;
import com.github.tartaricacid.simplebedrockmodel.client.bedrock.pojo.FaceUVsItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.embeddedt.embeddium.api.vertex.buffer.VertexBufferWriter;
import org.embeddedt.embeddium.impl.render.vertex.VertexConsumerUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class EmbeddiumBedrockCubePerFace extends BedrockCubePerFace implements IEmbeddiumVertexWriter {
    public EmbeddiumBedrockCubePerFace(float x, float y, float z, float width, float height, float depth, float delta, float texWidth, float texHeight, FaceUVsItem faces) {
        super(x, y, z, width, height, depth, delta, texWidth, texHeight, faces);
    }

    @Override
    public void compile(PoseStack.Pose pose, Vector3f[] normals, VertexConsumer consumer, int texU, int texV, float r, float g, float b, float a) {
        VertexBufferWriter writer = VertexConsumerUtils.convertOrLog(consumer);
        if (writer == null) {
            super.compile(pose, normals, consumer, texU, texV, r, g, b, a);
            return;
        }

        Matrix4f matrix4f = pose.pose();
        prepareVertices(matrix4f);
        prepareNormals(normals);
        int color = (int) (a * 255.0f) << 24 | (int) (b * 255.0f) << 16 | (int) (g * 255.0f) << 8 | (int) (r * 255.0f);

        int vertexCount = 0;
        long ptr = SCRATCH_BUFFER;

        for (int i = 0; i < NUM_CUBE_FACES; i++) {
            emitVertex(ptr, VERTICES[VERTEX_ORDER[i][0]].x, VERTICES[VERTEX_ORDER[i][0]].y, VERTICES[VERTEX_ORDER[i][0]].z,
                    color, uvs[i][1], uvs[i][2], texV, texU, NORMALS[i]);
            ptr += STRIDE;
            vertexCount += 4;

            emitVertex(ptr, VERTICES[VERTEX_ORDER[i][1]].x, VERTICES[VERTEX_ORDER[i][1]].y, VERTICES[VERTEX_ORDER[i][1]].z,
                    color, uvs[i][0], uvs[i][2], texV, texU, NORMALS[i]);
            ptr += STRIDE;
            vertexCount += 4;

            emitVertex(ptr, VERTICES[VERTEX_ORDER[i][2]].x, VERTICES[VERTEX_ORDER[i][2]].y, VERTICES[VERTEX_ORDER[i][2]].z,
                    color, uvs[i][0], uvs[i][3], texV, texU, NORMALS[i]);
            ptr += STRIDE;
            vertexCount += 4;

            emitVertex(ptr, VERTICES[VERTEX_ORDER[i][3]].x, VERTICES[VERTEX_ORDER[i][3]].y, VERTICES[VERTEX_ORDER[i][3]].z,
                    color, uvs[i][1], uvs[i][3], texV, texU, NORMALS[i]);
            ptr += STRIDE;
            vertexCount += 4;
        }

        flush(writer, vertexCount);
    }
}
