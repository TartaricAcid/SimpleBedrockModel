package com.github.tartaricacid.simplebedrockmodel;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SimpleBedrockModel.MOD_ID)
public class SimpleBedrockModel {
    public static final String MOD_ID = "simplebedrockmodel";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    /**
     * 全局变量，管控是否进行面剔除
     */
    public static boolean FACE_CULLING = true;

    public SimpleBedrockModel() {
    }
}
