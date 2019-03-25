package bai.bai.bai.demoroom;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

/**
 * 一篇不错的关于room的博客：https://blog.csdn.net/l_o_s/article/details/79346426
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private int mUid = 1;
    private EditText mEtFirstName;
    private EditText mEtLastName;

    private TestUserDao mTestUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtFirstName = findViewById(R.id.et_first);
        mEtLastName = findViewById(R.id.et_last);
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);

        mTestUserDao = TestUserDatabase
                .getInstance(MainActivity.this)
                .getTestUserDao();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_insert:
                insert();
                break;

            case R.id.btn_delete:
                delete();
                break;

            case R.id.btn_update:
                update();
                break;

            case R.id.btn_query:
                query();
                break;
        }
    }

    /**
     * 不能再主线程上对数据库进行操作，不然会报错(增删改查都是)
     * 无法访问主线程上的数据库，因为它可能会长时间锁定UI。
     */
    private void insert() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                TestUser user = new TestUser();
//                user.setUid(mUid);
                user.setFirstName(mEtFirstName.getText().toString());
                user.setLastName(mEtLastName.getText().toString());
                Address address = new Address();
                address.setCity("北京");
                address.setPostCode(3);
                user.setAddress(address);
                mTestUserDao.insertUser(user);//调用DAO里的对数据库的增删改查方法
                mUid++;
            }
        }).start();
    }

    private void delete() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TestUser user = new TestUser();
                user.setUid(mUid);//测试的是只要主键对了就行，其他域不做校验
                user.setFirstName(mEtFirstName.getText().toString());
                mTestUserDao.deleteUser(user);
                mUid++;
            }
        }).start();

    }

    private void update() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TestUser user = new TestUser();
                user.setUid(2);//直接通过主键找到数据并修改
                user.setFirstName(mEtFirstName.getText().toString());
                mTestUserDao.updateUser(user);
            }
        }).start();
    }

    private void query() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //如果是数据库中有多个比配的数据，会返回第一个，如果第一个被删除，则返回第二个
                TestUser user = mTestUserDao.findUserByName("白", "云");
                List<TestUser> users = mTestUserDao.getAll();
                List<TestUser> usersIncludeBai = mTestUserDao.getUserByFirstName("bai");
                Log.d("baibai", "查询结果：" + user);
                Log.d("baibai", "查询所有结果：" + users);
                Log.e("baibai", "长度：" + users.size());
                Log.e("baibai", "包含bai的数据：" + usersIncludeBai);
            }
        }).start();
    }

}
