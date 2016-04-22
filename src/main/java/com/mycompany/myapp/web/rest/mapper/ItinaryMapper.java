package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.ItinaryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Itinary and its DTO ItinaryDTO.
 */
@Mapper(componentModel = "spring", uses = {PathMapper.class, PassengerMapper.class, })
public interface ItinaryMapper {

    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "driver.lastName", target = "driverLastName")
    @Mapping(source = "car.id", target = "carId")
    ItinaryDTO itinaryToItinaryDTO(Itinary itinary);

    List<ItinaryDTO> itinariesToItinaryDTOs(List<Itinary> itinaries);

    @Mapping(source = "driverId", target = "driver")
    @Mapping(source = "carId", target = "car")
    Itinary itinaryDTOToItinary(ItinaryDTO itinaryDTO);

    List<Itinary> itinaryDTOsToItinaries(List<ItinaryDTO> itinaryDTOs);

    default Driver driverFromId(Long id) {
        if (id == null) {
            return null;
        }
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }

    default Car carFromId(Long id) {
        if (id == null) {
            return null;
        }
        Car car = new Car();
        car.setId(id);
        return car;
    }

    default Path pathFromId(Long id) {
        if (id == null) {
            return null;
        }
        Path path = new Path();
        path.setId(id);
        return path;
    }

    default Passenger passengerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Passenger passenger = new Passenger();
        passenger.setId(id);
        return passenger;
    }
}
