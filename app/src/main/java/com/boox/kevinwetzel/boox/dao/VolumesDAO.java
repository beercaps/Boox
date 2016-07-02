package com.boox.kevinwetzel.boox.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.boox.kevinwetzel.boox.databases.BooxDbHelper;
import com.google.api.client.util.DateTime;
import com.google.api.services.books.model.Volume;

/**
 * Created by Kevinn on 26.06.2016.
 */
public class VolumesDAO {

    private static final String TAG = VolumesDAO.class.getSimpleName();

    private SQLiteDatabase database;
    private BooxDbHelper booxDbHelper;

    private String[] columns = {
            BooxDbHelper.C_VOLUME_ID,
            BooxDbHelper.C_VOLUME_ACCESS,
            BooxDbHelper.C_VOLUME_KIND,
            BooxDbHelper.C_VOLUME_RECOMMENDEDINFO_EXPLANATION,
            BooxDbHelper.C_VOLUME_SALEINFO_BUYLINK,
            BooxDbHelper.C_VOLUME_SALEINFO_COUNTRY,
            BooxDbHelper.C_VOLUME_SALEINFO_ISEBOOK,
            BooxDbHelper.C_VOLUME_SEARCHINFO_TEXTSNIPPET,
            BooxDbHelper.C_VOLUME_USERINFO_ENTITLEMENTTYPE,
            BooxDbHelper.C_VOLUME_USERINFO_ISINMYBOOKS,
            BooxDbHelper.C_VOLUME_USERINFO_ISPREORDERED,
            BooxDbHelper.C_VOLUME_USERINFO_ISPURCHASED,
            BooxDbHelper.C_VOLUME_USERINFO_UPDATED,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_CANONICALVOLUMELINK,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_DESCRIPTION,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_CONTENTVERSION,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_DIMENSIONS_HEIGHT,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_DIMENSIONS_THICKNESS,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_DIMENSIONS_WIDTH,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_EXTRALARGE,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_LARGE,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_MEDIUM,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_SMALL,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_SMALLTHUMBNAIL,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_THUMBNAIL,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_LANGUAGE,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_MAINCATEGORY,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_MATURITYRATING,
            BooxDbHelper.C_VOLUME_SALEINFO_ISEBOOK,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_PAGECOUNT,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_PREVIEWLINK,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_PUBLISHEDDATE,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_PUBLISHER,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_RATINGSCOUNT,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_TITLE,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_SUBTITLE,
            BooxDbHelper.C_VOLUME_VOLUMEINFO_AVGRATING,
            };



    public VolumesDAO(Context context) {
        Log.d(TAG, "ProductDataSource erzeugt den DBhelper");
        this.booxDbHelper = new BooxDbHelper(context);
       }

