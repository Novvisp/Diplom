package com.articles.a;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class hellp {

    @GetMapping("/hello")
    public static String main(String[] args) throws AnalysisException, IOException {
        ContentExtractor extractor = new ContentExtractor();
        InputStream inputStream = new FileInputStream("C:\\Users\\Иван\\Desktop\\a.pdf");
        extractor.setPDF(inputStream);
        return extractor.getMetadata().getJournal();
    }
}
