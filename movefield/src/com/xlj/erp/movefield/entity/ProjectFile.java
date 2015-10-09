package com.xlj.erp.movefield.entity;

import java.io.Serializable;

/**
 * Created by zpy@98ki.com on 7/18/15.
 *
 * "docName": " dg sdfg sr",
 "docType": "Word",
 "docUrl": "http://172.29.137.2:8080/appServer/upload/saleDoc/struts_salesdocument.xml",
 "id": 18,
 "isActive": null,
 "isNew": 0,
 "isShow": null,
 "projectId": 2,
 "sortNum": "7",
 "uploadTime": "2015-07-15 17:47"

 */


public class ProjectFile implements Serializable {
    private String docName;
    private String docType;
    private int id;
    private String isActive;
    private int isNew;
    private boolean isShow;
    private int projectId;
    private String uploadTime;
    private String sortNum;

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    private String docUrl;

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }


    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

}