    public void open(){
        Log.d(TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = booxDbHelper.getWritableDatabase();
        Log.d(TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close(){
        booxDbHelper.close();
        Log.d(TAG, "Datenbank mit Hilfe des BooxDbHelpers geschlossen.");
    }



    public Volume createVolume(Volume volume){


        ContentValues values = new ContentValues();
        if (volume.getAccessInfo() != null){values.put(BooxDbHelper.C_VOLUME_ACCESS, volume.getAccessInfo().getAccessViewStatus());}
            values.put(BooxDbHelper.C_VOLUME_KIND, volume.getKind());
        if (volume.getRecommendedInfo() != null){
            values.put(BooxDbHelper.C_VOLUME_RECOMMENDEDINFO_EXPLANATION, volume.getRecommendedInfo().getExplanation());}
        if (volume.getSaleInfo() != null){
            values.put(BooxDbHelper.C_VOLUME_SALEINFO_BUYLINK, volume.getSaleInfo().getBuyLink());
            values.put(BooxDbHelper.C_VOLUME_SALEINFO_COUNTRY, volume.getSaleInfo().getCountry());
            values.put(BooxDbHelper.C_VOLUME_SALEINFO_ISEBOOK, (volume.getSaleInfo().getIsEbook())? 1 : 0);}  //convert true to 1 and false to 0
        if (volume.getSearchInfo() != null){
            values.put(BooxDbHelper.C_VOLUME_SEARCHINFO_TEXTSNIPPET, volume.getSearchInfo().getTextSnippet());}
        if (volume.getUserInfo() != null){
            values.put(BooxDbHelper.C_VOLUME_USERINFO_ENTITLEMENTTYPE, volume.getUserInfo().getEntitlementType());
            if (volume.getUserInfo().getIsInMyBooks() != null){values.put(BooxDbHelper.C_VOLUME_USERINFO_ISINMYBOOKS, ((volume.getUserInfo().getIsInMyBooks()))? 1 : 0);}
            if (volume.getUserInfo().getIsPreordered() != null){values.put(BooxDbHelper.C_VOLUME_USERINFO_ISPREORDERED, ((volume.getUserInfo().getIsPreordered()))? 1 : 0);}
            if (volume.getUserInfo().getIsPurchased() != null){values.put(BooxDbHelper.C_VOLUME_USERINFO_ISPURCHASED, ((volume.getUserInfo().getIsPurchased()))? 1 : 0);}
            values.put(BooxDbHelper.C_VOLUME_USERINFO_UPDATED, volume.getUserInfo().getUpdated().getValue());}
        if (volume.getVolumeInfo() != null){
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_CANONICALVOLUMELINK, volume.getVolumeInfo().getCanonicalVolumeLink());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_DESCRIPTION, volume.getVolumeInfo().getDescription());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_CONTENTVERSION, volume.getVolumeInfo().getContentVersion());
            if (volume.getVolumeInfo().getDimensions() != null){
                values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_DIMENSIONS_HEIGHT, volume.getVolumeInfo().getDimensions().getHeight());
                values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_DIMENSIONS_THICKNESS, volume.getVolumeInfo().getDimensions().getThickness());
                values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_DIMENSIONS_WIDTH, volume.getVolumeInfo().getDimensions().getWidth());}
            if (volume.getVolumeInfo().getImageLinks() != null){
                values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_EXTRALARGE, volume.getVolumeInfo().getImageLinks().getExtraLarge());
                values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_LARGE, volume.getVolumeInfo().getImageLinks().getLarge());
                values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_MEDIUM, volume.getVolumeInfo().getImageLinks().getMedium());
                values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_SMALL, volume.getVolumeInfo().getImageLinks().getSmall());
                values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_SMALLTHUMBNAIL, volume.getVolumeInfo().getImageLinks().getSmallThumbnail());
                values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_THUMBNAIL, volume.getVolumeInfo().getImageLinks().getThumbnail());}
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_LANGUAGE, volume.getVolumeInfo().getLanguage());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_MAINCATEGORY, volume.getVolumeInfo().getMainCategory());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_MATURITYRATING, volume.getVolumeInfo().getMaturityRating());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_PAGECOUNT, volume.getVolumeInfo().getPageCount());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_PREVIEWLINK, volume.getVolumeInfo().getPreviewLink());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_PUBLISHEDDATE, volume.getVolumeInfo().getPublishedDate());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_PUBLISHER, volume.getVolumeInfo().getPublisher());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_RATINGSCOUNT, volume.getVolumeInfo().getRatingsCount());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_TITLE, volume.getVolumeInfo().getTitle());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_SUBTITLE, volume.getVolumeInfo().getSubtitle());
            values.put(BooxDbHelper.C_VOLUME_VOLUMEINFO_AVGRATING, volume.getVolumeInfo().getAverageRating());
        }

