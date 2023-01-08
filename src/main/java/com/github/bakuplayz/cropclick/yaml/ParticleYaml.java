package com.github.bakuplayz.cropclick.yaml;

import com.github.bakuplayz.cropclick.runnables.particles.Particle;
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
 * A class representing a Particle object as YAML.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class ParticleYaml implements Yamlable, Enableable {

    /**
     * The given delay before playing the particle effect ({@link Particle#MIN_DELAY min delay} & {@link Particle#MAX_DELAY max delay}).
     */
    private @Setter @Getter @Accessors(chain = true) double delay;

    /**
     * The given speed the particle effect should be played at ({@link Particle#MIN_SPEED min speed} & {@link Particle#MAX_SPEED max speed}).
     */
    private @Setter @Getter @Accessors(chain = true) double speed;

    /**
     * The given amount of this particle effect to play at once ({@link Particle#MIN_AMOUNT min amount} & {@link Particle#MAX_AMOUNT max amount}).
     */
    private @Setter @Getter @Accessors(chain = true) int amount;


    public ParticleYaml(double delay, double speed, int amount) {
        this.delay = delay;
        this.speed = speed;
        this.amount = amount;
    }


    /**
     * It converts {@link ParticleYaml this} object to a YAML-styled map.
     *
     * @return a {@link HashMap YAML-formatted HashMap}.
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


    /**
     * It checks whether the Particle is enabled, by validating only ones with one or more {@link #amount}.
     *
     * @return true if the sound is enabled, otherwise false.
     */
    @Override
    public boolean isEnabled() {
        return amount > 0;
    }

}