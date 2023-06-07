package com.example.qmread.bean;

public class FileItem {
    private String fileName;
    private String fileSize;
    private String fileDate;
    private String fileType;

    public FileItem(String fileName, String fileSize, String fileDate, String fileType) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileDate = fileDate;
        this.fileType = fileType;
    }

    public String getFileName() {
        if(fileName.endsWith(".txt")){
            return fileName.substring(0,fileName.lastIndexOf("."));
        }
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
