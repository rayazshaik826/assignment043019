package com.walmart.dao;

import com.walmart.dto.Seat;
import com.walmart.dto.SeatHold;
import com.walmart.utils.ReservationUtil;

import java.util.*;
import java.util.stream.Collectors;

public class TicketDAOImpl implements TicketDAO {
    private Set<Seat> availableSeats = new LinkedHashSet();
    private Map<Integer, SeatHold> holdSeatMap = new LinkedHashMap<>();

    public TicketDAOImpl() {
        availableSeats = new LinkedHashSet(Arrays.asList(new Seat("A1"), new Seat("A2"), new Seat("A3"),
                new Seat("B1"), new Seat("B2"), new Seat("B3")));
    }

    @Override
    public Set<Seat> getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public Map<Integer, SeatHold> getHoldSeatMap() {
        return holdSeatMap;
    }

    @Override
    public SeatHold holdSeats(int numSeats, String customerEmail) {
        Set<Seat> seats = availableSeats.stream()
                .limit(numSeats)
                .collect(Collectors.toSet());
        availableSeats.removeAll(seats);
        int seatHoldId = ReservationUtil.getNextNumber();
        SeatHold holdSeat = new SeatHold(seatHoldId, customerEmail, seats, new Date(), false, this);

        holdSeatMap.put(seatHoldId, holdSeat);
        return holdSeat;
    }
}
