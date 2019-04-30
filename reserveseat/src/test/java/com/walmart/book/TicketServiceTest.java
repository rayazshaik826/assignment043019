package com.walmart.book;

import com.walmart.dao.TicketDAOImpl;
import com.walmart.dto.SeatHold;
import com.walmart.service.TicketServiceImpl;
import com.walmart.service.TicketService;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketServiceTest {
    static Logger log = Logger.getLogger(TicketServiceTest.class.getName());
    TicketService ticketService;

    @BeforeEach
    public void setUp() throws Exception {
        ticketService = new TicketServiceImpl(new TicketDAOImpl());
    }

    @AfterEach
    public void tearDown() throws Exception {
        ticketService = null;
    }

    @Test
    public void testNumSeatsAvailable() {
        assertTrue(ticketService.numSeatsAvailable() == 6);
    }

    @Test
    public void testFindAndHoldSeats() throws InterruptedException {
        SeatHold seatHold = ticketService.findAndHoldSeats(2, "rayaz@gmail.com");
        assertTrue(ticketService.numSeatsAvailable() == 4);
        assertTrue(seatHold.getSeats().size() == 2);

        Thread.sleep(15 * 1000);
        ticketService.reserveSeats(seatHold.getSeatHoldId(),seatHold.getCustomerEmail());
        assertTrue(ticketService.numSeatsAvailable() == 6);
        assertTrue(seatHold.getSeats().size() == 0);
        assertFalse(seatHold.isConfirmReservation());
    }

    @Test
    public void testReserveSeats() throws InterruptedException {

        SeatHold seatHold = ticketService.findAndHoldSeats(2, "rayaz@gmail.com");
        assertTrue(ticketService.numSeatsAvailable() == 4);
        assertTrue(seatHold.getSeats().size() == 2);

        Thread.sleep(5 * 1000);
        ticketService.reserveSeats(seatHold.getSeatHoldId(),seatHold.getCustomerEmail());
        assertTrue(ticketService.numSeatsAvailable() == 4);
        assertTrue(seatHold.getSeats().size() == 2);
        assertTrue(seatHold.isConfirmReservation());
    }

    @Test
    public void testFindAndHoldConcurrentUser() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            SeatHold seatHold = ticketService.findAndHoldSeats(3, "shaik@gmail.com");
            log.debug(seatHold);
        });
        Thread thread2 = new Thread(() -> {
            SeatHold seatHold = ticketService.findAndHoldSeats(4, "Rayaz@gmail.com");
            log.debug(seatHold);
        });
        Thread thread3 = new Thread(() -> {
            SeatHold seatHold = ticketService.findAndHoldSeats(3, "basha@gmail.com");
            log.debug(seatHold);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertTrue(ticketService.numSeatsAvailable() == 3 || ticketService.numSeatsAvailable() == 2);

        thread3.start();
        thread3.join();

        assertTrue(ticketService.numSeatsAvailable() == 0 || ticketService.numSeatsAvailable() == 2);

    }
}