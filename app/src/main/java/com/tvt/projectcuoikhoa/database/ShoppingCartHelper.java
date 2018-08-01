package com.tvt.projectcuoikhoa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tvt.projectcuoikhoa.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "ShoppingCart";
    private static final String TABLE_NAME = "Cart";
    private static final String ID = "id";
    private static final String ID_SP = "id_sp";
    private static final String ID_USER = "id_user";
    private static final String IMAGE = "image";
    private static final String PRICE = "price";
    private static final String NAME = "name";
    private static final String SOLUONG = "Soluong";


    public ShoppingCartHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + ID_SP + " INTEGER NOT NULL ," + ID_USER + " TEXT NOT NULL," +
                "" + IMAGE + " TEXT NOT NULL," + PRICE + " INTEGER NOT NULL ," + NAME + " TEXT NOT NULL," + SOLUONG + " INTEGER NOT NULL)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    public void insertCart(Cart cart) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ID_SP, cart.getId_sp());
        values.put(ID_USER, cart.getId_user());
        values.put(IMAGE, cart.getUrl_image());
        values.put(NAME, cart.getName());
        values.put(PRICE, cart.getPrice());
        values.put(SOLUONG, cart.getSoluong());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public void updateColumn(Cart cart) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_SP, cart.getId_sp());
        values.put(ID_USER, cart.getId_user());
        values.put(IMAGE, cart.getUrl_image());
        values.put(NAME, cart.getName());
        values.put(PRICE, cart.getPrice());
        values.put(SOLUONG, cart.getSoluong());

        db.update(TABLE_NAME, values, ID_SP + "=?", new String[]{String.valueOf(cart.getId_sp())});
    }

    public List<Cart> getCartByIDuser(String user_id) {

        List<Cart> arrCart = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_USER + " = '" + user_id + "' ";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            do {
                Cart cart = new Cart();
                cart.setId_user(cursor.getString(cursor.getColumnIndex(ID_USER)));
                cart.setId_sp(cursor.getInt(cursor.getColumnIndex(ID_SP)));
                cart.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                cart.setPrice(cursor.getInt(cursor.getColumnIndex(PRICE)));
                cart.setUrl_image(cursor.getString(cursor.getColumnIndex(IMAGE)));
                cart.setSoluong(cursor.getInt(cursor.getColumnIndex(SOLUONG)));
                arrCart.add(cart);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrCart;
    }

    public void delete(int idSP) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID_SP + "=?", new String[]{String.valueOf(idSP)});
    }
}
