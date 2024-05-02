package dev.xxj.logistics.model;

import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link GoodStorage}.
 * <p>
 * This record is used to transfer data between the client and the server.
 * It contains the warehouse id, good id and the amount of the good to be transferred.
 *
 * @author Frank-Xiao
 * @see GoodStorage
 */
public record GoodStorageDto(UUID warehouseId,
                             UUID goodId,
                             @Positive Long amount) implements Serializable {
}