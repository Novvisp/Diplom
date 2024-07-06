package com.articles.a;

import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.bibref.model.BibEntry;
import pl.edu.icm.cermine.bibref.model.BibEntryFieldType;
import pl.edu.icm.cermine.exception.AnalysisException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueueProcessor<Long> {
    private final ArticleRepository articleRepository;
    private final ReferenceRepository referenceRepository;

    private SimpleQueue<Long> queue;

    public QueueProcessor(SimpleQueue<Long> queue, ArticleRepository articleRepository, ReferenceRepository referenceRepository) {
        this.queue = queue;
        this.articleRepository = articleRepository;
        this.referenceRepository = referenceRepository;
    }

    public void startProcessing() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
                while (true) {

                    Long id = queue.dequeue();
                    try {
                    Optional<Article> optionalBook = articleRepository.findById((java.lang.Long) id);
                    if (optionalBook.isPresent()) {
                        Article article = optionalBook.get();
                        String fileName = article.getFileName();

                        String filePath = "pdf/" + fileName;

                        ContentExtractor extractor = new ContentExtractor();
                        InputStream inputStream = new FileInputStream(filePath);
                        extractor.setPDF(inputStream);
                        String title = extractor.getMetadata().getTitle();
                        for (var i=0;i<extractor.getMetadata().getAuthors().size();i++) {
                            article.addAuthor(extractor.getMetadata().getAuthors().get(i).getName());
                        }
                        article.setTitle(title);
                        article.setJournal(extractor.getMetadata().getJournal());
                        article.setAbstrakt(extractor.getMetadata().getAbstrakt());
                        article.setTime(LocalDateTime.now());
                        List<Reference> references = new ArrayList<>();
                        for (BibEntry refer:extractor.getReferences()) {
                            Reference reference = new Reference();
                            for (BibEntryFieldType field: refer.getFieldKeys()) {
                                reference.addField(field.toString());
                                reference.addSpec(refer.getAllFieldValues(field).get(0));
                            }
                            referenceRepository.save(reference);
                            references.add(reference);
                        }
                        article.setReferences(references);
                        articleRepository.save(article);

                        System.out.println("Обработан файл с ID: " + id);
                    } else {
                        System.out.println("Запись с ID " + id + " не найдена в базе данных");
                    }
                    }  catch (AnalysisException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        });
        executor.shutdown();
    }
}
