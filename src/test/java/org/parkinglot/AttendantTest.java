package org.parkinglot;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AttendantTest {

    @Test
    public void testAttendantCanDirectTheCarToHisParkingLot() throws AlreadyParkedException, AllParkingLotsAreFullException, ParkingLotFullException {
        ParkingLot parkingLot = mock(ParkingLot.class);
        Attendant attendant = new Attendant(new FirstAvailableParkingLotSelector());
        attendant.assignLot(parkingLot);
        attendant.park(mock(Parkable.class));

        verify(parkingLot).park(Mockito.any(Parkable.class));
    }

    @Test
    public void testAttendantThrowsErrorWhenAllParkingLotsAreFull() throws AlreadyParkedException, AllParkingLotsAreFullException {
        ParkingLot parkingLot = new ParkingLot(1);
        Attendant attendant = new Attendant(new FirstAvailableParkingLotSelector());
        attendant.assignLot(parkingLot);
        attendant.park(mock(Parkable.class));
        assertThrows(AllParkingLotsAreFullException.class, () -> attendant.park(mock(Parkable.class)));
    }

    @Test
    public void testAttendantCanHandleMultipleParkingLots() throws AlreadyParkedException, AllParkingLotsAreFullException {
        Attendant attendant = new Attendant(new FirstAvailableParkingLotSelector());
        attendant.assignLot(new ParkingLot(1));
        attendant.assignLot(new ParkingLot(1));

        attendant.park(mock(Parkable.class));
        attendant.park(mock(Parkable.class));

        assertThrows(AllParkingLotsAreFullException.class, () -> attendant.park(mock(Parkable.class)));
    }

    @Test
    public void testParkAlreadyParkedCarInAnotherLot() throws AlreadyParkedException, AllParkingLotsAreFullException {
        Attendant attendant = new Attendant(new FirstAvailableParkingLotSelector());
        attendant.assignLot(new ParkingLot(1));
        attendant.assignLot(new ParkingLot(1));

        Parkable car = mock(Parkable.class);
        attendant.park(car);

        assertThrows(AlreadyParkedException.class, () -> attendant.park(car));
    }

    @Test
    public void testAttendantCanUnparkTheCar() throws AlreadyParkedException, AllParkingLotsAreFullException {
        Attendant attendant = new Attendant(new FirstAvailableParkingLotSelector());
        attendant.assignLot(new ParkingLot(1));

        Parkable car = mock(Parkable.class);
        attendant.park(car);

        attendant.unpark(car);
        attendant.park(car);
    }

}
