package com.omair.Controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omair.Models.Category;
import com.omair.Payloads.Apiresponse;
import com.omair.Payloads.CategDto;
import com.omair.service.CategoryService;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@RestController
@RequiredArgsConstructor
@RequestMapping("/categ")
public class CategoryController {
    private final CategoryService categ;

    @GetMapping("/")
    public List<CategDto> ss(){
        return categ.getAllCategory();
    }

    @PostMapping("/")
    public ResponseEntity<CategDto> creatCateg(@Valid @RequestBody CategDto data){
        return categ.createCaeteg(data);
    }

    @GetMapping("/{id}")
    public CategDto gCategory(@PathVariable Long id){
        return categ.getCategory(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse> delCateg(@PathVariable Long id){
        return categ.deleteCateg(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategDto> updateCateg(@RequestBody CategDto data, @PathVariable Long id){
        return categ.updateCateg(data, id);
    }
}
