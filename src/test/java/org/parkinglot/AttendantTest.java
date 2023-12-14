package org.parkinglot;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    public void testLeastAvailableCarSelector() throws AlreadyParkedException, AllParkingLotsAreFullException, ParkingLotFullException {
        ParkingLotSelector parkingLotSelector= mock(ParkingLotSelector.class);
        Attendant attendant= new Attendant(parkingLotSelector);
        ParkingLot parkingLot1 = mock(ParkingLot.class);
        ParkingLot parkingLot2 = mock(ParkingLot.class);
        when(parkingLot1.isFull()).thenReturn(false);
        when(parkingLot2.isFull()).thenReturn(false);
        attendant.assignLot(parkingLot1);
        attendant.assignLot(parkingLot2);

        Parkable car = mock(Parkable.class);

        when(parkingLotSelector.select(any())).thenReturn(parkingLot1);
        attendant.park(car);
        verify(parkingLot1, times(1)).park(car);
        verify(parkingLot2, never()).park(car);
    }

}
