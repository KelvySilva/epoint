package br.com.epoint.resource;

import br.com.epoint.domain.PageableResponse;
import br.com.epoint.domain.Point;
import br.com.epoint.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "point")
public class PointAPI {

    private final PointService service;

    @Autowired
    public PointAPI(PointService service) {
        this.service = service;
    }

    @PostMapping(path = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity saveOne(@RequestBody Point point) {
        return ResponseEntity.ok(this.service.saveOne(point));
    }

    @GetMapping(path = "/protected")
    public ResponseEntity listAll() {
        return ResponseEntity.ok(this.service.listAll());
    }

    @GetMapping(path = "/protected/list")
    public ResponseEntity listAll(Pageable pageable) {
        return ResponseEntity.ok(this.service.listAll(pageable));
    }

    @GetMapping(path = "/protected/date")
    public ResponseEntity listByDate(@RequestParam("date") String date) {
        LocalDate data = LocalDate.parse(date);
        return ResponseEntity.ok(this.service.findByDate(data));
    }

    @GetMapping(path = "/protected/dateAndId")
    public ResponseEntity listByFilter(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "date", required = false) String date
    ) {
        LocalDate data = LocalDate.parse(date);
        return ResponseEntity.ok(this.service.findByDateAndEmployeeId(data, id));
    }

    @GetMapping(path = "/protected/listByName")
    public ResponseEntity listByName(@RequestParam("name") String name, Pageable pageable) {
        System.out.println(name);
        Page<Point> allByNameLike = this.service.findByEmployeeNameLike(name, pageable);
        return ResponseEntity.ok(allByNameLike);
    }


}
