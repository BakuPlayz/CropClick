package com.github.bakuplayz.cropclick.yaml;

import com.github.bakuplayz.cropclick.utils.Enableable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


/**
 * Represents an Particle object, as YAML.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class ParticleYaml implements Yamlable, Enableable {

    /**
     * A variable
     */
    private @Setter @Getter @Accessors(chain = true) double delay;
    private @Setter @Getter @Accessors(chain = true) double speed;
    private @Setter @Getter @Accessors(chain = true) int amount;


    public ParticleYaml(double delay, double speed, int amount) {
        this.delay = delay;
        this.speed = speed;
        this.amount = amount;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Map<String, Object> toYaml() {
        return new HashMap<String, Object>() {{
            put("delay", delay);
            put("speed", speed);
            put("amount", amount);
        }};
    }


    @Override
    public boolean isEnabled() {
        return amount != 0;
    }

}