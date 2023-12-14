package org.parkinglot;

import java.util.HashSet;

public class ParkingLotObservers extends HashSet<ParkingLotObserver> {

    public void notifyFull(ParkingLot parkingLot) {
        for (ParkingLotObserver observer : this) {
            observer.notifyFull(parkingLot);
        }
    }

    public void notifyAvailable(ParkingLot parkingLot) {
        for (ParkingLotObserver observer : this) {
            observer.notifyAvailable(parkingLot);
        }
    }
}
