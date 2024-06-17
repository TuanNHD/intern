package com.tuan.filemanager.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    private LocalDateTime uploadDate = LocalDateTime.now();
    @Lob
    private byte[] data;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    FolderEntity folderEntity;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    ControlCenter controlCenter;
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setFolderEntity(FolderEntity folderEntity) {
        this.folderEntity = folderEntity;
    }

    public FolderEntity getFolderEntity() {
        return folderEntity;
    }
}
