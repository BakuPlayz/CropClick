package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.common.Locations;
import com.github.bakuplayz.cropclick.common.types.DoublyLocation;
import com.github.bakuplayz.cropclick.datacontainers.services.AbstractLocalDataService;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.DoubleChest;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Local in-memory implementation of {@link AutofarmDataService}, used when there
 * is no database configured.
 */
public final class LocalAutofarmService extends AbstractLocalDataService<Autofarm> implements AutofarmDataService {

    public LocalAutofarmService() {
        super("autofarms.json", new TypeReference<Map<String, Autofarm>>() {
        });
    }


    @Override
    protected String getDefaultIdentifier(@NotNull Autofarm autofarm) {
        return autofarm.getFarmerId().toString();
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByCrop(@NotNull Location location) {
        return CompletableFuture.completedFuture(
                dataContainer.getMany().stream()
                        .filter(Autofarm::isEnabled)
                        .filter(farm -> farm.getCropLocation().equals(location))
                        .findFirst().orElse(null)
        );
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByDispenser(@NotNull Location location) {
        return CompletableFuture.completedFuture(
                dataContainer.getMany().stream()
                        .filter(Autofarm::isEnabled)
                        .filter(farm -> farm.getDispenserLocation().equals(location))
                        .findFirst().orElse(null)
        );
    }


    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public CompletableFuture<Autofarm> getOneByContainer(@NotNull Location location) {
        return CompletableFuture.completedFuture(
                dataContainer.getMany().stream()
                        .filter(Autofarm::isEnabled)
                        .filter(farm -> {
                            boolean filterByDoubly = filterByDoubly(farm, location);
                            boolean filterByDoubleChest = filterByDoubleChest(farm, location.getBlock());

                            if (filterByDoubly || filterByDoubleChest) {
                                return true;
                            }

                            Location containerLocation = farm.getContainerLocation();
                            return containerLocation.equals(location);
                        }).findFirst().orElse(null)
        );
    }


    /**
     * Filters searches based on {@link DoublyLocation provided doubly location} matching with the {@link Location provided location}.
     *
     * @param doubly   the doubly location.
     * @param location the location to match with the doubly location.
     *
     * @return true if it matches, otherwise false.
     */
    private boolean filterByDoubly(@NotNull DoublyLocation doubly, @NotNull Location location) {
        Location singlyLocation = doubly.getSingly();
        Location doublyLocation = doubly.getDoubly();
        return singlyLocation.equals(location) || doublyLocation.equals(location);
    }


    /**
     * Filters searches based on {@link DoublyLocation doubly location} matching with the {@link Location location}.
     *
     * @param autofarm the farm to base the doubly location on.
     * @param location the location to match with the doubly location.
     *
     * @return true if it matches, otherwise false.
     */
    private boolean filterByDoubly(@NotNull Autofarm autofarm, @NotNull Location location) {
        Location containerLocation = autofarm.getContainerLocation();
        if (!(containerLocation instanceof DoublyLocation)) {
            return false;
        }

        return filterByDoubly((DoublyLocation) autofarm.getContainerLocation(), location);
    }


    /**
     * Filters searches based on {@link DoubleChest double chests} matching with the {@link Block provided block's} location.
     *
     * @param autofarm the farm to base the doubly location on.
     * @param block    the block to match with the doubly location.
     *
     * @return true if it matches, otherwise false.
     */
    private boolean filterByDoubleChest(@NotNull Autofarm autofarm, @NotNull Block block) {
        if (!Blocks.isDoubleChest(block)) {
            return false;
        }

        DoublyLocation doubleChest = Locations.findDoubly(block);
        if (doubleChest == null) {
            return false;
        }

        return filterByDoubly(doubleChest, autofarm.getContainerLocation());
    }

}
