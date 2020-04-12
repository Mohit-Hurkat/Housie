package com.housie.repository;

import com.housie.models.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface TicketRepository extends MongoRepository<Ticket, String> {

    @Query("{ 'ticketId' : ?0 , 'groupId' : ?1 ,'roomCode' : ?2 }")
    Ticket findTicketByTicketIdAndGroupIdAndRoomCode(String ticketId, String groupId, String roomCode);

    @Query("{ 'groupId' : ?0 ,'roomCode' : ?1 }")
    List<Ticket> findTicketsByGroupIdAndRoomCode(String groupId, String roomCode);

}
