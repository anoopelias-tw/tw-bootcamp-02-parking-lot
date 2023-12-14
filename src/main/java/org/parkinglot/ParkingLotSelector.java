package org.parkinglot;

import java.util.ArrayList;
import java.util.List;

public interface ParkingLotSelector {
    ParkingLot select(List<ParkingLot> parkingLots);
}
