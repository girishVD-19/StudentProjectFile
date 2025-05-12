package com.example.student.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Rooms;

public interface RoomRepository extends JpaRepository<Gd_Rooms,Integer> {

	@Query(value = "SELECT r.ROOM_ID, r.CAPACITY, c.CLASS_ID, c.CLASS_NAME, c.STD " +
            "FROM GD_ROOMS r " +
            "JOIN  c ON r.ROOM_ID = c.ROOM_ID " +
            "WHERE r.ROOM_ID = :roomId",
    nativeQuery = true)
List<Object[]> findRoomWithClassDetails(@Param("roomId") Integer roomId);

}
