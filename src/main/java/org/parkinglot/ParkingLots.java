package org.parkinglot;

import java.util.HashSet;

public class ParkingLots extends HashSet<ParkingLot> {

    public ParkingLot getAvailableParkingLot() throws AllParkingLotsAreFullException {
        for (ParkingLot parkingLot : this) {
            if (!parkingLot.isFull()) {
                return parkingLot;
            }
        }
        throw new AllParkingLotsAreFullException();
    }

}
