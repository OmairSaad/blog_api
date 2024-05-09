package com.omair.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.omair.Models.Category;
import com.omair.Payloads.Apiresponse;
import com.omair.Payloads.CategDto;
import com.omair.Repository.CategoryRepository;
import com.omair.exception.ResourceNotFoundException;

@Service
public class CategoryService {
    @Autowired
    private  CategoryRepository categ;
    
    // ModelMapper mapper = new ModelMapper();   // use this to convert from dto to catg if in main Bean of model is not use
    
    @Autowired
    private ModelMapper mapper;
    public List<CategDto> getAllCategory(){
        List<Category> data = categ.findAll();
        return data.stream().map(e-> mapper.map(e, CategDto.class)).collect(Collectors.toList());
    }

    public ResponseEntity<CategDto> createCaeteg(@RequestBody CategDto data){
        Category savedCateg = categ.save(mapper.map(data, Category.class));
     return new ResponseEntity<>(mapper.map(savedCateg, CategDto.class), HttpStatus.CREATED);
    }

    public CategDto getCategory(@PathVariable Long id){
       Category find  = categ.findById(id)
       .orElseThrow(()-> new ResourceNotFoundException("Id not found: "+id));
       return mapper.map(find, CategDto.class);
    }

    public ResponseEntity<Apiresponse> deleteCateg(@PathVariable Long id){
      Category exist = categ.findById(id)
      .orElseThrow(()-> new ResourceNotFoundException("Id not found: "+id));
      categ.delete(exist);
      return new ResponseEntity<Apiresponse>(new Apiresponse("Deleted Success", true), HttpStatus.OK);
    }

    public ResponseEntity<CategDto> updateCateg(@RequestBody CategDto data, @PathVariable Long id){
        Category existCateg = categ.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Id not found:"+id));
        existCateg.setDescription(data.getDescription());
        existCateg.setTitle(data.getTitle());
        existCateg.setType(data.getType());
        categ.save(existCateg);
        return new ResponseEntity<CategDto>(mapper.map(existCateg, CategDto.class), HttpStatus.OK);
    }
}
