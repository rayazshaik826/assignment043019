package com.walmart.service;

import com.walmart.dao.TicketDAO;
import com.walmart.dto.Seat;
import com.walmart.dto.SeatHold;

import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class TicketServiceImpl implements TicketService {

    static Logger log = Logger.getLogger(TicketServiceImpl.class.getName());

    private TicketDAO ticketDAO;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    public TicketServiceImpl(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public Set<Seat> getAvailableSeats() {
        return ticketDAO.getAvailableSeats();
    }
    public int numSeatsAvailable() {
        try {
            readLock.lock();
            return getAvailableSeats().size();
        } finally {
            readLock.unlock();
        }
    }

    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        SeatHold seatHold = null;

        try{
            writeLock.lock();
            if (getAvailableSeats().size() < numSeats) {
                log.debug("For User "+ customerEmail +" : "+numSeats + ": Seats not available to hold");
                return null;
            } else {
                seatHold = ticketDAO.holdSeats(numSeats, customerEmail);
                return seatHold;
            }
        }catch (Exception ex) {
            log.debug("Exception Rised:" + ex);
            throw ex;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {

        SeatHold seatHold = ticketDAO.getHoldSeatMap().get(seatHoldId);

        if(seatHold == null){
            log.debug("SeatHoldID: " +seatHoldId +": Time Out ..Please hold the tickets before reserve..");
            return StringUtils.EMPTY;
        }
        seatHold.setConfirmReservation(true);
        //perform Persistence Operation to get reservaton Code
        seatHold.setReservationCode("CNF" + seatHoldId);
        log.debug("SeatHoldID: " +seatHoldId +"Reservation Confirmed Reservaton Code: "+ seatHold.getReservationCode() );
        return seatHold.getReservationCode();
    }
}
