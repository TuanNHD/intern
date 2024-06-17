package com.tuan.filemanager.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class FolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long  id;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "folderEntity")
    Set<FileEntity> fileEntity;
    String folderName;
    int location;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    ControlCenter controlCenter;
    public FolderEntity() {
    }
    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<FolderEntity> subFolders;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id")
    FolderEntity parentFolder;

    public FolderEntity(Long id, Set<FileEntity> fileEntity, String folderName, ControlCenter controlCenter) {
        this.id = id;
        this.fileEntity = fileEntity;
        this.folderName = folderName;
        this.controlCenter = controlCenter;
    }

    public void setParentFolder(FolderEntity parentFolder) {
        this.parentFolder = parentFolder;
    }

    public FolderEntity getParentFolder() {
        return parentFolder;
    }

    public void setSubFolders(Set<FolderEntity> subFolders) {
        this.subFolders = subFolders;
    }

    public Set<FolderEntity> getSubFolders() {
        return subFolders;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Set<FileEntity> getFileEntity() {
        return fileEntity;
    }
    public void setFileEntity(Set<FileEntity> fileEntity) {
        this.fileEntity = fileEntity;
    }
    public String getFolderName() {
        return folderName;
    }
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public ControlCenter getControlCenter() {
        return controlCenter;
    }

    public void setControlCenter(ControlCenter controlCenter) {
        this.controlCenter = controlCenter;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }
}