/*        values.put(BooxDbHelper.C_VOLUME_DESCRIPTION, volume.getVolumeInfo().getDescription());
        values.put(BooxDbHelper.C_VOLUME_TITLE, volume.getVolumeInfo().getTitle());
        values.put(BooxDbHelper.C_VOLUME_SUBTITLE, volume.getVolumeInfo().getSubtitle());
        values.put(BooxDbHelper.C_VOLUME_RATING, volume.getVolumeInfo().getAverageRating());*/

        if (searchVolume(volume.getId())!= null){
            //modify existing Volume
            database.update(BooxDbHelper.T_VOLUME,
                    values,
                    BooxDbHelper.C_VOLUME_ID + "='" + volume.getId()+"'",
                    null);
            Log.d(TAG, "createVolume: modified: ");
        }else {
            //create new Volume
            values.put(BooxDbHelper.C_VOLUME_ID, volume.getId());
            database.insert(BooxDbHelper.T_VOLUME, null, values);
            Log.d(TAG, "createVolume: created: ");

        }


        Cursor cursor = database.query(BooxDbHelper.T_VOLUME,
                columns, BooxDbHelper.C_VOLUME_ID + "='" + volume.getId()+"'",
                null, null, null, null);
        cursor.moveToFirst();
        Volume vol= cursorToVolume(cursor);

        cursor.close();


        Log.d(TAG, "Volume  "+ vol.toString());
        return vol;

    }

    private Volume cursorToVolume(Cursor cursor){
        int columnIndexID                               = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_ID);
        int columnVolumeAccess                          = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_ACCESS);
        int columnVolumeKind                            = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_KIND);
        int columnVolumeRecommendedInfoExplanation      = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_RECOMMENDEDINFO_EXPLANATION);
        int columnVolumeSaleinfoBuylink                 = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_SALEINFO_BUYLINK);
        int columnVolumeSaleinfoCountry                 = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_SALEINFO_COUNTRY);
        int columnVolumeSaleinfoIsEbook                 = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_SALEINFO_ISEBOOK);
        int columnVolumeSearchinfoTextsnippet           = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_SEARCHINFO_TEXTSNIPPET);
        int columnVolumeUserinfoEntitlementType         = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_USERINFO_ENTITLEMENTTYPE);
        int columnVolumeUserinfoIsInMyEbooks            = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_USERINFO_ISINMYBOOKS);
        int columnVolumeUserinfoIsPreordered            = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_USERINFO_ISPREORDERED);
        int columnVolumeUserinfoIsPurchased             = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_USERINFO_ISPURCHASED);
        int columnVolumeUserinfoUpdated                 = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_USERINFO_ISPURCHASED);
        int columnVolumeinfoCanonicalVolumeLink         = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_CANONICALVOLUMELINK);
        int columnVolumeinfoDescription                 = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_DESCRIPTION);
        int columnVolumeinfoContentVersion              = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_CONTENTVERSION);
        int columnVolumeinfoDimensionsHeight            = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_DIMENSIONS_HEIGHT);
        int columnVolumeinfoDimensionsThickness         = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_DIMENSIONS_THICKNESS);
        int columnVolumeinfoDimensionsWidth             = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_DIMENSIONS_WIDTH);
        int columnVolumeinfoImagelinksExtralarge        = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_EXTRALARGE);
        int columnVolumeinfoImagelinksLarge             = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_LARGE);
        int columnVolumeinfoImagelinksMedium            = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_MEDIUM);
        int columnVolumeinfoImagelinksSmall             = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_SMALL);
        int columnVolumeinfoImagelinksSmallThumbnail    = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_SMALLTHUMBNAIL);
        int columnVolumeinfoImagelinksThumbnail         = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_IMAGELINKS_THUMBNAIL);
        int columnVolumeinfoLanguage                    = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_LANGUAGE);
        int columnVolumeinfoMainCategory                = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_MAINCATEGORY);
        int columnVolumeinfoMaturityRating              = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_MATURITYRATING);
        int columnVolumeinfoPageCount                   = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_PAGECOUNT);
        int columnVolumeinfoPreviewLink                 = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_PREVIEWLINK);
        int columnVolumeinfoPublishedDate               = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_PUBLISHEDDATE);
        int columnVolumeinfoPublisher                   = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_PUBLISHER);
        int columnVolumeinfoRatingsCount                = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_RATINGSCOUNT);
        int columnVolumeinfoTitle                       = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_TITLE);
        int columnVolumeinfoSubtitle                    = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_SUBTITLE);
        int columnVolumeinfoAVGRating                   = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_VOLUMEINFO_AVGRATING);




