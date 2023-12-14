package org.parkinglot;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeastCarsParkedSelectorTest {

    @Test
    void shouldSelectParkingLotWithLeastCars() {
        ParkingLot parkingLot1 = mock(ParkingLot.class);
        when(parkingLot1.numberOfCars()).thenReturn(5);
        ParkingLot parkingLot2 = mock(ParkingLot.class);
        when(parkingLot2.numberOfCars()).thenReturn(3);
        LeastCarsParkedSelector leastCarsParkedSelector = new LeastCarsParkedSelector();
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);
        ParkingLot parkingLot = leastCarsParkedSelector.select(parkingLots);
        assertEquals(parkingLot, parkingLot2);
    }
}
