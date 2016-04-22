package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Itinary;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Itinary entity.
 */
public interface ItinaryRepository extends JpaRepository<Itinary,Long> {

    @Query("select distinct itinary from Itinary itinary left join fetch itinary.pathss left join fetch itinary.passengerss")
    List<Itinary> findAllWithEagerRelationships();

    @Query("select itinary from Itinary itinary left join fetch itinary.pathss left join fetch itinary.passengerss where itinary.id =:id")
    Itinary findOneWithEagerRelationships(@Param("id") Long id);

}
