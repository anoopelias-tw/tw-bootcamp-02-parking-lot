package org.parkinglot;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ParkingLotTest {

    @Test
    public void testParkCar() throws AlreadyParkedException, ParkingLotFullException {
        Parkable car = Mockito.mock(Parkable.class);
        ParkingLot parkingLot = new ParkingLot(1, 100);
        parkingLot.park(car);
    }

    @Test
    public void testParkAlreadyParkedCar() throws AlreadyParkedException, ParkingLotFullException {
        Parkable car = Mockito.mock(Parkable.class);
        ParkingLot parkingLot = new ParkingLot(2, 100);
        parkingLot.park(car);
        assertThrows(AlreadyParkedException.class,() -> parkingLot.park(car));
    }

    @Test
    public void testParkInFullParkingLot() throws AlreadyParkedException, ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(1, 100);
        Parkable silverStallion = Mockito.mock(Parkable.class);
        Parkable jeepCompass = Mockito.mock(Parkable.class);
        parkingLot.park(silverStallion);
        assertThrows(ParkingLotFullException.class,() -> parkingLot.park(jeepCompass));
    }

    @Test
    public void testUnparkCar() throws ParkingLotFullException, AlreadyParkedException, NotParkedHereException {
        Parkable car = Mockito.mock(Parkable.class);
        ParkingLot parkingLot = new ParkingLot(1, 100);
        parkingLot.park(car);
        parkingLot.unpark(car);

        Parkable nextCar = Mockito.mock(Parkable.class);
        parkingLot.park(nextCar);
    }

    @Test
    public void testUnparkACarThatIsNotParkedThere() {
        Parkable car = Mockito.mock(Parkable.class);
        ParkingLot parkingLot = new ParkingLot(1, 100);
        assertThrows(NotParkedHereException.class, () -> parkingLot.unpark(car));
    }

    @Test
    public void testOwnerNotifiedOnParkingFull() throws ParkingLotFullException, AlreadyParkedException {
        ParkingLotObserver owner = Mockito.mock(ParkingLotObserver.class);
        ParkingLot parkingLot = new ParkingLot(2, 100);
        parkingLot.addObserver(owner);

        parkingLot.park(Mockito.mock(Parkable.class));
        parkingLot.park(Mockito.mock(Parkable.class));

        verify(owner, times(1)).notifyFull(parkingLot);
    }

    @Test
    public void testBothOwnerAndTrafficIsNotifiedWhenParkingIsFull() throws ParkingLotFullException, AlreadyParkedException {
        ParkingLotObserver owner = Mockito.mock(ParkingLotObserver.class);
        ParkingLotObserver trafficPolice = Mockito.mock(ParkingLotObserver.class);

        ParkingLot parkingLot = new ParkingLot(2, 100);
        parkingLot.addObserver(owner);
        parkingLot.addObserver(trafficPolice);

        parkingLot.park(Mockito.mock(Parkable.class));
        parkingLot.park(Mockito.mock(Parkable.class));

        verify(owner, times(1)).notifyFull(parkingLot);
        verify(trafficPolice, times(1)).notifyFull(parkingLot);
    }

    @Test
    public void testBothOwnerAndTrafficPoliceIsNotifiedWhenParkingIsAvailableAfterFull()
            throws ParkingLotFullException, AlreadyParkedException, NotParkedHereException {
        ParkingLotObserver owner = Mockito.mock(ParkingLotObserver.class);
        ParkingLotObserver trafficPolice = Mockito.mock(ParkingLotObserver.class);
        Parkable car = Mockito.mock(Parkable.class);

        ParkingLot parkingLot = new ParkingLot(1, 100);
        parkingLot.addObserver(owner);
        parkingLot.addObserver(trafficPolice);

        parkingLot.park(car);
        parkingLot.unpark(car);

        verify(owner, times(2)).notifyAvailable(parkingLot);
        verify(trafficPolice, times(1)).notifyAvailable(parkingLot);
    }

    @Test
    void shouldReturnCostOfAParkingLot(){
        ParkingLot parkingLot = new ParkingLot(2, 100);
        assertEquals(100, parkingLot.cost());
    }
}
