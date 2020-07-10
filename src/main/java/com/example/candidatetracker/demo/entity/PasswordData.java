package com.example.candidatetracker.demo.entity;

public class PasswordData {
    
    private String oldPassword;
    private String newPassword;

    public PasswordData(String oldPassword, String newPassword){
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public PasswordData(){}

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "PasswordData [newPassword=" + newPassword + ", oldPassword=" + oldPassword + "]";
    }

    

}