package com.daniel.apps.ecommerce.app.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Lob
    private byte[] content;
    private String contentType;
    private  String fileName;
    private  Long size;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference()
    private Product product;
}
