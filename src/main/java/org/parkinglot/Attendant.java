package org.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendant implements ParkingLotObserver {

    private final List<ParkingLot> availableLots = new ArrayList<>();
    private final Map<Parkable, ParkingLot> cars = new HashMap<>();

    private final ParkingLotSelector parkingLotSelector;

    public Attendant() {
        parkingLotSelector = new FirstAvailableParkingLotSelector();
    }

    public void assignLot(ParkingLot parkingLot) {
        parkingLot.addObserver(this);

        if (!parkingLot.isFull()) {
            availableLots.add(parkingLot);
        }
    }

    public void park(Parkable car) throws AlreadyParkedException, AllParkingLotsAreFullException {
        if (availableLots.isEmpty()) {
            throw new AllParkingLotsAreFullException();
        }

        if (cars.containsKey(car)) {
            throw new AlreadyParkedException();
        }

        try {
            ParkingLot parkingLot = parkingLotSelector.select(availableLots);
            parkingLot.park(car);
            cars.put(car, parkingLot);
        } catch (ParkingLotFullException e) {
            // Unreachable code
        }


    }

    @Override
    public void notifyFull(ParkingLot parkingLot) {
        availableLots.remove(parkingLot);
    }

    @Override
    public void notifyAvailable(ParkingLot parkingLot) {
        availableLots.add(parkingLot);
    }

    public void unpark(Parkable car) {
        try {
            cars.get(car).unpark(car);
            cars.remove(car);
        } catch (NotParkedHereException e) {
            System.out.println("Unreachable code");
        }
    }
}
