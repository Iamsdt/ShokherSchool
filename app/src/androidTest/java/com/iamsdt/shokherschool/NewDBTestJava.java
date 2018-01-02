package com.iamsdt.shokherschool;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.iamsdt.shokherschool.database.MyDatabase;
import com.iamsdt.shokherschool.database.dao.AuthorTableDao;
import com.iamsdt.shokherschool.database.dao.MediaTableDao;
import com.iamsdt.shokherschool.database.dao.PostTableDao;
import com.iamsdt.shokherschool.database.table.AuthorTable;
import com.iamsdt.shokherschool.database.table.MediaTable;
import com.iamsdt.shokherschool.database.table.PostTable;
import com.iamsdt.shokherschool.model.PostModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shudipto Trafder on 1/2/2018.
 * at 6:41 PM
 */

@RunWith(AndroidJUnit4.class)
public class NewDBTestJava {

    private Context context = InstrumentationRegistry.getContext();

    //dao
    private PostTableDao postTableDao;
    private MediaTableDao mediaTableDao;
    private AuthorTableDao authorTableDao;

    @Before
    public void setUp() throws Exception {

        MyDatabase myDatabase = Room.inMemoryDatabaseBuilder(context, MyDatabase.class)
                .allowMainThreadQueries().build();

        authorTableDao = myDatabase.getAuthorTableDao();
        postTableDao = myDatabase.getPostTableDao();
        mediaTableDao = myDatabase.getMediaTableDao();

    }

    @Test
    public void insert() throws Exception {
        PostTable post1 = new PostTable(1,"12/15/17",1,"New post",1);
        PostTable post2 = new PostTable(2,"12/15/17",1,"New post",2);


        MediaTable media1 = new MediaTable(1,"profile","thumb","","");
        MediaTable media2 = new MediaTable(2,"profile2","thumb2","","");

        AuthorTable author1 = new AuthorTable("","","","Shudipto","link1","author1",1);
        AuthorTable author2 = new AuthorTable("","","","Trafder","link2","author2",2);

        ArrayList<Long> po = new ArrayList<>();
        ArrayList<Long> me = new ArrayList<>();
        ArrayList<Long> au = new ArrayList<>();


        //post insert
        po.add(postTableDao.insert(post1));
        po.add(postTableDao.insert(post2));

        //media insert
        me.add(mediaTableDao.insert(media1));
        me.add(mediaTableDao.insert(media2));

        //author insert
        au.add(authorTableDao.insert(author1));
        au.add(authorTableDao.insert(author2));


        int p = po.size();
        int m = me.size();
        int a = au.size();


        List<PostModel> postData = postTableDao.getPostData();

        List<PostModel> list = postData;

        if (list != null && !list.isEmpty()){
            for (PostModel post : list) {
                if (post != null){
                    String data = post.getAuthor();
                    String author = post.getAuthor();
                    String media = post.getMediaLink();
                    String title = post.getTitle();
                }
            }
        }

    }
}
