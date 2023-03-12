package com.originals.meraqi;

public class complaintDetails {
    private String name;
    private String description;
    private String category;
    private String senders_email;
    private String accused_email;
    private boolean expanded;
    public complaintDetails(String name,String category,String description){
        this.name=name;
        this.description=description;
        this.category=category;
        expanded=false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
