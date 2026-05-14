package com.library.model.items;

public class Book extends Item {
    private String isbn;
    private String genre;
    private String mainAuthorName;
    private String publisherId;
    private boolean isCourseLiterature;
    // getters/setters

   public Book(String itemId,
            String type,
            String itemTitle,
            String categoryId,
            String status,
            String isbn,
            String publisherId,
            String genre,
            String mainAuthorName,
            boolean isCourseLiterature) {

    super(itemId, type, itemTitle, categoryId, status);
    this.isbn = isbn;
    this.publisherId = publisherId;
    this.genre = genre;
    this.mainAuthorName = mainAuthorName;
    this.isCourseLiterature = isCourseLiterature;
}

    public Book() {
        super(null, null, null, null, null);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMainAuthorName() {
        return mainAuthorName;
    }

    public void setMainAuthorName(String mainAuthorName) {
        this.mainAuthorName = mainAuthorName;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public String getCreator() {
        return mainAuthorName;
    }
    // TODO: Never used
    public boolean isCourseLiterature() {
    return isCourseLiterature;
}

    // TODO: Never used
public void setCourseLiterature(boolean courseLiterature) {
    isCourseLiterature = courseLiterature;
}
}