/*        int columnIndexTitle = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_TITLE);
        int columnIndexSubtitle = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_SUBTITLE);
        int columnIndexAccess = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_ACCESS);
        int columnIndexDescription = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_DESCRIPTION);
        int columnIndexKind = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_KIND);
        int columnIndexRating = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_RATING);*/

        Volume volume = new Volume();
        Volume.SaleInfo saleInfo = new Volume.SaleInfo();
        Volume.UserInfo userInfo = new Volume.UserInfo();
        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();
        Volume.VolumeInfo.Dimensions dimensions = new Volume.VolumeInfo.Dimensions();
        Volume.VolumeInfo.ImageLinks imageLinks = new Volume.VolumeInfo.ImageLinks();


        volume.setId(cursor.getString(columnIndexID));
        volume.setAccessInfo(new Volume.AccessInfo().setAccessViewStatus(cursor.getString(columnVolumeAccess)));
        volume.setKind(cursor.getString(columnVolumeKind));
        volume.setRecommendedInfo(new Volume.RecommendedInfo().setExplanation(cursor.getString(columnVolumeRecommendedInfoExplanation)));

        saleInfo.setBuyLink(cursor.getString(columnVolumeSaleinfoBuylink));
        saleInfo.setCountry(cursor.getString(columnVolumeSaleinfoCountry));
        saleInfo.setIsEbook(cursor.getInt(columnVolumeSaleinfoIsEbook)!= 0);
        volume.setSaleInfo(saleInfo);

        volume.setSearchInfo(new Volume.SearchInfo().setTextSnippet(cursor.getString(columnVolumeSearchinfoTextsnippet)));

        userInfo.setEntitlementType(cursor.getInt(columnVolumeUserinfoEntitlementType));
        userInfo.setIsInMyBooks(cursor.getInt(columnVolumeUserinfoIsInMyEbooks) != 0);
        userInfo.setIsPreordered(cursor.getInt(columnVolumeUserinfoIsPreordered) != 0);
        userInfo.setIsPurchased(cursor.getInt(columnVolumeUserinfoIsPurchased) != 0);
        userInfo.setUpdated(new DateTime(cursor.getLong(columnVolumeUserinfoUpdated)));
        volume.setUserInfo(userInfo);

        volumeInfo.setCanonicalVolumeLink(cursor.getString(columnVolumeinfoCanonicalVolumeLink));
        volumeInfo.setDescription(cursor.getString(columnVolumeinfoDescription));
        volumeInfo.setContentVersion(cursor.getString(columnVolumeinfoContentVersion));
        dimensions.setHeight(cursor.getString(columnVolumeinfoDimensionsHeight));
        dimensions.setThickness(cursor.getString(columnVolumeinfoDimensionsThickness));
        dimensions.setWidth(cursor.getString(columnVolumeinfoDimensionsWidth));
        volumeInfo.setDimensions(dimensions);
        imageLinks.setExtraLarge(cursor.getString(columnVolumeinfoImagelinksExtralarge));
        imageLinks.setLarge(cursor.getString(columnVolumeinfoImagelinksLarge));
        imageLinks.setMedium(cursor.getString(columnVolumeinfoImagelinksMedium));
        imageLinks.setSmall(cursor.getString(columnVolumeinfoImagelinksSmall));
        imageLinks.setSmallThumbnail(cursor.getString(columnVolumeinfoImagelinksSmallThumbnail));
        imageLinks.setThumbnail(cursor.getString(columnVolumeinfoImagelinksThumbnail));
        volumeInfo.setImageLinks(imageLinks);
        volumeInfo.setLanguage(cursor.getString(columnVolumeinfoLanguage));
        volumeInfo.setMainCategory(cursor.getString(columnVolumeinfoMainCategory));
        volumeInfo.setMaturityRating(cursor.getString(columnVolumeinfoMaturityRating));
        volumeInfo.setPageCount(cursor.getInt(columnVolumeinfoPageCount));
        volumeInfo.setPreviewLink(cursor.getString(columnVolumeinfoPreviewLink));
        volumeInfo.setPublishedDate(cursor.getString(columnVolumeinfoPublishedDate));
        volumeInfo.setPublisher(cursor.getString(columnVolumeinfoPublisher));
        volumeInfo.setRatingsCount(cursor.getInt(columnVolumeinfoRatingsCount));
        volumeInfo.setTitle(cursor.getString(columnVolumeinfoTitle));
        volumeInfo.setSubtitle(cursor.getString(columnVolumeinfoSubtitle));
        volumeInfo.setAverageRating(cursor.getDouble(columnVolumeinfoAVGRating));
        volume.setVolumeInfo(volumeInfo);

        /*volume.setVolumeInfo(new Volume.VolumeInfo().setTitle(cursor.getString(columnIndexTitle)));
        volume.setAccessInfo(new Volume.AccessInfo().setAccessViewStatus(cursor.getString(columnIndexAccess)));
        volume.setVolumeInfo(new Volume.VolumeInfo().setDescription(cursor.getString(columnIndexDescription)));
        volume.setVolumeInfo(new Volume.VolumeInfo().setSubtitle(cursor.getString(columnIndexSubtitle)));
        volume.setKind(cursor.getString(columnIndexKind));
        volume.setVolumeInfo(new Volume.VolumeInfo().setAverageRating(cursor.getDouble(columnIndexRating)));*/


        return volume;
    }

    public Volume searchVolume(String volumeID){
        Log.d(TAG, "now searching volume");
        Volume volume = null;
      Cursor cursor = database.query(BooxDbHelper.T_VOLUME,columns, BooxDbHelper.C_VOLUME_ID + "= '"+volumeID+"'",null, null, null, null);
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            volume = cursorToVolume(cursor);
            cursor.close();
            Log.d(TAG, "searchVolume ID"+ volume.getId()+ "Inhalt "+volume.toString());
        }
        return volume;

    }

    //TODO getAllVolumes
//    public List<Bookshelf>getAllBookshelves(){
//        List<Bookshelf> bookshelfList = new ArrayList<>();
//        Cursor cursor = database.query(BooxDbHelper.T_BOOKSHELVES,
//                columns, null, null, null, null, null );
//        cursor.moveToFirst();
//        Bookshelf bookshelf;
//
//        while (!cursor.isAfterLast()){
//            bookshelf = cursorToVolume(cursor);
//            bookshelfList.add(bookshelf);
//            Log.d(TAG, "getAllBookshelves ID: "+bookshelf.getId()+ " Inhalt: "+bookshelf.toString());
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return bookshelfList;
//    }
}
