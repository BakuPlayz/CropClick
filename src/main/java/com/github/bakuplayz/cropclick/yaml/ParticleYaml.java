package com.github.bakuplayz.cropclick.yaml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
public final class ParticleYaml extends YamlItem {

    private @Setter @Getter double delay;
    private @Setter @Getter double speed;
    private @Setter @Getter double volume;
    private @Setter @Getter int amount;


    public ParticleYaml(double delay, double speed, double volume, int amount) {
        this.delay = delay;
        this.speed = speed;
        this.volume = volume;
        this.amount = amount;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Map<String, Object> toYaml() {
        return new HashMap<String, Object>() {{
            put("delay", delay);
            put("speed", speed);
            put("volume", volume);
            put("amount", amount);
        }};
    }

}