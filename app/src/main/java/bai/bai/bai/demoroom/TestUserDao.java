package bai.bai.bai.demoroom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * 对user表进行操作的Dao，也是数据库与数据的连接类（必须是个接口或抽象类）
 * 如果DAO是抽象类的话, 它可以随意地拥有一个将RoomDatabase作为唯一参数的构造器. Room在运行时创建DAO的实现
 * 数据访问对象(data access objects, 即DAO)
 */
@Dao
public interface TestUserDao {

    @Query("select * from testuser")
    List<TestUser> getAll();//必须包含一个具有0个参数的抽象方法，并返回带注释的类

    @Query("select * from testuser where firstName like :first and " + "last_name like :last limit 1")
    TestUser findUserByName(String first, String last);

//    @Query("select * from testuser where firstName in :firstNames")
    @Query("select * from testuser where firstName like :firstName")
    List<TestUser> getUserByFirstName(String firstName);

    @Insert
    void insertUser(TestUser... user);

    @Update
    void updateUser(TestUser... user);

    @Delete
    void deleteUser(TestUser... user);

}
