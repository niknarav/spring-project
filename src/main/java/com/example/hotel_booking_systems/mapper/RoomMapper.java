package com.example.hotel_booking_systems.mapper;

import com.example.hotel_booking_systems.entity.Room;
import com.example.hotel_booking_systems.model.room.RoomResponse;
import com.example.hotel_booking_systems.model.room.RoomResponsesList;
import com.example.hotel_booking_systems.model.room.UpsertRoomRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    default Room requestToRoom(UpsertRoomRequest request) {
        Room room = new Room();
        room.setDescription(request.getDescription());
        room.setName(request.getName());
        room.setPrice(request.getPrice());
        room.setMaximumNumberOfPeople(request.getMaximumNumberOfPeople());
        room.setNumber(request.getNumber());
        room.setUnavailableDates(request.getUnavailableDates());

        return room;
    }

    RoomResponse roomToResponse(Room room);

    default RoomResponsesList roomListToRoomResponsesList(List<Room> roomList) {
        RoomResponsesList roomResponsesList = new RoomResponsesList();
        roomResponsesList.setRoomResponses(roomList.stream()
                .map(this::roomToResponse)
                .toList());

        return roomResponsesList;
    }
}
