package model;

import java.util.Date;
import java.util.Objects;



public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date CheckOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date CheckOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.CheckOutDate = CheckOutDate;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate(){
        return CheckOutDate;
    }

    @Override
    public String toString(){
        return customer.toString() + room.toString() + " Check-in Date: " + checkInDate.toString()
                + " Check-out Date: " + CheckOutDate.toString() + "\n";
    }
}
