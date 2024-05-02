package dev.xxj.logistics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.UUID;

/**
 * Class GoodStorage represents a good storage entity.
 * <p>
 * Each {@link Good} is stored in a {@link Warehouse} with a certain amount.
 * The amount should be positive or zero. Basically, this class serves as a connection
 * between {@link Good} and {@link Warehouse}.
 *
 * @author Frank-Xiao
 */
@Getter
@Setter
@Entity
@Table(name = "good_storage")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(optional = false)
    @JoinColumn(name = "good_id", nullable = false)
    private Good good;

    @Column(name = "amount", nullable = false)
    @PositiveOrZero
    private Long amount;

    /**
     * Default toString method for GoodStorage.
     * It returns the id, warehouse id, good id and amount of the good storage.
     *
     * @return a string representation containing {@link GoodStorage}'s id, warehouse id, good id & amount
     * @see Good
     * @see Warehouse
     */
    @Override
    public String toString() {
        return "GoodStorage{" +
                "id=" + id +
                ", warehouse=" + warehouse.getId() +
                ", good=" + good.getId() +
                ", amount=" + amount +
                '}';
    }
}