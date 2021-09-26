package io.openliberty.guides.rest;

public class PathDetails {
    private String path;
    private String description;

    public PathDetails() {
    }

    public PathDetails(String path, String description) {
        this.path = path;
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PathDetails [description=" + description + ", path=" + path + "]";
    }

}
