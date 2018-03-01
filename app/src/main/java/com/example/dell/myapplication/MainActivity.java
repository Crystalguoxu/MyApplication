package com.example.dell.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.dell.myapplication.R.id.fab;

public class MainActivity extends AppCompatActivity {
     Button mButton;
     TextView mTextView;
     ImageView mImageView;
     Animation mAnimation;
     MediaPlayer mMediaPlayer;
     Button mButton1;
     Button mButton2;
     Button mButton3;
     EditText content;
     MySql mySql;
     SQLiteDatabase dbRead,dbWriter;
     SimpleCursorAdapter adapter;
     ListView mlistView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mButton = (Button) findViewById(R.id.button4 );
        mTextView = (TextView) findViewById(R.id.textView4);
        mImageView = (ImageView) findViewById(R.id.imageView3);
        mAnimation = AnimationUtils.loadAnimation(this,R.anim.zoonin);
        mButton1 = (Button) findViewById(R.id.button);
        mButton2 = (Button) findViewById(R.id.button3);
        mMediaPlayer = new MediaPlayer();
        mButton3 = (Button) findViewById(R.id.sure);
        content = (EditText) findViewById(R.id.editText2);
        mySql = new MySql(this);

        //mlistView = new ListView(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener(){
        @Override

            public void onClick(View view) {
            String answer = "Yes";
            mTextView.setText(answer);
            mImageView.startAnimation(mAnimation);
            toast("开始选择您最喜欢的萌猫吧！");
        }
        } );
        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setBackgroundResource(R.drawable.s4);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.setBackgroundResource(R.drawable.s3);
                }
                return false;
            }
        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp){
                mMediaPlayer.start();
            }
        });
        mButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                playSound();
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mMediaPlayer.stop();
            }
        });

        dbRead = mySql.getReadableDatabase();
        dbWriter = mySql.getWritableDatabase();
        adapter = new SimpleCursorAdapter(this,R.layout.call,null,new String[] {MySql.CONTENT},
        new int[]{R.id.editText2});
           mlistView.setAdapter(adapter);
        // getListView().setOnItemLongClickListener(this);

        ContentValues cv = new ContentValues();
        cv.put("content","我喜欢猫");
        dbWriter.insert(MySql.TABLE_NAME,null,cv);

    }
    public void select(){
        Cursor cursor = dbRead.query(MySql.TABLE_NAME,null,null,null,null,null,null);
        adapter.changeCursor(cursor);
    }

        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sure:
            ContentValues cv = new ContentValues();
            cv.put(MySql.CONTENT,content.getText().toString());
            dbWriter.insert(MySql.TABLE_NAME,null,cv);
                    select();
                    break;
        }
    }
    private void playSound(){

        mMediaPlayer = MediaPlayer.create(this,R.raw.my);
        mMediaPlayer.start();
    }

     public void showdialog(){
         AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
         mBuilder.setTitle("喵喵特别提醒");
         mBuilder.setMessage("你要离开我吗？");
         mBuilder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
             @Override

             public void onClick(DialogInterface dialog,int which){
                 dialog.dismiss();
                 MainActivity.this.finish();

             }
         });
         mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();

             }
         });
         mBuilder.create().show();
     }
     @Override
     public void onBackPressed(){
         showdialog();
     }
     public void toast(String content){
         Toast.makeText(this,content,Toast.LENGTH_LONG).show();
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
