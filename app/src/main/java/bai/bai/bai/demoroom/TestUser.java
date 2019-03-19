package bai.bai.bai.demoroom;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

/**
 * 实体类
 * 默认情况下, Room使用实体类的名字作为数据库表的名字. 如果你想要表拥有一个不同的名字,
 * 设置@Entity注解的tableName属性, 示例代码如下:
 *
 * @Entity(tableName = "users")
 * public class User {}
 * <p>
 * 注意: SQLite中表名是大小写敏感的.
 */
@Entity
public class TestUser {

    /**
     * 必须至少有个主键（可多个），用PrimaryKey来标记
     * autoGenerate:主键是否自动增长，默认为false
     */
    @PrimaryKey(autoGenerate = true)
    private int uid;

    /**
     * 跟tableName属性相似的是, Room使用域的名字作为数据库中列的名字. 如果你想要列有一个不同的名字的话, 给域添加@ColumnInfo注解
     */
    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    /**
     * 不想持久化，用Ignore来标记
     */
    @Ignore
    private Bitmap picture;

    /**
     * 这里的getter和setter方法是必须的
     */
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", picture=" + picture +
                '}';
    }

}
