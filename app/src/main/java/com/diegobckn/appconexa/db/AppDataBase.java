package com.diegobckn.appconexa.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.diegobckn.appconexa.db.dao.AgendaDao;
import com.diegobckn.appconexa.db.entities.Agenda;


@Database(entities = {Agenda.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract AgendaDao agendaDao();

    private static AppDataBase instance;
}
