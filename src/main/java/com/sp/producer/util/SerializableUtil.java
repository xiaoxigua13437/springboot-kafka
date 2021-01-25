package com.sp.producer.util;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 序列化与反序列化
 * @author yushu.zhao
 * @create 2020-12-16 15:07
 */
public class SerializableUtil {

    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {

        byte[] bytes = null;
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            if (object != null) {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                bytes = baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * 反序列化对象
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {

        Object object = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            if (bytes != null && bytes.length > 0) {
                bais = new ByteArrayInputStream(bytes);
                ois = new ObjectInputStream(bais);
                object = ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                bais.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }


    /**
     * 序列化list
     *
     * @param value
     * @return
     */
    public static byte[] serializeList(List<Object> value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] bytes = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            //方法一：
            for (Object object : value) {
                oos.writeObject(object);
            }
            oos.writeObject(null);
            //方法二：
//            Object[] objects=new Object[value.size()];
//            value.toArray(objects);
//            oos.writeObject(objects);

            oos.close();
            baos.close();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }


    /**
     * 反序列化list
     *
     * @param bytes
     * @return
     */
    public static List<Object> unSerializeList(byte[] bytes) {

        List returnList = new ArrayList();
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            if (bytes != null) {
                bais = new ByteArrayInputStream(bytes);
                ois = new ObjectInputStream(bais);
                //方法一：
                while (true) {
                    Object object = ois.readObject();
                    if (object == null) {
                        break;
                    } else {
                        returnList.add(object);
                    }
                }
                //方法二：
//                Object[] objects= (Object[]) ois.readObject();
//                returnList= Arrays.asList(objects);

                ois.close();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return returnList;
    }

    @Test
    public void test() {
        List list = new ArrayList<>();


        list.add(new UserSerializable("fzy1", 0, 25));
        list.add(new UserSerializable("fzy2", 1, 20));
//        byte[] bytes=serializeList(list);
//        List list1=unSerializeList(bytes);
//        System.out.println(((UserSerializable)list1.get(0)).getName());
        byte[] bytes = serialize(list);
        List list1 = (List) unserialize(bytes);
        System.out.println(((UserSerializable) list1.get(1)).getName());
    }

}