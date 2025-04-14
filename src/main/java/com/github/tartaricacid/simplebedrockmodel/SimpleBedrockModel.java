package com.github.tartaricacid.simplebedrockmodel;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = SimpleBedrockModel.MOD_ID, dist = Dist.CLIENT)
public class SimpleBedrockModel {
    public static final String MOD_ID = "simplebedrockmodel";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public SimpleBedrockModel() {
    }
}