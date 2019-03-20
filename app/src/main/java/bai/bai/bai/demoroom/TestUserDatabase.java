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
@Database(entities = {TestUser.class}, version = 2, exportSchema = false)//迁移数据库，步骤3，version改为2
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
                .addMigrations(migration1_2)//迁移数据库，步骤2
                .build();
    }

    /**
     * 迁移数据库，步骤1
     */
    private static Migration migration1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            /**
             * 这个地方是报错的一个点
             * 看log对比一下Expected的数据库结构和found数据库结构的区别，并修改SQLite语句进行修改
             * 看一下类型type、域名称name、是否允许为空notNull等是否一致
             */
            database.execSQL("alter table TestUser " + " add column age integer not null default 10");
            database.execSQL("alter table TestUser " + " add column post_code integer");
            database.execSQL("alter table TestUser " + " add column state text");
            database.execSQL("alter table TestUser " + " add column city text");

        }
    };

}
