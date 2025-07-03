package com.github.tartaricacid.simplebedrockmodel.client.bedrock.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.LightTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class BedrockPart {
    private static final Vector3f[] NORMALS = new Vector3f[6];
    public final ObjectList<BedrockCube> cubes = new ObjectArrayList<>();
    private final ObjectList<BedrockPart> children = new ObjectArrayList<>();
    public float x;
    public float y;
    public float z;
    public float xRot;
    public float yRot;
    public float zRot;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public boolean visible = true;
    public boolean illuminated = false;
    public boolean mirror;
    public float xScale = 1;
    public float yScale = 1;
    public float zScale = 1;
    private float initRotX;
    private float initRotY;
    private float initRotZ;
    //可能用于动画的四元数
    public Quaternionf additionalQuaternion = new Quaternionf(0, 0, 0, 1);
    protected BedrockPart parent;

    static {
        for (int i = 0; i < NORMALS.length; i++) {
            NORMALS[i] = new Vector3f();
        }
    }

    public void setPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void render(PoseStack poseStack, VertexConsumer consumer, int overlay, int lightmap) {
        this.render(poseStack, consumer, overlay, lightmap, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void render(PoseStack poseStack, VertexConsumer consumer, int overlay, int lightmap, float red, float green, float blue, float alpha) {
        int cubePackedLight = (!illuminated) ? lightmap : LightTexture.pack(15, 15);
        if (this.visible) {
            if (!this.cubes.isEmpty() || !this.children.isEmpty()) {
                poseStack.pushPose();
                this.translateAndRotateAndScale(poseStack);
                this.compile(poseStack.last(), consumer, overlay, cubePackedLight, red, green, blue, alpha);

                for (BedrockPart part : this.children) {
                    part.render(poseStack, consumer, overlay, cubePackedLight, red, green, blue, alpha);
                }

                poseStack.popPose();
            }
        }
    }

    public void translateAndRotate(PoseStack poseStack) {
        poseStack.translate((this.x / 16.0F) + this.offsetX, (this.y / 16.0F) + this.offsetY, (this.z / 16.0F) + this.offsetZ);
        if (this.xRot != 0.0F || this.yRot != 0.0F || this.zRot != 0.0F) {
            poseStack.last().pose().rotateZYX(this.zRot, this.yRot, this.xRot);
            poseStack.last().normal().rotateZYX(this.zRot, this.yRot, this.xRot);
        }
    }

    public void translateAndRotateAndScale(PoseStack poseStack) {
        poseStack.translate((this.x / 16.0F) + this.offsetX, (this.y / 16.0F) + this.offsetY, (this.z / 16.0F) + this.offsetZ);
        if (this.xRot != 0.0F || this.yRot != 0.0F || this.zRot != 0.0F) {
            poseStack.last().pose().rotateZYX(this.zRot, this.yRot, this.xRot);
            poseStack.last().normal().rotateZYX(this.zRot, this.yRot, this.xRot);
        }
        poseStack.mulPose(additionalQuaternion);
        poseStack.scale(xScale, yScale, zScale);
    }

    private void compile(PoseStack.Pose pose, VertexConsumer consumer, int overlay, int lightmap, float red, float green, float blue, float alpha) {
        Matrix3f normal = pose.normal();
        NORMALS[0].set(-normal.m10, -normal.m11, -normal.m12);
        NORMALS[1].set(normal.m10, normal.m11, normal.m12);
        NORMALS[2].set(-normal.m20, -normal.m21, -normal.m22);
        NORMALS[3].set(normal.m20, normal.m21, normal.m22);
        NORMALS[4].set(-normal.m00, -normal.m01, -normal.m02);
        NORMALS[5].set(normal.m00, normal.m01, normal.m02);
        for (BedrockCube bedrockCube : this.cubes) {
            bedrockCube.compile(pose, NORMALS, consumer, overlay, lightmap, red, green, blue, alpha);
        }
    }

    public BedrockCube getRandomCube(Random random) {
        return this.cubes.get(random.nextInt(this.cubes.size()));
    }

    public boolean isEmpty() {
        return this.cubes.isEmpty();
    }

    public void setInitRotationAngle(float x, float y, float z) {
        this.initRotX = x;
        this.initRotY = y;
        this.initRotZ = z;
    }

    public float getInitRotX() {
        return initRotX;
    }

    public float getInitRotY() {
        return initRotY;
    }

    public float getInitRotZ() {
        return initRotZ;
    }

    public void addChild(BedrockPart model) {
        this.children.add(model);
    }

    public BedrockPart getParent() {
        return parent;
    }
}
