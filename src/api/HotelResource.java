package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public final class HotelResource {
    private static HotelResource hotel_resource;
    private ReservationService reservationService ;
    private CustomerService customerService;

    private HotelResource(){
        reservationService = ReservationService.getInstance();
        customerService = CustomerService.getInstance();
    }

    public static HotelResource getInstance(){
        //lazy initialization
        if (hotel_resource == null){
            hotel_resource = new HotelResource();
        }
        return hotel_resource;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);

    }

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);

    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckOutDate){
        return reservationService.reserveARoom(customerService.getCustomer(customerEmail), room, checkInDate, CheckOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String email){
        return reservationService.getCustomersReservation(getCustomer(email));
    }

    public void printRooms(Collection<IRoom> RoomList){
        for (IRoom room:RoomList ){
            System.out.println(room);
        }
    }

    public Collection<IRoom> findARoom(Date CheckIn, Date CheckOut){
        return reservationService.findRooms(CheckIn, CheckOut);

    }
}
