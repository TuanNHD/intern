package com.tuan.filemanager.service;

import com.tuan.filemanager.entity.FileEntity;
import com.tuan.filemanager.entity.FolderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tuan.filemanager.repository.FileRepository;

import java.util.List;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;
    public void saveFile(FileEntity fileEntity) {
        fileRepository.save(fileEntity);
    }
    public List<FileEntity> getAllFile() {
        return fileRepository.findAll();
    }

    public FileEntity getFile(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    public void deleteFile(FileEntity fileEntity) {
        fileRepository.delete(fileEntity);
    }
    public List<FileEntity> findByFolderEntity(FolderEntity folderEntity) {
        return fileRepository.findByFolderEntity(folderEntity);
    }

    public List<FileEntity> getAllFileNotInFolder() {
        return fileRepository.getAllFileNotInFolder();
    }
}
