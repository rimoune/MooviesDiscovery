package com.example.android.moviesdiscovery.LocalDB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteRepository {

    private static FavouriteDAO mFavouriteDao;
    private LiveData<List<Favourite>> mAllFavourite;
    private Integer id;

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
            //TODO Logic to check whether the id is already in the db, if so delete it
            if ((mAsyncTaskDao.loadMovieById(params[0].getId()) instanceof Favourite)){
                deleteFavourite(params[0].getId());
                return null;

            }
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public static void deleteFavourite(Integer id)  {
        new deleteFavouriteAsyncTask(mFavouriteDao).execute(id);
    }

    private static class deleteFavouriteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private FavouriteDAO mAsyncTaskDao;

        deleteFavouriteAsyncTask(FavouriteDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


}
