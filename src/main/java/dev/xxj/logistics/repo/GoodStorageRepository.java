package dev.xxj.logistics.repo;

import dev.xxj.logistics.model.Good;
import dev.xxj.logistics.model.GoodStorage;
import dev.xxj.logistics.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * GoodStorageRepository interface to allow CRUD and paging operations on GoodStorage objects.
 * <p>
 * Because {@link GoodStorage} is used as a connection , this repo needs several find methods so that
 * the service layer can call easily.
 * It not only provides methods to find GoodStorage objects by either warehouse ID or good ID, but also
 * provides methods to find GoodStorage objects by both warehouse ID and good ID.
 *
 * @author Frank-Xiao
 * @see JpaRepository
 * @see GoodStorage
 * @see UUID
 */
public interface GoodStorageRepository extends JpaRepository<GoodStorage, UUID> {
    Optional<GoodStorage> findByWarehouse_IdAndGood_Id(UUID wareId, UUID goodId);

    List<GoodStorage> findByGood_Id(UUID id);

    List<GoodStorage> findByWarehouse_Id(UUID id);

    long deleteByWarehouseAndGood(Warehouse warehouse, Good good);


}