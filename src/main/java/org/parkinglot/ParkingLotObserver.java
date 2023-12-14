package org.parkinglot;

public interface ParkingLotObserver {
    public void notifyFull(ParkingLot parkingLot);

    public void notifyAvailable(ParkingLot parkingLot);
}
