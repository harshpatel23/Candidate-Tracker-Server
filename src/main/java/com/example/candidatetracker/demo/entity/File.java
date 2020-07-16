package com.example.candidatetracker.demo.entity;

import java.io.Serializable;

public class File implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String fileName;
    private String fileType;
    private byte[] fileContent;

    public File(String fileName, String fileType, byte[] fileContent) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileContent = fileContent;
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

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }
    
}