package com.articles.a;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class asddd {

    @GetMapping("/ddd")
    public String fun(){
        System.out.println("alert");
        return ("hi");
    }
}
