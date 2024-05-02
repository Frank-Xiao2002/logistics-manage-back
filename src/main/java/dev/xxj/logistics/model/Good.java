package dev.xxj.logistics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

/**
 * Class Good represents a good entity.
 * This is the model class for all goods.
 *
 * @author Frank-Xiao
 */
@Getter
@Setter
@Entity
@Table(name = "good")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    /**
     * Default toString method for Good.
     * It returns the id and name of the good.
     *
     * @return a string representation containing good's id & name
     */
    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return Objects.equals(id, good.id) && Objects.equals(name, good.name);
    }

}