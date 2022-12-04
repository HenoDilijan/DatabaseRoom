package club.aborigen.groupstudents.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface GroupDao {
    @Transaction
    @Query("SELECT * FROM groups")
    List<GroupWithStudents> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertGroup(GroupEntity group);

    @Query("DELETE FROM groups")
    void deleteAll();
}
