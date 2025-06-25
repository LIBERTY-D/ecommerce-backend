package com.daniel.apps.ecommerce.app.repository;

import com.daniel.apps.ecommerce.app.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository  extends JpaRepository<FileModel,Long> {
}

