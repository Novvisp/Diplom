package com.articles.a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class FileController {
    private final ArticleRepository articleRepository;
    private final ReferenceRepository referenceRepository;

    SimpleQueue<Long> queue;
    QueueProcessor<Long> queueProcessor;
    @Autowired
    public FileController(ArticleRepository articleRepository, ReferenceRepository referenceRepository) {
        this.articleRepository = articleRepository;
        this.referenceRepository = referenceRepository;
        queue = new SimpleQueue<>();
        queueProcessor = new QueueProcessor<>(queue, articleRepository, this.referenceRepository);
        queueProcessor.startProcessing();
    }

    // Укажите путь к директории, где будут сохраняться файлы

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile[] files) {
        try {
            for (MultipartFile file : files) {
                String fileName = saveFile(file);
                Article newArticle = new Article();
                newArticle.setFileName(fileName);
                // Сохраняем запись в базе данных
                Article savedArticle = articleRepository.save(newArticle);
                Long id = savedArticle.getId();
                queue.enqueue(id);
            }

            return ResponseEntity.ok("True");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка при обработке файла: " + e.getMessage());
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        // Путь, где будет сохранен файл на сервере
        String uploadPath = "pdf/";

        // Переводим название файла в уникальное имя для избежания конфликтов
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Создаем файл на сервере и сохраняем в него содержимое
        Path filePath = Paths.get(uploadPath + uniqueFileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return uniqueFileName;
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Article>> getAllBooks() {
        List<Article> articles = (List<Article>) articleRepository.findAll();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Article> getBookById(@PathVariable Long id) {
        Optional<Article> optionalBook = articleRepository.findById(id);
        return optionalBook.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
        // Путь к файлу на сервере
        String fileName = articleRepository.findById(id).get().getFileName();
        String filePath = "pdf/" + fileName;

        // Считываем содержимое файла в массив байтов
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF); // Установите соответствующий MediaType

        // Устанавливаем заголовок с именем файла для скачивания
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
    @GetMapping("/queueSize")
    public int getQueueSize() {

        return queue.size(); // Возвращаем размер очереди
    }
}