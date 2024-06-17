package com.tuan.filemanager.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class ControlCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "controlCenter")
    Set<FolderEntity> folderEntity;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "controlCenter")
    Set<FileEntity> fileEntity;

    public ControlCenter() {
    }

    public ControlCenter(Long id, Set<FolderEntity> folderEntity, Set<FileEntity> fileEntity) {
        this.id = id;
        this.folderEntity = folderEntity;
        this.fileEntity = fileEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FolderEntity> getFolderEntity() {
        return folderEntity;
    }

    public void setFolderEntity(Set<FolderEntity> folderEntity) {
        this.folderEntity = folderEntity;
    }

    public Set<FileEntity> getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(Set<FileEntity> fileEntity) {
        this.fileEntity = fileEntity;

    }
}
