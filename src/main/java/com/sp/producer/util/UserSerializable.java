package com.sp.producer.util;

import java.io.Serializable;
import java.util.UUID;

/**
 * 用户序列化测试类
 *
 *
 */

/**
 *    注：将需要序列化的类实现Serializable接口就可以了，Serializable接口中没有任何方法，可以理解为一个标记，即表明这个类可以序列化。
 * 要实现序列化可以参考SerializableUtils中的方法
 */
public class UserSerializable implements Serializable{

    /**
     *    序列化的时候系统会把当前类的serialVersionUID 写入序列化的文件中，当反序列化的时候系统会去检测文件中的serialVersionUID ，看它是否和
     * 当前类的serialVersionUID 一致，如果一致就说明序列化的类的版本和当前类的版本是相同的，这个时候可以成功反序列化，否则就说明当前类，和序列化
     * 的类相比发生了某些变换，比如成员变量的数量，类型可能发生了改变，这个时候就会抛异常，反序列化失败。
     *
     *    默认情况下，也就是不声明serialVersionUID 属性情况下，系统会按当前类的成员变量计算hash值并赋值给serialVersionUID 。所以，当我们修改、
     * 增加、删除了变量而又没有指定serialVersionUID时，就会出现反序列化失败的情况。也就是说，声明serialVersionUID ，可以很大程度上避免反序列化过程的失败。
     *
     */
    private static final long serialVersionUID=1L;



    private String id;
    private String name;   //用户名字
    private Integer gender;  //用户性别 0-男，1-女
    private Integer age;  //用户年龄

    /**
     * 无参构造器
     */
    public UserSerializable(){}

    /**
     * 有参构造器
     * @param name
     * @param gender
     * @param age
     */
    public UserSerializable(String name, Integer gender, Integer age){
        this.id=buildUUId();
        this.name=name;
        this.gender=gender;
        this.age=age;
    }

    /**
     * 生成id
     * @return
     */
    public String buildUUId(){
        return String.valueOf(UUID.randomUUID());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
