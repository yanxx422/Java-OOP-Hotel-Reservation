package api;


import model.Customer;
import model.IRoom;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public final class AdminResource {
    private static AdminResource admin_resource;
    private ReservationService reservationService;
    private CustomerService customerService;

    private AdminResource(){
        reservationService = ReservationService.getInstance();
        customerService = CustomerService.getInstance();
    }

    public static AdminResource getInstance(){
        //lazy initialization
        if (admin_resource == null){
            admin_resource = new AdminResource();
        }
        return admin_resource;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void addRoom(String roomNumber, Double price, RoomType enumeration) {

        reservationService.addRoom(roomNumber, price, enumeration);
    }


    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();

    }

    public void displayAllReservations(){
        reservationService.printAllReservation();
    }

    public void displayAllRooms(){
        reservationService.printAllRoom();
    }

    public void displayAllCustomers(){
        customerService.printAllCustomers();
    }


}
