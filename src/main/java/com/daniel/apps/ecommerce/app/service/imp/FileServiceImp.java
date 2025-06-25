package com.daniel.apps.ecommerce.app.service.imp;


import com.daniel.apps.ecommerce.app.exception.FileNotFoundException;
import com.daniel.apps.ecommerce.app.exception.MyIOException;
import com.daniel.apps.ecommerce.app.model.FileModel;
import com.daniel.apps.ecommerce.app.model.Product;
import com.daniel.apps.ecommerce.app.repository.FileRepository;
import com.daniel.apps.ecommerce.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceImp {
    private  final FileRepository fileRepository;
    private  final  ProductServiceImp productServiceImp;
    private  final ProductRepository productRepository;

    @Transactional
    public  FileModel createFile(MultipartFile file, Long productId) {
        try{
            String fileName = file.getOriginalFilename();
            byte[] content = file.getBytes();
            String contentType = file.getContentType();
            Long size =  file.getSize();
            FileModel createFile =  FileModel.builder().build();
            createFile.setContent(content);
            createFile.setSize(size);
            createFile.setFileName(fileName);
            createFile.setContentType(contentType);
            Product product = productServiceImp.findProductById(productId);
            Collection<FileModel> files = product.getFiles() ;
            files.add(createFile);
            product.setFiles(files);
            createFile.setProduct(product);
            productRepository.save(product);
            return createFile;

        }catch (IOException ioException){
            throw new MyIOException(ioException.getMessage());
        }


    }


    public Collection<FileModel>  findAllFiles(){
        return  fileRepository.findAll();
    }
    public FileModel  findFileById(Long id){

        return  findById(id);
    }

    private FileModel findById(Long id){
        Optional<FileModel> fileModel =  fileRepository.findById(id);
        if(fileModel.isEmpty()){
            throw new FileNotFoundException("file not found");
        }
        return  fileModel.get();
    }


}
