package com.walmart.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class ReservationUtil {
    public static void main(String arg[]){
      System.out.println(getNextNumber());
    }

    private static final AtomicInteger counter = new AtomicInteger(1000);

    public static int getNextNumber(){
        return counter.incrementAndGet();
    }
}
