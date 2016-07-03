package com.boox.kevinwetzel.boox.model;

/**
 * Created by Kevinn on 02.07.2016.
 */
public class BookshelfVolumeAssociation {

    private String volumeId;
    private int bookshelfId;

    public BookshelfVolumeAssociation(int bookshelfId, String volumeId) {
        this.bookshelfId = bookshelfId;
        this.volumeId = volumeId;
    }
    public BookshelfVolumeAssociation() {

    }

    public int getBookshelfId() {
        return bookshelfId;
    }

    public void setBookshelfId(int bookshelfId) {
        this.bookshelfId = bookshelfId;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }
}
