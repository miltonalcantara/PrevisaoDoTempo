package br.com.miltonalcantara.previsaotempo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import br.com.miltonalcantara.previsaotempo.modelo.Cidade;

/**
 * Created by Milton Alcântara on 02/06/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String BANCODEDADOS = "mydb";
    private final static String TABELA = "cidades";
    private final static String NOME = "nome";
    private final static String LATITUDE = "latitude";
    private final static String LONGITUDE = "longitude";
    private final static String DESCRICAO = "descricao";
    private final static String TEMPERATURA = "temperatura";
    private final static String UMIDADE = "umidade";
    private final static String PRESSAO = "pressaoDoAr";
    private final static String ICONE = "icone";
    private final static String DATA_HORA = "data_hora";

    public DBHelper(Context context) {
        super(context, BANCODEDADOS, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABELA + "(id integer primary key, " + NOME + " text," +
                LATITUDE + " text," +
                LONGITUDE + " text," +
                DESCRICAO + " text," +
                TEMPERATURA + " text," +
                UMIDADE + " text," +
                PRESSAO + " text," +
                ICONE + " text," +
                DATA_HORA + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table " + TABELA + " add column " + LATITUDE + " text" +
                " add column " + LONGITUDE + " text +" +
                " add column " + DESCRICAO + " text" +
                " add column " + TEMPERATURA + " text" +
                " add column " + UMIDADE + " text" +
                " add column " + PRESSAO + " text" +
                " add column " + ICONE + " text" +
                " add column " + DATA_HORA + " text");
    }

    public boolean inserirCidade(String nome, String latitude, String longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(NOME, nome); //inserir aqui outras colunas
        content.put(LATITUDE, latitude);
        content.put(LONGITUDE, longitude);
        db.insert(TABELA, null, content);
        return true;
    }

    public ArrayList<Cidade> getTodasCidades() {
        ArrayList<Cidade> myArray = new ArrayList<Cidade>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABELA, null);
        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            Cidade c = new Cidade();
            c.setId(cur.getInt(cur.getColumnIndex("id")));
            c.setNome(cur.getString(cur.getColumnIndex(NOME)));
            c.setLatitude(cur.getString(cur.getColumnIndex(LATITUDE)));
            c.setLongitude(cur.getString(cur.getColumnIndex(LONGITUDE)));
            c.setDescricao(cur.getString(cur.getColumnIndex(DESCRICAO)));
            c.setTemperaturaAtual(cur.getString(cur.getColumnIndex(TEMPERATURA)));
            c.setUmidadeDoAr(cur.getString(cur.getColumnIndex(UMIDADE)));
            c.setPressaoDoAr(cur.getString(cur.getColumnIndex(PRESSAO)));
            c.setIconeDoTempo(cur.getString(cur.getColumnIndex(ICONE)));
            c.setDataEhora(cur.getString(cur.getColumnIndex(DATA_HORA)));
            myArray.add(c);
            cur.moveToNext();
        }
        return myArray;
    }

    public boolean atualizarCidade(int id, String nome, String latitude, String longitude) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put(NOME, nome);
        contentValues.put(LATITUDE, latitude);
        contentValues.put(LONGITUDE, longitude);

        db.update(TABELA, contentValues, " id = ?", new String[]{String.valueOf(id)});

        return true;
    }
}
