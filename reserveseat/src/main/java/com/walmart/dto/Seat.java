package com.walmart.dto;

public class Seat {
    String seatId;

    public Seat(String seatId) {
        this.seatId = seatId;
    }

    public String getSeatId() {
        return seatId;
    }
    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    @Override
    public String toString(){

        return "seatId : "+seatId;
    }

    @Override
    public int hashCode(){
      return seatId.hashCode();
    }
}
