package com.github.tartaricacid.simplebedrockmodel.client.bedrock;

import com.github.tartaricacid.simplebedrockmodel.client.bedrock.pojo.CubesItem;
import com.github.tartaricacid.simplebedrockmodel.client.bedrock.pojo.FaceItem;
import com.github.tartaricacid.simplebedrockmodel.client.bedrock.pojo.FaceUVsItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class BedrockModelUtil {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .registerTypeAdapter(CubesItem.class, new CubesItem.Deserializer())
            .create();

    public static FaceUVsItem singleSouthFace() {
        return new FaceUVsItem(emptyFace(), emptyFace(), emptyFace(), single16xFace(), emptyFace(), emptyFace());
    }

    public static FaceItem single16xFace() {
        return new FaceItem(new float[]{0, 0}, new float[]{16, 16});
    }

    public static FaceItem emptyFace() {
        return new FaceItem(new float[]{0, 0}, new float[]{0, 0});
    }

    public static Vector3f mulPosition(Vector3f in, Matrix4f mat) {
        float x = in.x, y = in.y, z = in.z;
        in.x = Math.fma(mat.m00, x, Math.fma(mat.m10, y, Math.fma(mat.m20, z, mat.m30)));
        in.y = Math.fma(mat.m01, x, Math.fma(mat.m11, y, Math.fma(mat.m21, z, mat.m31)));
        in.z = Math.fma(mat.m02, x, Math.fma(mat.m12, y, Math.fma(mat.m22, z, mat.m32)));
        return in;
    }

    public static Vector3f add(Vector3f in, Vector3f v, Vector3f dest) {
        dest.x = in.x + v.x();
        dest.y = in.y + v.y();
        dest.z = in.z + v.z();
        return dest;
    }
}
