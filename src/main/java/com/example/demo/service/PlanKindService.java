package com.example.demo.service;

import com.example.demo.data.PlanKind;
import com.example.demo.mapper.PlanKindMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanKindService {

    @Autowired
    PlanKindMapper planKindMapper ;
    
    public  List<PlanKind> listPalnKind (){
        
            return planKindMapper.listPalnKind();
      
    }
    
}
