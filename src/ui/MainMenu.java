package ui;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public class MainMenu {
    private static HotelResource hotelresource;
    private static AdminResource adminresource;
    private static Scanner scanner;
    private static String[] options;

    MainMenu(){
        hotelresource = HotelResource.getInstance();
        adminresource = AdminResource.getInstance();
        scanner = new Scanner(System.in);
        options = new String[] {
                "1. Find and reserve a room",
                "2. See my reservations",
                "3. Create an Account",
                "4. Admin",
                "5. Exit",
        };
    }


    public static void printMenu(String[] options){
        System.out.print("----------------------------------------------------- \n");
        System.out.print("Welcome to the Hotel Reservation Application \n");
        System.out.print("----------------------------------------------------- \n");
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
    public void StartOptions(){
        while(true) {
            boolean finish_option = false;
            printMenu(options);

            String option = scanner.next();
            String regex = "[1-5]";
            while(!finish_option){
                if (!Pattern.compile(regex).matcher(option).matches()){
                    System.out.println("Please enter number 1 to 5 to proceed.");
                    option = scanner.next();
                }else{
                    finish_option = true;
                }
            }
            Integer option_number = Integer.parseInt(option);
            try {
                switch (option_number) {
                    case 1:
                        FindAndReserve();
                        break;
                    case 2:
                        seeCustomerReservation();
                        break;
                    case 3:
                        createCustomerAccount();
                        break;
                    case 4:
                        AdminMenu aMenu = new AdminMenu();
                        aMenu.StartOptions();
                    case 5:
                        System.out.println("Closing the program, see you next time.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Inputs! Going back to the menu.....");
                        throw new InputMismatchException();

                }
            } catch(InputMismatchException  ex){
                System.out.println("Invalid Inputs! Going back to the menu.....");
                option = scanner.next();
            }
        }

    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public void FindAndReserve(){
        String dateRegex =  "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
        String emailRegex = "^(.+)@(.+).com$";
        String roomNumberRegex = "[0-9]+";

        Date checkInDate = null;
        String checkInDateText = "";
        Date checkOutDate = null;
        String checkOutDateText = "";
        String roomNumber = "";
        String email = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        boolean finished_checkInDate = false;
        boolean finished_checkOutDate = false;
        boolean finished_book_decision = false;
        boolean finished_account_decision = false;
        boolean finished_email = false;
        boolean finished_roomNumber = false;

        System.out.println("Enter CheckIn Date yyyy-mm-dd example 2011-1-1");
        while(!finished_checkInDate){
            checkInDateText = scanner.next();
            if (!Pattern.compile(dateRegex).matcher(checkInDateText).matches()){
                System.out.println("Date is not valid, format yyyy-mm-dd example 2011-1-1");

            } else{
                checkInDate = parseDate(checkInDateText);
                finished_checkInDate = true;
            }
        }

        System.out.println("Enter checkOut Date yyyy-mm-dd example 2011-1-1");
        while(!finished_checkOutDate) {
            checkOutDateText = scanner.next();
            if (!Pattern.compile(dateRegex).matcher(checkOutDateText).matches()) {
                System.out.println("Date is not valid, format yyyy-mm-dd example 2011-1-1");

            } else {
                checkOutDate = parseDate(checkOutDateText);
                if (checkOutDate.equals(checkInDate) || checkOutDate.before(checkInDate)) {
                    System.out.println("CheckOut Date must be after CheckIn Date. Please enter another checkOut Date, format: yyyy-mm-dd example 2011-1-1");
                } else {
                    finished_checkOutDate = true;
                }
            }
        }
        hotelresource.printRooms(hotelresource.findARoom(checkInDate, checkOutDate));

        System.out.println("Would you like to book a room? y/n");
        while(!finished_book_decision){
            switch (scanner.next().charAt(0)) {
                case 'Y':
                case 'y':
                    finished_book_decision = true;
                    System.out.println("Do you have an account with us? y/n");
                    while(!finished_account_decision){
                        switch (scanner.next().charAt(0)) {
                            case 'Y':
                            case 'y':
                                finished_account_decision = true;
                                System.out.println("Enter Email Format: name@domain.com ");
                                while(!finished_email) {
                                    email = scanner.next();
                                    if (!Pattern.compile(emailRegex).matcher(email).matches()) {
                                        System.out.println("email is not valid, format: name@domain.com.");
                                    } else if (!adminresource.getAllCustomers().contains(adminresource.getCustomer(email))) {
                                        System.out.println("This email address is not in our system, try another one.");
                                    } else {
                                        finished_email = true;
                                    }
                                }

                                System.out.println("What room number would you like to reserve? ");
                                while(!finished_roomNumber){
                                    roomNumber = scanner.next();
                                    if (!Pattern.compile(roomNumberRegex).matcher(roomNumber).matches()){
                                        System.out.println("Room number is not valid, please enter numbers only.");
                                    }
                                    else{
                                        finished_roomNumber = true;
                                    }
                                }

                                Reservation newReservation = hotelresource.bookARoom(email,roomNumber,checkInDate, checkOutDate);
                                if (newReservation != null){
                                    System.out.println(newReservation);
                                }
                                break;
                            case 'N':
                            case 'n':
                                finished_account_decision = true;
                                break;
                            default:
                                System.out.println("Invalid inputs! Please enter y or n");
                        }
                    }

                    break;
                case 'N':
                case 'n':
                    finished_book_decision = true;
                    break;
                default:
                    System.out.println("Invalid inputs! Please enter y or n");

            }
        }
    }

    public void seeCustomerReservation(){
        String emailRegex = "^(.+)@(.+).com$";
        String email = "";

        boolean finished_account_decision = false;
        boolean finished_email = false;

        System.out.println("Do you have an account with us? y/n");
        while(!finished_account_decision){
            switch (scanner.next().charAt(0)) {
                case 'Y':
                case 'y':
                    finished_account_decision = true;
                    System.out.println("Enter Email Format: name@domain.com ");
                    while(!finished_email){
                        email = scanner.next();
                        if (!Pattern.compile(emailRegex).matcher(email).matches()){
                            System.out.println("email is not valid, format: name@domain.com.");
                        }else if (!adminresource.getAllCustomers().contains(adminresource.getCustomer(email))){
                            System.out.println("This email address is not in our system, try another one.");
                        }
                        else{
                            finished_email = true;
                        }
                    }
                    Collection<Reservation> reservations = hotelresource.getCustomersReservations(email);

                    if (reservations != null){
                        for (Reservation reservation: reservations){
                            System.out.println(reservation);
                        }
                    }
                    else{
                        System.out.println("This account doesn't have any reservations.");

                    }

                    break;

                case 'N':
                case 'n':
                    System.out.print("You need to create an account first. Going back to menu... \n ");
                    finished_account_decision = true;
                    break;
                default:
                    System.out.println("Invalid inputs! Please enter y or n");
            }
        }



    }

    public void createCustomerAccount(){
        String email = "";
        String firstName = "";
        String lastName = "";

        boolean finished_email = false;
        boolean finished_firstName = false;
        boolean finihsed_lastName = false;

        String nameRegex = "[a-zA-Z]+";
        String emailRegex = "^(.+)@(.+).com$";;

        System.out.println("Enter Email Format: name@domain.com ");
        while(!finished_email){
            email = scanner.next();
            if (!Pattern.compile(emailRegex).matcher(email).matches()){
                System.out.println("email is not valid, format: name@domain.com.");
            } else{
                finished_email = true;
            }
        }

        System.out.println("Enter first name ");
        while(!finished_firstName){
            firstName = scanner.next();
            if (!Pattern.compile(nameRegex).matcher(firstName).matches()){
                System.out.println("Name is not valid, enter chatacters only.");
            } else{
                finished_firstName = true;
            }
        }

        System.out.println("Enter last name ");
        while(!finihsed_lastName){
            lastName = scanner.next();
            if (!Pattern.compile(nameRegex).matcher(lastName).matches()){
                System.out.println("Name is not valid, enter chatacters only.");
            } else{
                finihsed_lastName = true;
            }
        }
        hotelresource.createACustomer(email, firstName, lastName);
    }

}