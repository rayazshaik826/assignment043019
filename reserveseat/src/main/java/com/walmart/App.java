package com.walmart;

import com.walmart.dao.TicketDAOImpl;
import com.walmart.service.TicketServiceImpl;
import com.walmart.dto.SeatHold;
import com.walmart.service.TicketService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );

      /*  TicketService ticketService = new TicketServiceImpl(new TicketDAO());
        System.out.println( "Number of Seats available:" + ticketService.numSeatsAvailable());
        TicketServiceImpl ticketService1 =  (TicketServiceImpl)ticketService;
        System.out.println( "Seats available:" + ticketService1.getAvailableSeats());

        SeatHold seatHold = ticketService.findAndHoldSeats(2,"rayaz@gmail.com");
        System.out.println(seatHold.toString());
        System.out.println( "Number of Seats available:" + ticketService.numSeatsAvailable());
        Thread.sleep(15*1000);
        ticketService.reserveSeats(1001,"rayaz@gmail.com");

        System.out.println(seatHold.toString());
        System.out.println( "Number of Seats available:" + ticketService.numSeatsAvailable());
        */

        TicketService ticketService = new TicketServiceImpl(new TicketDAOImpl());

        Thread thread1 = new Thread(()->{
            SeatHold seatHold = ticketService.findAndHoldSeats(3,"shaik@gmail.com");
            System.out.println(seatHold);
        });
        Thread thread2 = new Thread(()->{
            SeatHold seatHold = ticketService.findAndHoldSeats(4,"Rayaz@gmail.com");
            System.out.println(seatHold);
        });
        System.out.println( "Number of Seats available:" + ticketService.numSeatsAvailable());
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println( "Number of Seats available:" + ticketService.numSeatsAvailable());


       /* System.out.println( "Number of Seats available:" + ticketService.numSeatsAvailable());
        System.out.println( "Seats available:" + ticketService1.getAvailableSeats());

        seatHold = ticketService.findAndHoldSeats(3,"shaik@gmail.com");
        System.out.println(seatHold.toString());

        System.out.println( "Number of Seats available:" + ticketService.numSeatsAvailable());
        System.out.println( "Seats available:" + ticketService1.getAvailableSeats());*/
    }
}
