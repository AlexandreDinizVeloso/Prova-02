package DataBase;

import java.util.ArrayList;
import Pessoa.Pessoa;

public class DataBase {
    private ArrayList<Pessoa> db;

    public DataBase(){
        db = new ArrayList<Pessoa>();
    }

    public void addRegister(Pessoa newReg){
        db.add(newReg);
    }

    public void altRegister(int oldReg, Pessoa newReg){
        for (int i=0; i<db.size()-1; i++){
            if (db.get(i).getId() == oldReg){
                db.set(i, newReg);
                return;
            }
        }
    }

    public void remRegister(int id){
        int index = -1;

        for (Pessoa i: db){
            if (i.getId() == id){
                index = db.indexOf(i);
            }
        }

        if (index == -1) return;
        else db.remove(index);
    }

    public Pessoa readSingleRegister(int id){
        for (var i:db){
            if (id == i.getId()){
                return i;
            }
        }

        return null;
    }

    public int getId(String name){
        for (var i: db){
            if (name == i.getName()){
                return i.getId();
            }
        }

        return -1;
    }

    public int getLastId(){
        if (!db.isEmpty()) return db.get(db.size()-1).getId();
        else return -1;
    }

    public Object[][] getTable(){
        Object[][] temp = new Object[db.size()-1][4];

        for (int i=0; i<db.size()-1; i++){
            temp[i][0] = db.get(i).getId();
            temp[i][1] = db.get(i).getName();
            temp[i][2] = db.get(i).getCpf();
            temp[i][3] = db.get(i).getTipo();
        }

        return temp;
    }

    /**
     * @return ArrayList<Pessoa> return the db
     */
    public ArrayList<Pessoa> getDb(){
        return db;
    }

    /**
     * @param db the db to set
     */
    public void setDb(ArrayList<Pessoa> db) {
        this.db = db;
    }

}
