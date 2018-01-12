package com.iamsdt.shokherschool;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.iamsdt.shokherschool.data.database.MyDatabase;
import com.iamsdt.shokherschool.data.database.dao.PostTableDao;
import com.iamsdt.shokherschool.data.database.table.PostTable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Shudipto Trafder on 1/12/2018.
 * at 5:46 PM
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseUpdateTestJava {

    private PostTableDao postDao;

    private Context context = InstrumentationRegistry.getContext();

    public DatabaseUpdateTestJava() {

    }

    @Before
    public void setUp() throws Exception {
        postDao = Room.inMemoryDatabaseBuilder(context, MyDatabase.class)
                .allowMainThreadQueries().build().getPostTableDao();
    }

    @Test
    public void runUpdate() throws Exception{
        PostTable postTable = new PostTable();
    }

}
