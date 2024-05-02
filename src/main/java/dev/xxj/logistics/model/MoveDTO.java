package dev.xxj.logistics.model;

import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for moving goods between warehouses.
 * <p>
 * This record is used to transfer data between the client and the server.
 * It contains the starting warehouse id, the destination warehouse id,
 * the good id and the amount of the good to be transferred.
 * <p>
 *
 * @author Frank-Xiao
 * @see GoodStorage
 * @see Warehouse
 * @see Good
 */
public record MoveDTO(UUID fromId,
                      UUID toId,
                      UUID goodId,
                      @PositiveOrZero Long amount) implements Serializable {
}