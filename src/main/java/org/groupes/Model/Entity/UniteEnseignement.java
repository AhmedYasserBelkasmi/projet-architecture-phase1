package org.groupes.Model.Entity;

import java.util.List;

public class UniteEnseignement {
    private int id;
    private String code;
    private String designation;
    private List<Groupe> groupes;

    public UniteEnseignement(int id, String code, String desination) {
        this.id = id;
        this.code = code;
        this.designation = desination;
    }

    public UniteEnseignement(String code, String designation) {
        this.code = code;
        this.designation = designation;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


}
