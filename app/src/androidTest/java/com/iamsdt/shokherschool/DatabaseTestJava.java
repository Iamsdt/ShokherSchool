package com.iamsdt.shokherschool;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.iamsdt.shokherschool.database.MyDatabase;
import com.iamsdt.shokherschool.database.dao.PostDao;
import com.iamsdt.shokherschool.database.table.Post;

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

    private PostDao postDao;

    private Context context = InstrumentationRegistry.getContext();

    public DatabaseTestJava() {

    }

    @Before
    public void setUp() throws Exception {
        postDao = Room.inMemoryDatabaseBuilder(context, MyDatabase.class)
                .allowMainThreadQueries().build().getPostDao();
    }

    @Test
    public void dataCheck() throws Exception {

        Post post = new Post(1,"2017",1,"link","Title","false",
                1,"zer");

        Post post2 = new Post(2,"2017",1,"link","Title","false",
                1,"zer");

        Post post3 = new Post(3,"2017",1,"link","Title","false",
                1,"zer");


        postDao.insert(post);
        postDao.insert(post2);
        postDao.insert(post3);


        List<Post> listLiveData = postDao.getGetAllData2();

        ArrayList<String> name = new ArrayList<>();

        for (Post post1: listLiveData){
            String date = post1.getDate();
            name.add(date);
        }

        System.out.print(name.size());

    }
}
