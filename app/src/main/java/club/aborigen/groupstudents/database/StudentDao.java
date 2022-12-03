package club.aborigen.groupstudents.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM students WHERE groupId==:selected")
    List<StudentEntity> getAllFromGroup(long selected);

    @Query("SELECT * FROM students")
    List<StudentEntity> getAll();

    @Insert
    long insertStudent(StudentEntity student);

    @Query("DELETE FROM students")
    void deleteAll();
}
