package com.mycompany.myapp.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Itinary entity.
 */
public class ItinaryDTO implements Serializable {

    private Long id;

    private LocalDate startDate;


    private Integer pricePerSeat;


    private Long driverId;

    private String driverLastName;

    private Long carId;
    private Set<PathDTO> pathss = new HashSet<>();

    private Set<PassengerDTO> passengerss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public Integer getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(Integer pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
    public Set<PathDTO> getPathss() {
        return pathss;
    }

    public void setPathss(Set<PathDTO> paths) {
        this.pathss = paths;
    }

    public Set<PassengerDTO> getPassengerss() {
        return passengerss;
    }

    public void setPassengerss(Set<PassengerDTO> passengers) {
        this.passengerss = passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItinaryDTO itinaryDTO = (ItinaryDTO) o;

        if ( ! Objects.equals(id, itinaryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ItinaryDTO{" +
            "id=" + id +
            ", startDate='" + startDate + "'" +
            ", pricePerSeat='" + pricePerSeat + "'" +
            '}';
    }
}
