package com.sample.wmz.bmob;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 具体可以参考慕课网徐医生的教程或者Bmob官方文档
 * 慕课网:http://www.imooc.com/learn/254
 * Bmob:http://docs.bmob.cn/android/developdoc/index.html?menukey=develop_doc&key=develop_android#index_%E6%9F%A5%E8%AF%A2%E6%95%B0%E6%8D%AE
 */
public class MainActivity extends AppCompatActivity {
    EditText mName;
    EditText mSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化BmobSDK, 参数: context,Application ID
        Bmob.initialize(this,"1978128a1bd6f61dcccee68bf5062bbd");
//      //使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this);

        mName = (EditText) findViewById(R.id.name);
        mSex = (EditText) findViewById(R.id.sex);
    }

    /**
     * 保存个人信息
     * @param view
     */
    public void saveProfile(View view) {
        String name = mName.getText().toString();
        String sex = mSex.getText().toString();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(sex)){
            Toast.makeText(MainActivity.this,"请输入非空信息~~",Toast.LENGTH_SHORT).show();
            return;
        }

        PersonProfile profile = new PersonProfile();
        profile.setName(name);
        profile.setSex(sex);
        //BmobObject.save() 保存
        profile.save(MainActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"保存成功!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this,"出错啦"+"\t"+s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询个人信息
     * @param view
     */
    public void queryAllProfile(View view) {
        //获取BmobQuery对象
        BmobQuery<PersonProfile> queryAll = new BmobQuery<>();
        queryAll.findObjects(MainActivity.this, new FindListener<PersonProfile>() {
            @Override
            public void onSuccess(List<PersonProfile> list) {
                if(list.size()<0){
                    Toast.makeText(MainActivity.this,"服务器没有数据! 请先上传个人信息!",Toast.LENGTH_SHORT).show();
                    return;
                }

                //拼接服务器返回的个人信息
                String str = "";
                for (PersonProfile profile:list){
                    str += profile.getName()+" : "+profile.getSex()+"\n";
                }

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Query");
                dialog.setMessage(str);
                dialog.create().show();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this,"哦,No! 查询出错: "+s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 通过姓名查询 (与上面 queryAllProfile 基本一致,只是加了addWhereEqualTo条件)
     * @param view
     */
    public void queryProfileByName(View view) {
        String name = mName.getText().toString();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(MainActivity.this,"请输入要查询的姓名!",Toast.LENGTH_SHORT).show();
            return;
        }
        //获取BmobQuery对象
        BmobQuery<PersonProfile> queryByName = new BmobQuery<>();
        // where name = "yangyangyang"
        queryByName.addWhereEqualTo("name",name);
        queryByName.findObjects(MainActivity.this, new FindListener<PersonProfile>() {
            @Override
            public void onSuccess(List<PersonProfile> list) {
                if(list.size()<0){
                    Toast.makeText(MainActivity.this,"服务器没有数据! 请先上传个人信息!",Toast.LENGTH_SHORT).show();
                    return;
                }

                //拼接服务器返回的个人信息
                String str = "";
                for (PersonProfile profile:list){
                    str += profile.getName()+" : "+profile.getSex()+"\n";
                }

                //判断是否有该用户数据(如果没有,那么str是空)
                if (TextUtils.isEmpty(str)){
                    Toast.makeText(MainActivity.this,"查无是此人~~~ ",Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Query By Name");
                dialog.setMessage(str);
                dialog.create().show();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this,"哦,No! 查询出错: "+s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Bmob消息推送!
     * @param view
     *  1、注册清单中 增加了 service 以及receiver 这些可以去官网直接copy
     *  2、Bmob后端推送 需要设置: 推送-->推送设置(包名要与App包名一致,否则手机端收不到push消息)
     *  -->发送消息(1、代码中发送消息，就是如下的方法 2、服务端去手动发送消息)
     *  3、服务端地址: www.bmob.cn/app/pushmsg/108124
     */
    public void bmobPush(View view) {
        BmobPushManager push = new BmobPushManager(MainActivity.this);
        push.pushMessage("我是一个测试push");
    }
}
