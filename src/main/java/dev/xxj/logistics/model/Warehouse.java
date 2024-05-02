package dev.xxj.logistics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

/**
 * Class Warehouse represents a warehouse entity.
 * <p>
 * Each warehouse has a name, location and a maximum amount of goods it can store.
 * The warehouse is identified by a unique id using {@link UUID}, which is generated automatically.
 *
 * @author Frank-Xiao
 */
@Getter
@Setter
@Entity
@Table(name = "warehouse")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "max_amount", nullable = false)
    @Positive
    private Long maxAmount;

    /**
     * Default toString method for Warehouse.
     * It returns the id, name, location and max amount of the warehouse.
     *
     * @return a string representation containing warehouse's id, name, location & max amount
     */
    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", maxAmount=" + maxAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(id, warehouse.id) && Objects.equals(name, warehouse.name) && Objects.equals(location, warehouse.location) && Objects.equals(maxAmount, warehouse.maxAmount);
    }

}