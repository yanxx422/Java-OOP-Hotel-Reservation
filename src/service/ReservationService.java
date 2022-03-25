package service;
import model.*;

import java.util.*;

public final class ReservationService {
    private static ReservationService reservationService;
    private Collection <Reservation> reservationList = new HashSet<Reservation>();
    private Map<Customer,ArrayList<Reservation>> customerToReservation = new HashMap<Customer,ArrayList<Reservation>>();
    private Set<IRoom> roomSet = new HashSet<IRoom>();
    private ReservationService(){ };

    public static ReservationService getInstance(){
        if (reservationService == null){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(String roomNumber, Double price, RoomType enumeration){
        roomSet.add(new Room(roomNumber, price, enumeration));

    }

    public IRoom getARoom(String roomId){
        for (IRoom room:roomSet){
            if (roomId.equals(room.getRoomNumber())) {
                return room;
            }
        }
        return null;
    }

    public Collection getAllRooms(){
        return roomSet;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){

        Reservation newReservation = null;

        Collection<IRoom> available_rooms = findRooms(checkInDate, checkOutDate);
        if(!available_rooms.contains(room)){
            System.out.println("Create reservation failed, this room is not available.");
        } else{
            newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
            reservationList.add(newReservation);
            if (customerToReservation.get(customer) != null){
                customerToReservation.get(customer).add(newReservation);

            } else{
                ArrayList<Reservation> NewReservationList = new ArrayList<>();
                NewReservationList.add(newReservation);
                customerToReservation.put(customer, NewReservationList);
            }
        }

        return newReservation;
    }

    private static boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
        return testDate.after(startDate) && testDate.before(endDate);
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> conflicting_rooms = new HashSet<>();
        Collection<IRoom> roomSetCopy = new HashSet<>(roomSet);;

        for (Reservation reservation : reservationList) {
            IRoom room = reservation.getRoom();
            boolean checkInDateWithinRange = isWithinRange(checkInDate, reservation.getCheckInDate(), reservation.getCheckOutDate());
            boolean checkOutDateWithinRange = isWithinRange(checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate());

            if (checkInDateWithinRange && checkOutDateWithinRange){
                conflicting_rooms.add(room);
            }
            else if (checkInDateWithinRange){
                conflicting_rooms.add(room);
            }
            else if(checkOutDateWithinRange){
                conflicting_rooms.add(room);
            }
            else if ((checkInDate.before(reservation.getCheckInDate())) && ( checkOutDate.after(reservation.getCheckOutDate()))){
                conflicting_rooms.add(room);
            }
        }

        roomSetCopy.removeAll(conflicting_rooms);
        return roomSetCopy;
    }

    public Collection <Reservation> getAllReservation(){
        return reservationList;
    }

    public Collection <Reservation> getCustomersReservation(Customer customer){
        return customerToReservation.get(customer);
    }

    public void printAllReservation(){
        for(Reservation reservation: reservationList){
            System.out.println(reservation);
        }
    }

    public void printAllRoom(){
        for(IRoom room: roomSet){
            System.out.println(room);
        }
    }



}
