package com.library.model.items;

public class Periodical extends Item {

    private String issueNumber;
    private String publisher;
    private String editorName; // motsvarar "creator"

    public Periodical(String itemId,
                      String type,
                      String itemTitle,
                      String categoryId,
                      String status,
                      String issueNumber,
                      String publisher,
                      String editorName) {

        super(itemId, type, itemTitle, categoryId, status);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
        this.editorName = editorName;
    }

    public Periodical() {
        super(null, null, null, null, null);
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    @Override
    public String getCreator() {
        return editorName;
    }
}