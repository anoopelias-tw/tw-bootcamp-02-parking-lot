package org.parkinglot;

import java.util.List;

public class FirstAvailableParkingLotSelector implements ParkingLotSelector {

    @Override
    public ParkingLot select(List<ParkingLot> availableParkingLots) {
        return availableParkingLots.iterator().next();
    }
}
