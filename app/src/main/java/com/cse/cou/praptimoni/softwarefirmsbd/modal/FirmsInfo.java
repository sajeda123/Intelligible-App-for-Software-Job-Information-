package com.cse.cou.praptimoni.softwarefirmsbd.modal;

public class FirmsInfo {
    String id;
    String firm_name;
    String size;
    String requirment;

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    String starting_salary;
    String examsLanguage;
    String hiring_procedure;
    String website;
    String location;
    String image_link;

    public FirmsInfo(String id, String firm_name, String size, String requirment, String starting_salary, String examsLanguage, String hiring_procedure, String website, String location, String image_link) {
        this.id = id;
        this.firm_name = firm_name;
        this.size = size;
        this.requirment = requirment;
        this.starting_salary = starting_salary;
        this.examsLanguage = examsLanguage;
        this.hiring_procedure = hiring_procedure;
        this.website = website;
        this.location = location;
        this.image_link=image_link;
    }




    public FirmsInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirm_name() {
        return firm_name;
    }

    public void setFirm_name(String firm_name) {
        this.firm_name = firm_name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExamsLanguage() {
        return examsLanguage;
    }

    public void setExamsLanguage(String examsLanguage) {
        this.examsLanguage = examsLanguage;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRequirment() {
        return requirment;
    }

    public void setRequirment(String requirment) {
        this.requirment = requirment;
    }

    public String getStarting_salary() {
        return starting_salary;
    }

    public void setStarting_salary(String starting_salary) {
        this.starting_salary = starting_salary;
    }

    public String getHiring_procedure() {
        return hiring_procedure;
    }

    public void setHiring_procedure(String hiring_procedure) {
        this.hiring_procedure = hiring_procedure;
    }

}
