package com.tuan.filemanager.service;

import com.tuan.filemanager.entity.FolderEntity;
import com.tuan.filemanager.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FolderService {
    @Autowired
    FolderRepository folderRepository;

    public void createFolder(FolderEntity folderEntity) {
         folderRepository.save(folderEntity);
    }

    public void deleteFolder(Long folderId) {
        folderRepository.deleteByFolderId(folderId);
    }
    public void updateFolder(FolderEntity folderEntity) {
        folderRepository.save(folderEntity);
    }
    public FolderEntity getFolderById(Long id) {
        return folderRepository.findById(id).orElse(null);
    }

    public List<FolderEntity> getAllFolder() {
        return folderRepository.findAll();
    }
    public FolderEntity findChildFolderByParentFolderId(String folderName,Long id) {
        return folderRepository.findChildFolderByParentFolderId(folderName,id);
    }

    public List<FolderEntity> findAllSingleFolder() {
        return folderRepository.findAllSingleFolder();
    }

    public List<FolderEntity> findByFolderParentId(Long folderParentName) {
        return folderRepository.findByFolderParentId(folderParentName);
    }

    public FolderEntity findSingleFolderByFolderName(String folderName) {
        return folderRepository.findSingleFolderByFolderName(folderName);
    }
}
