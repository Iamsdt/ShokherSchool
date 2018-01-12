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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shudipto Trafder on 12/8/2017.
 * at 12:31 AM
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseTestJava {

    private PostTableDao postDao;

    private Context context = InstrumentationRegistry.getContext();

    public DatabaseTestJava() {

    }

    @Before
    public void setUp() throws Exception {
        postDao = Room.inMemoryDatabaseBuilder(context, MyDatabase.class)
                .allowMainThreadQueries().build().getPostTableDao();
    }

    @Test
    public void dataCheck() throws Exception {

        PostTable post = new PostTable();

        PostTable post2 = new PostTable();

        PostTable post3 = new PostTable();


        postDao.insert(post);
        postDao.insert(post2);
        postDao.insert(post3);


        List<PostTable> listLiveData = postDao.getGetFirst10DataList();

        ArrayList<String> name = new ArrayList<>();

        //change the database design so that
        // comment out this code
        for (PostTable postTable: listLiveData){
            //String date = postTable.getDate();
            //name.add(date);

//            if (postTable.getId() != null){
//                int id = postTable.getId();
//                int id2 = id;
//            }
        }

        System.out.print(name.size());

    }
}
