package com.tuan.filemanager.repository;
import com.tuan.filemanager.entity.FileEntity;
import com.tuan.filemanager.entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
List<FileEntity> findByFolderEntity(FolderEntity folderEntity);
@Query("select f from FileEntity f where f.folderEntity is null")
List<FileEntity> getAllFileNotInFolder();


}
