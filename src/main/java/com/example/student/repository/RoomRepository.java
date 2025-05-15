package com.example.student.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Rooms;

public interface RoomRepository extends JpaRepository<Gd_Rooms,Integer> {

@Query(value="select * from gd_rooms where IS_ACTIVE=:isActive", nativeQuery=true)
Page<Gd_Rooms> findByIs_active(Boolean  isActive, Pageable pageable);


}
