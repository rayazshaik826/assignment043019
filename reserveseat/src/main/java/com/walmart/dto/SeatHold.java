package com.walmart.dto;

import com.walmart.dao.TicketDAO;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.Timer;
import org.apache.log4j.Logger;

public class SeatHold extends TimerTask {

    static Logger log = Logger.getLogger(SeatHold.class.getName());

    private int seatHoldId;
    private String customerEmail;
    private Set<Seat> seats = new LinkedHashSet<>();
    private Date holdTime;
    private boolean holdTimeExpired;
    private Timer timer;
    private TicketDAO ticketDAO;
    private String reservationCode;
    boolean confirmReservation = false;

    public boolean isConfirmReservation() {
        return confirmReservation;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public void setConfirmReservation(boolean confirmReservation) {
        timer.cancel();
        this.confirmReservation = confirmReservation;
    }

    public SeatHold(int seatHoldId, String customerEmail, Set<Seat> seats, Date holdTime, boolean holdTimeExpired, TicketDAO ticketDAO) {
        this.seatHoldId = seatHoldId;
        this.customerEmail = customerEmail;
        this.seats = seats;
        this.holdTime = holdTime;
        this.holdTimeExpired = holdTimeExpired;
        this.timer = new Timer();
        timer.schedule(this, 10 * 1000);
        this.ticketDAO = ticketDAO;
    }

    @Override
    public void run() {
        log.debug("HoldTime Expired for the Hold Seat :"+ seatHoldId);
        holdTimeExpired = true;
        timer.cancel();
        ticketDAO.getAvailableSeats().addAll(seats);
        this.seats.removeAll(seats);
        ticketDAO.getHoldSeatMap().remove(seatHoldId);
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public int getSeatHoldId() {
        return seatHoldId;
    }

    @Override
    public String toString() {

        return "seatHoldId :" + seatHoldId + " customerEmail :" + customerEmail + " seats :" + seats + " holdTime :" + holdTime + "holdTimeExpired :" + holdTimeExpired;

    }

}
