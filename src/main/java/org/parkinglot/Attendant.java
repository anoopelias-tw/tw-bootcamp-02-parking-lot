package org.parkinglot;

import java.util.HashMap;
import java.util.Map;

public class Attendant implements ParkingLotObserver {

    private final ParkingLots availableLots = new ParkingLots();
    private final Map<Parkable, ParkingLot> cars = new HashMap<>();

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
            ParkingLot parkingLot = availableLots.iterator().next();
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
