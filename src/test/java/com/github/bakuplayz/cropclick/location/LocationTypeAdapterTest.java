package com.github.bakuplayz.cropclick.location;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class LocationTypeAdapterTest {

    private LocationTypeAdapter typeAdapter;


    @Before
    public void initialize() {
        MockBukkit.mock();
    }


    @BeforeEach
    public void setUp() {
        typeAdapter = new LocationTypeAdapter();
    }


    @After
    public void tearDown() {
        MockBukkit.unload();
    }

    //TODO: Fix World Mock...

   /* @Test
    public void testDeserialize() {
        JsonObject worldElement = new JsonObject();
        worldElement.addProperty("world", "world");
        worldElement.addProperty("x", 0.0);
        worldElement.addProperty("y", 0.0);
        worldElement.addProperty("z", 0.0);

        Location result = typeAdapter.deserialize(worldElement, null, null);
        System.out.println(result);
    }

    @Test
    public void testSerialize() {
    }

*/
}