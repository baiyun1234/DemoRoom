package bai.bai.bai.demoroom;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 继承自RoomDatabase的抽象类
 * entities:指定表名，如果是多个，用逗号隔开
 * version:版本
 * exportSchema:必须添加的属性，不然会有警告
 */
@Database(entities = {TestUser.class}, version = 1, exportSchema = false)
public abstract class TestUserDatabase extends RoomDatabase {

    private static final String DB_NAME = "UserDatabase.db";
    private static volatile TestUserDatabase instance;


    /**
     * 抽象方法获得Dao对象（必须的）
     */
    public abstract TestUserDao getTestUserDao();

    /**
     * 使用单例
     */
    static synchronized TestUserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    /**
     * 创建数据库
     */
    private static TestUserDatabase create(final Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),//防止内存泄漏
                TestUserDatabase.class,
                DB_NAME)
                .build();
    }

}
