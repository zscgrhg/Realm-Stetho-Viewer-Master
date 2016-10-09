package com.example.tools;

import android.database.AbstractCursor;
import android.database.Cursor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by THINK on 2016/10/8.
 * Cursor的简单实现，没办法,Cursor这个接口太复杂了,凑合用吧,反正是Debug
 */

public class UselessRealmCursor<T> extends AbstractCursor {
    public static final String GENERATED_INDEX_COLUMN_NAME="cursor_index";
    private final List<T> source;
    private String[] columns;
    List<Method> methods;
    int[] columnType;

    public UselessRealmCursor(List<T> source, Class<T> clazz) {
        super();
        this.source = source;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        methods = new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            if (isGetter(declaredMethod)) {
                methods.add(declaredMethod);
            }
        }
        int size = methods.size();
        columns = new String[size +1];
        columnType = new int[size +1];
        for (int i = 0; i < size; i++) {
            Method method = methods.get(i);
            columns[i] = method.getName().substring(3);
            Class<?> returnType = method.getReturnType();
          if(isInteger(returnType)){
              columnType[i]=Cursor.FIELD_TYPE_INTEGER;
          }else if(isFloat(returnType)){
              columnType[i]=Cursor.FIELD_TYPE_FLOAT;
          }else if(isString(returnType)){
              columnType[i]=Cursor.FIELD_TYPE_STRING;
          }else {
              columnType[i]=Cursor.FIELD_TYPE_NULL;
          }
        }
        columns[size]=GENERATED_INDEX_COLUMN_NAME;
        columnType[size]=Cursor.FIELD_TYPE_INTEGER;
    }

    private boolean isInteger(Class clazz){
        String name = clazz.getName();
        boolean result= name.equals("int")||name.equals("long")||
                name.equals("java.lang.Integer")||name.equals("java.lang.Long");
        return result;
    }
    private boolean isFloat(Class clazz){
        String name = clazz.getName();
        boolean result= name.equals("float")||name.equals("double")||
                name.equals("java.lang.Float")||name.equals("java.lang.Double");
        return result;
    }
    private boolean isString(Class clazz){
        return clazz.getName().equals("java.lang.String");
    }

    private boolean isGetter(Method method) {
        String name = method.getName();
        int modifiers = method.getModifiers();
        Class<?> returnType = method.getReturnType();
        Class<?>[] parameterTypes = method.getParameterTypes();

        boolean result;
        result = Modifier.isPublic(modifiers)
                && (!Modifier.isStatic(modifiers))
                && (!Modifier.isAbstract(modifiers))
                && (name.startsWith("get"))
                && (name.length() > 3)
                && (!"void".equals(returnType.getName()))
                && (parameterTypes == null || parameterTypes.length == 0);

        return result;
    }

    private Object getValue(int column) {

        if(column==columns.length-1){
            return getPosition();
        }
        Method method = methods.get(column);

        int position = getPosition();
        T t = source.get(position);
        try {


            return  method.invoke(t, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return source.size();
    }

    @Override
    public String[] getColumnNames() {
        return columns;
    }

    @Override
    public String getString(int column) {
        Object value = getValue(column);

        return value.toString();
    }

    @Override
    public short getShort(int column) {
        return (short) getLong(column);
    }

    @Override
    public int getInt(int column) {
        return (int) getLong(column);

    }

    @Override
    public long getLong(int column) {
        Object value = getValue(column);

        long l = Long.parseLong(value.toString());
        return l;
    }

    @Override
    public float getFloat(int column) {
        return (float) getDouble(column);
    }

    @Override
    public double getDouble(int column) {
        Object value = getValue(column);

        double l = Double.parseDouble(value.toString());
        return l;
    }

    @Override
    public boolean isNull(int column) {
        Object value = getValue(column);
        return value == null;
    }

    @Override
    public int getType(int column) {
        if(isNull(column)){
            return Cursor.FIELD_TYPE_NULL;
        }
        return columnType[column];
    }

}
