package bai.bai.bai.demoroom;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

/**
 * 实体类
 * 默认情况下, Room使用实体类的名字作为数据库表的名字. 如果你想要表拥有一个不同的名字,
 * ===================================================================================
 * 设置@Entity注解的tableName属性,SQLite中表名是大小写敏感的. 示例代码如下:
 * --@Entity(tableName = "users")
 * public class User {}
 * ===================================================================================
 * 联合主键这样表示，注意是primaryKeys，比单主键的注解多个s
 * --@Entity(primaryKeys = {"firstName", "lastName"})
 * ===================================================================================
 * 某个字段或者几个字段必须是唯一的。你可以通过把@Index注解的unique属性设置为true来实现唯一性。
 * 下面的代码防止了一个表中的两行数据出现firstName和lastName字段的值相同
 * --@Entity(indices = {@Index(value = {"firstName", "last_name"}, unique = true)})
 */

//
@Entity
public class TestUser {

    /**
     * 必须至少有个主键（可多个），用PrimaryKey来标记
     * autoGenerate:主键是否自动增长，默认为false
     */
    @PrimaryKey(autoGenerate = true)
    private int uid;

    /**
     * 跟tableName属性相似的是, Room使用域的名字作为数据库中列的名字.
     * 如果你想要列有一个不同的名字的话, 给域添加@ColumnInfo注解的name属性
     * 如果不添加该name属性，该表格里的域名就是变量名
     */
//    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    /**
     * 不想持久化，也就是忽略掉那些不需要保存到表中的字段，用Ignore来标记
     */
    @Ignore
    private Bitmap picture;

    /**
     * 自定义类型字段，用Embedded注解来标记
     * 此时表里没有address这个域，而是uid，firstName，last_name，state，city，post_code几个域
     * --那么如果有个自定义类型的坐标类，包含经度longitude、纬度latitude
     * --而且用@Embedded标记的有两个都用到这个自定义类型，比如中国坐标，英国坐标
     * 此时如果只标记@Embedded的话就相当于有两个经度，两个纬度，这是表格所不允许的，对此Embedded有个prefix属性来解决这个问题
     * 可用@Embedded(prefix = "CN_")来表示中国坐标，用@Embedded(prefix = "UK_")来表示英国
     * 这样表格里的域名就是CN_longitude,CN_latitude,UK_longitude,UK_latitude
     */
    @Embedded
    public Address address;

    //region 这里的getter和setter方法是必须的
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    //endregion

    @Override
    public String toString() {
        return "TestUser{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", picture=" + picture +
                ", address=" + address +
                '}';
    }
}

/**
 * 自定义类
 */
class Address {
    public String state;
    public String city;

    @ColumnInfo(name = "post_code")
    public int postCode;
}
