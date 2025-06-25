package com.daniel.apps.ecommerce.app.controller.imp;

import com.daniel.apps.ecommerce.app.http.HttpResponse;
import com.daniel.apps.ecommerce.app.model.FileModel;
import com.daniel.apps.ecommerce.app.service.imp.FileServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class FileController extends BaseController {

    private final FileServiceImp fileService;


    @PostMapping("files/upload")
    public ResponseEntity<HttpResponse<String>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productId") Long productId) throws IOException {

        FileModel fileModel = fileService.createFile(file, productId);

        HttpResponse<String> response = HttpResponse.<String>builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("File uploaded and viewable" + fileModel.getFileName())
                .data(fileModel.getFileName())
                .build();

        return ResponseEntity.ok(response);
    }
    @GetMapping("files")
    public ResponseEntity<HttpResponse<Collection<FileModel>>> getAllFiles() {
        Collection<FileModel> fileModels = fileService.findAllFiles();
        HttpResponse<Collection<FileModel>> response =
                HttpResponse.<Collection<FileModel>>builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("retrieved all files successfully")
                .data(fileModels).build();

        return  ResponseEntity.ok(response);

    }

    @GetMapping("files/view/{fileId}")
    public ResponseEntity<Resource> viewFile(@PathVariable Long fileId) {
        FileModel fileModel = fileService.findFileById(fileId);
        ByteArrayResource resource = new ByteArrayResource(fileModel.getContent());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileModel.getContentType())).body(resource);
    }

}
