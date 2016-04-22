package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Itinary.
 */
@Entity
@Table(name = "itinary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Itinary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "price_per_seat")
    private Integer pricePerSeat;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Car car;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "itinary_paths",
               joinColumns = @JoinColumn(name="itinarys_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="pathss_id", referencedColumnName="ID"))
    private Set<Path> pathss = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "itinary_passengers",
               joinColumns = @JoinColumn(name="itinarys_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="passengerss_id", referencedColumnName="ID"))
    private Set<Passenger> passengerss = new HashSet<>();

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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Set<Path> getPathss() {
        return pathss;
    }

    public void setPathss(Set<Path> paths) {
        this.pathss = paths;
    }

    public Set<Passenger> getPassengerss() {
        return passengerss;
    }

    public void setPassengerss(Set<Passenger> passengers) {
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
        Itinary itinary = (Itinary) o;
        if(itinary.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, itinary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Itinary{" +
            "id=" + id +
            ", startDate='" + startDate + "'" +
            ", pricePerSeat='" + pricePerSeat + "'" +
            '}';
    }
}
