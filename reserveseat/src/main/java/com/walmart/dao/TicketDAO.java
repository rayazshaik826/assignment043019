package com.walmart.dao;

import com.walmart.dto.Seat;
import com.walmart.dto.SeatHold;

import java.util.Map;
import java.util.Set;


public interface TicketDAO {

    Set<Seat> getAvailableSeats();

    Map<Integer, SeatHold> getHoldSeatMap();

    SeatHold holdSeats(int numSeats, String customerEmail);

}