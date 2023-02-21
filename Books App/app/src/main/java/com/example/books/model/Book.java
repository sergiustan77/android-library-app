package com.example.books.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Book implements Serializable {

    public Book(String volumeID, String title, String authors, String publisher, String publishedDate, String description, String pageCount, String categories, String imageSmallThumbnail, String imageSmallBookPage, String language, String previewLink, String buyLink, String averageRating) {
        this.volumeID = volumeID;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.categories = categories;
        this.imageSmallThumbnail = imageSmallThumbnail;
        this.imageSmallBookPage = imageSmallBookPage;
        this.language = language;
        this.previewLink = previewLink;
        this.buyLink = buyLink;
        this.averageRating = averageRating;
    }

    public Book() {
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "volume_id")
    private String volumeID;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "authors")
    private String authors;

    @ColumnInfo(name = "publisher")
    private String publisher;

    @ColumnInfo(name = "published_date")
    private String publishedDate;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "page_count")
    private String pageCount;

    @ColumnInfo(name = "categories")
    private String categories;

    @ColumnInfo(name = "image_small_thumbnail")
    private String imageSmallThumbnail;

    @ColumnInfo(name = "image_small_book_page")
    private String imageSmallBookPage;

    @ColumnInfo(name = "language")
    private String language;

    @ColumnInfo(name = "preview_link")
    private String previewLink;

    @ColumnInfo(name = "buy_link")
    private String buyLink;

    @ColumnInfo(name = "average_rating")
    private String averageRating;

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageCount() {
        return pageCount;
    }

    public String getVolumeID() {
        return volumeID;
    }

    public void setVolumeID(String volumeID) {
        this.volumeID = volumeID;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getImageSmallThumbnail() {
        return imageSmallThumbnail;
    }

    public void setImageSmallThumbnail(String imageSmallThumbnail) {
        this.imageSmallThumbnail = imageSmallThumbnail;
    }

    public String getImageSmallBookPage() {
        return imageSmallBookPage;
    }

    public void setImageSmallBookPage(String imageSmallBookPage) {
        this.imageSmallBookPage = imageSmallBookPage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", volumeID='" + volumeID + '\'' +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", description='" + description + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", categories='" + categories + '\'' +
                ", imageSmallThumbnail='" + imageSmallThumbnail + '\'' +
                ", imageSmallBookPage='" + imageSmallBookPage + '\'' +
                ", language='" + language + '\'' +
                ", previewLink='" + previewLink + '\'' +
                ", buyLink='" + buyLink + '\'' +
                '}';
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }


}
