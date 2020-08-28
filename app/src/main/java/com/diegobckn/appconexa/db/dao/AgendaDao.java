package com.diegobckn.appconexa.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.diegobckn.appconexa.db.entities.Agenda;

@Dao
public interface AgendaDao {

    @Query("SELECT COUNT(*) FROM " + Agenda.TABLE_NAME)
    int count();

    @Insert
    long insert(Agenda agenda);
}
