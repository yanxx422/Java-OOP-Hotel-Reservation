package model;

import java.util.Date;
import java.util.Objects;



public class Reservation {
    private final Customer customer;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return customer.equals(that.customer) && room.equals(that.room) && checkInDate.equals(that.checkInDate) && CheckOutDate.equals(that.CheckOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, CheckOutDate);
    }

    @Override
    public String toString(){
        return customer.toString() + room.toString() + " Check-in Date: " + checkInDate.toString()
                + " Check-out Date: " + CheckOutDate.toString() + "\n";
    }
}
