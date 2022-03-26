package service;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class Tester {
    public static void main(String[] args) {
        ReservationService reservationService = ReservationService.getInstance();
        CustomerService customerService = CustomerService.getInstance();

        customerService.addCustomer("james_007@gmail.com","James", "Bond");
        customerService.addCustomer("Hasan@hotmail.com","Hasan", "Piker");
        Customer customer_1 = customerService.getCustomer("james_007@gmail.com");
        Customer customer_2 = customerService.getCustomer("Hasan@hotmail.com");

        System.out.println("Should print Customer Hasan\n");
        System.out.println(customer_2);

        reservationService.addRoom("1",100.0, RoomType.SINGLE);
        reservationService.addRoom("2",140.0, RoomType.SINGLE);
        reservationService.addRoom("3",120.0, RoomType.DOUBLE);
        reservationService.addRoom("4",160.0, RoomType.DOUBLE);



        reservationService.reserveARoom(customer_1, reservationService.getARoom("2"), parseDate("2022-03-25"), parseDate("2022-03-28"));

        System.out.println("\nShould print room 1, 3, 4\n");
        System.out.println(reservationService.findRooms(parseDate("2022-03-27"),parseDate("2022-4-1")));

        reservationService.reserveARoom(customer_1, reservationService.getARoom("4"), parseDate("2022-03-27"), parseDate("2022-4-1"));

        System.out.println("\nShould print room 1, 2, 3\n");
        System.out.println(reservationService.findRooms(parseDate("2022-4-1"), parseDate("2022-4-5")));
        System.out.println("\nShould print room 1, 3\n");
        System.out.println(reservationService.findRooms(parseDate("2022-3-28"), parseDate("2022-4-2")));
        reservationService.reserveARoom(customer_1, reservationService.getARoom("1"), parseDate("2022-03-8"), parseDate("2022-04-02"));
        System.out.println("\nShould print room 3\n");
        System.out.println(reservationService.findRooms(parseDate("2022-3-25"), parseDate("2022-4-1")));

        reservationService.reserveARoom(customer_1, reservationService.getARoom("2"), parseDate("2022-03-25"), parseDate("2022-03-28"));


    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
