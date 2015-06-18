package com.nusaraya.dbhelper;

import java.util.ArrayList;

import com.donbaka.reklameuser.model.Model;

import android.database.Cursor;
import android.database.SQLException;

public abstract class AbstractPatternDAO {

	    abstract public void open() throws SQLException;

	    abstract public void close();

	    abstract public long insert(Model model);

	    abstract public Model findById(long id);

	    abstract public ArrayList<Model> getAll();

	    abstract public int update(Model model);

	    abstract public int delete(long id);

	    abstract protected Model cursorToModel(Cursor cursor);
}
