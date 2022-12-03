package club.aborigen.groupstudents.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GroupWithStudents {
    @Embedded public GroupEntity group;
    @Relation(
            parentColumn = "id",
            entityColumn = "groupId"
    )
    public List<StudentEntity> students;
}

