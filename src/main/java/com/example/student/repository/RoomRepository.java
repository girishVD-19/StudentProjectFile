package com.example.student.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.DTO.RoomDTO;
import com.example.student.entity.Gd_Rooms;

public interface RoomRepository extends JpaRepository<Gd_Rooms,Integer> {
//	 @Query(value = """
//		        SELECT r.room_id AS roomId, r.capacity, c.class_id AS classId, c.class_name AS className
//		        FROM gd_rooms r
//		        LEFT JOIN gd_class c ON r.room_id = c.room_id
//		        WHERE r.room_id = :roomId
//		    """, nativeQuery = true)
//		    List<RoomDTO> findRoomWithClasses(@Param("roomId") Integer roomId);

	@Query(value = "SELECT r.room_id, r.capacity, c.class_id, c.class_name "
            + "FROM Gd_Rooms r "
            + "LEFT JOIN Gd_Class c ON c.room_id = r.room_id "
            + "WHERE r.room_id = :roomId", nativeQuery = true)
List<Object[]> findRoomWithClasses(@Param("roomId") Integer roomId);

}
