package dev.xxj.logistics.controller;

import dev.xxj.logistics.model.*;
import dev.xxj.logistics.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * GoodController  class is a controller class that handles all the requests related to goods.
 * <p>
 * It provides the following functionalities:
 * 1. Get all goods
 * 2. Update a good
 * 3. Add a good
 * 4. Delete a good
 * 5. Get a good by id
 * 6. Get a good by name
 * 7. Get locations of a good
 * 8. Get total amount of a good
 * 9. Move a good
 * 10. Store a good
 * 11. Retrieve a good
 * 12. Get good storages
 *
 * @author Frank-Xiao
 */
@RestController
@RequestMapping("/good")
public class GoodController {
    private final GoodService goodService;

    @Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }


    @GetMapping
    public ResponseEntity<List<Good>> getAllGoods() {
        return ResponseEntity.ok(goodService.getAllGoods());
    }

    @PutMapping
    public ResponseEntity<Good> updateGood(@RequestBody Good good) {
        return ResponseEntity.accepted().body(goodService.updateGood(good));
    }

    @PostMapping
    public ResponseEntity<Good> addGood(@RequestBody Good good) {
        return ResponseEntity.status(HttpStatus.CREATED).body(goodService.addGood(good));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGood(@PathVariable UUID id) {
        goodService.deleteGood(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Good> getGoodById(@PathVariable UUID id) {
        return ResponseEntity.ok(goodService.getGoodById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Good>> getGoodByName(@PathVariable String name) {
        return ResponseEntity.ok(goodService.getGoodByName(name));
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<List<Warehouse>> getLocations(@PathVariable UUID id) {
        return ResponseEntity.ok(goodService.getLocations(id));
    }

    @GetMapping("/amount/{id}")
    public ResponseEntity<Long> getTotalAmount(@PathVariable UUID id) {
        return ResponseEntity.ok(goodService.getTotalAmount(id));
    }

    @PostMapping("/move")
    public ResponseEntity<String> moveGood(@RequestBody MoveDTO moveDTO) {
        boolean result = goodService.moveGood(moveDTO);
        return result ? ResponseEntity.ok("Success") : ResponseEntity.badRequest().body("Failed");
    }

    @PostMapping("/store")
    public ResponseEntity<String> storeGood(@RequestBody GoodStorageDto dto) {
        GoodStorage goodStorage;
        try {
            goodStorage = goodService.storeGood(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success stored to " + goodStorage);
    }

    @PostMapping("/retrieve")
    public ResponseEntity<String> retrieveGood(@RequestBody GoodStorageDto dto) {
        try {
            goodService.retrieveGood(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/storages/{goodId}")
    public ResponseEntity<List<GoodStorage>> getGoodStorages(@PathVariable UUID goodId) {
        return ResponseEntity.ok(goodService.getGoodStorages(goodId));
    }
}
