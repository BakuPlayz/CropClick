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
     * A variable indicating how many milliseconds before the particle should be displayed.
     */
    private @Setter @Getter @Accessors(chain = true) double delay;

    /**
     * A variable indicating at what speed the particle should be displayed at.
     */
    private @Setter @Getter @Accessors(chain = true) double speed;

    /**
     * A variable indicating how many particles should be displayed, when displayed.
     */
    private @Setter @Getter @Accessors(chain = true) int amount;


    public ParticleYaml(double delay, double speed, int amount) {
        this.delay = delay;
        this.speed = speed;
        this.amount = amount;
    }


    /**
     * It checks wheaten or not the {@link ParticleYaml particle} is enabled in the yaml file.
     */
    @Override
    public boolean isEnabled() {
        return amount != 0;
    }

    /**
     * It converts {@link ParticleYaml this} object to a yaml-styled map.
     *
     * @return A HashMap<String, Object>
     */
    @Override
    @Contract(" -> new")
    public @NotNull Map<String, Object> toYaml() {
        return new HashMap<String, Object>() {{
            put("delay", delay);
            put("speed", speed);
            put("amount", amount);
        }};
    }

}