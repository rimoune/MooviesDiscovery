package com.example.android.moviesdiscovery.LocalDB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteRepository {

    private FavouriteDAO mFavouriteDao;
    private LiveData<List<Favourite>> mAllFavourite;

    FavouriteRepository(Application application) {
        FavouriteRoomDatabase db = FavouriteRoomDatabase.getDatabase(application);
        mFavouriteDao = db.favouriteDAO();
        mAllFavourite = mFavouriteDao.getAllFavourite();
    }


    LiveData<List<Favourite>> getAllFavourite() {
        return mAllFavourite;
    }

    public void insert (Favourite favourite) {
        new insertAsyncTask(mFavouriteDao).execute(favourite);
    }

    private static class insertAsyncTask extends AsyncTask<Favourite, Void, Void> {

        private FavouriteDAO mAsyncTaskDao;

        insertAsyncTask(FavouriteDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Favourite... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
