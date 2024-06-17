package com.tuan.filemanager.repository;

import com.tuan.filemanager.entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
    @Query("select f from FolderEntity f where f.folderName = :folderName and f.parentFolder.id = :folderParentId")
    FolderEntity findChildFolderByParentFolderId(@Param("folderName") String folderName,
                                              @Param("folderParentId") Long folderParentId);
    @Query("select f from FolderEntity f where f.folderName = :folderName and f.parentFolder is null")
    FolderEntity findByFolderName(@Param("folderName") String folderName);
    @Query("select f from FolderEntity f where f.parentFolder is null")
    List<FolderEntity> findAllSingleFolder();
    @Query("select f from FolderEntity f where f.parentFolder is null and f.folderName = :folderName")
    FolderEntity findSingleFolderByFolderName(@Param("folderName") String folderName);
    @Query("select f from FolderEntity f where f.parentFolder.id = :folderParentId")
    List<FolderEntity> findByFolderParentId(@Param("folderParentId") Long folderParentId);
}