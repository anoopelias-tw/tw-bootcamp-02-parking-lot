package org.parkinglot;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LeastCarsParkedSelector implements ParkingLotSelector {
    @Override
    public ParkingLot select(List<ParkingLot> parkingLots) {
        return parkingLots.size() > 0 ? parkingLots.stream()
                .sorted(Comparator.comparing(ParkingLot::numberOfCars))
                .findFirst().get() : null;
    }
}
