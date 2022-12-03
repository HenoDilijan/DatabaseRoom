package club.aborigen.groupstudents.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {GroupEntity.class, StudentEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract GroupDao groupDao();
    public abstract StudentDao studentDao();
}
