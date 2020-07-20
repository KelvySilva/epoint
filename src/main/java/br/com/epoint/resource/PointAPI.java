package br.com.epoint.resource;

import br.com.epoint.domain.Point;
import br.com.epoint.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "point")
public class PointAPI {

    private PointService service;

    @Autowired
    public PointAPI(PointService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity saveOne(@RequestBody Point point) {
        return ResponseEntity.ok(this.service.saveOne(point));
    }

    @GetMapping
    public ResponseEntity listAll() {
        return ResponseEntity.ok(this.service.listAll());
    }

}
