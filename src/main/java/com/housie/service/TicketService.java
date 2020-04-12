package com.housie.service;

import com.housie.models.Room;
import com.housie.models.Ticket;
import com.housie.repository.TicketRepository;
import com.housie.response.RoomResponse;
import com.housie.response.TicketResponse;
import com.housie.response.TicketVerificationResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class TicketService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RoomService roomService;


    private static final Logger logger = LogManager.getLogger(TicketService.class);


    public TicketResponse getTicketById(String ticketId, String groupId, String roomCode) {
        try {
            Ticket ticket = ticketRepository.findTicketByTicketIdAndGroupIdAndRoomCode(ticketId, groupId, roomCode);
            if (ticket != null) {
                return new TicketResponse(ticket, "", 200);
            }
            return new TicketResponse(null, "TICKET_NOT_FOUND", 2004);
        } catch (Exception e) {
            logger.info("Exception while fetching Ticket for ticketId : {} , groupId : {} , roomCode : {}", ticketId, groupId, roomCode, e);
            return new TicketResponse(null, "EXCEPTION " + e.getMessage(), 500);
        }
    }

    private boolean checkForLine(int lineNumber, Ticket ticket, Set<Integer> calledNumber) {
        if (calledNumber != null) {
            switch (lineNumber) {
                case 1:
                    return calledNumber.containsAll(ticket.getTicketFirstLine());
                case 2:
                    return calledNumber.containsAll(ticket.getTicketSecondLine());
                case 3:
                    return calledNumber.containsAll(ticket.getTicketThirdLine());
                default:
                    return false;
            }
        }
        return false;
    }

    private boolean checkForOneSide(List<Integer> oneSideNumbers, Set<Integer> calledNumber) {
        if (calledNumber != null) {
            return calledNumber.containsAll(oneSideNumbers);
        }
        return false;
    }

    private boolean checkOnesAndZeros(List<Integer> numbersHavingOneAndZero, Set<Integer> calledNumber) {
        if (calledNumber != null) {
            return calledNumber.containsAll(numbersHavingOneAndZero);
        }
        return false;
    }

    private boolean checkCorners(Ticket ticket, Set<Integer> calledNumber) {
        List<Integer> cornerNumbers = Arrays.asList(
                ticket.getTicketFirstLine().get(0), ticket.getTicketFirstLine().get(4),
                ticket.getTicketThirdLine().get(0), ticket.getTicketThirdLine().get(4));
        return calledNumber.containsAll(cornerNumbers);
    }

    private boolean checkCenterNumber(Ticket ticket, Set<Integer> calledNumber) {
        return calledNumber.contains(ticket.getTicketSecondLine().get(2));
    }

    private boolean checkFirstAndLast(List<Integer> ticketNumber, Set<Integer> calledNumber) {
        return calledNumber.contains(ticketNumber.get(0)) && calledNumber.contains(ticketNumber.get(ticketNumber.size() - 1));
    }

    private List<Integer> getRemainingNumber(List<Integer> ticketNumber, Set<Integer> calledNumber) {
        ticketNumber.removeAll(calledNumber);
        return ticketNumber;
    }

    public TicketVerificationResponse checkTicket(String ticketId, String groupId, String roomCode) {
        TicketResponse ticketResponse = getTicketById(ticketId, groupId, roomCode);
        if (ticketResponse.getData() == null) {
            return new TicketVerificationResponse(ticketResponse.getData(), ticketResponse.getMessage(), ticketResponse.getStatus());
        }

        //OnesAndZeros
        List<Integer> numbersHavingOnesAndZeros = new ArrayList<>();
        List<Integer> totalTicketNumbers = new ArrayList<>();
        List<Integer> firstHalf = new ArrayList<>();
        List<Integer> secondHalf = new ArrayList<>();

        updateLists(ticketResponse.getData().getTicketFirstLine(), numbersHavingOnesAndZeros, firstHalf, secondHalf,totalTicketNumbers);
        updateLists(ticketResponse.getData().getTicketSecondLine(), numbersHavingOnesAndZeros, firstHalf, secondHalf,totalTicketNumbers);
        updateLists(ticketResponse.getData().getTicketThirdLine(), numbersHavingOnesAndZeros, firstHalf, secondHalf,totalTicketNumbers);

        TicketVerificationResponse ticketVerificationResponse = new TicketVerificationResponse(ticketResponse.getData(), ticketResponse.getMessage(), ticketResponse.getStatus());
        RoomResponse roomResponse = roomService.getHousieRoom(roomCode);
        if(roomResponse.getData()==null){
            return new TicketVerificationResponse(ticketResponse.getData(), roomResponse.getMessage(), roomResponse.getStatus());
        }

        Set<Integer> calledNumbers = roomResponse.getData().getCalledNumbers();
        if(calledNumbers == null){
            return new TicketVerificationResponse(ticketResponse.getData(), roomResponse.getMessage(), roomResponse.getStatus());
        }
        ticketVerificationResponse.setRoom(roomResponse.getData());


        ticketVerificationResponse.setCheckFirstLastAndMiddle(checkFirstAndLast(
                totalTicketNumbers,calledNumbers) &&  checkCenterNumber(ticketResponse.getData(),calledNumbers));


        ticketVerificationResponse.setCheckCornersAndMiddle(checkCorners(
                ticketResponse.getData(),calledNumbers) &&  checkCenterNumber(ticketResponse.getData(),calledNumbers));

        ticketVerificationResponse.setCheckOnesAndZeros(checkOnesAndZeros(numbersHavingOnesAndZeros,calledNumbers));

        ticketVerificationResponse.setCheckFirstHalf(checkForOneSide(firstHalf,calledNumbers));
        ticketVerificationResponse.setCheckSecondHalf(checkForOneSide(secondHalf,calledNumbers));

        ticketVerificationResponse.setRemainingNumber(getRemainingNumber(totalTicketNumbers,calledNumbers));

        ticketVerificationResponse.setCheckFirstLine(checkForLine(1,ticketResponse.getData(),calledNumbers));
        ticketVerificationResponse.setCheckSecondLine(checkForLine(2,ticketResponse.getData(),calledNumbers));
        ticketVerificationResponse.setCheckThirdLine(checkForLine(3,ticketResponse.getData(),calledNumbers));

        ticketVerificationResponse.setCheckFullHouse(ticketVerificationResponse.getRemainingNumber().isEmpty());

        return ticketVerificationResponse;
    }

    private void updateLists(List<Integer> ticketNumbers,
                             List<Integer> numbersHavingOnesAndZeros, List<Integer> firstHalf,
                             List<Integer> secondHalf,
                             List<Integer> totalTicketNumbers) {
        for (Integer number : ticketNumbers) {
            totalTicketNumbers.add(number);
            if (String.valueOf(number).contains("0") || String.valueOf(number).contains("1")) {
                numbersHavingOnesAndZeros.add(number);
            }

            if (number <= 45) {
                firstHalf.add(number);
            } else {
                secondHalf.add(number);
            }
        }

    }

}
