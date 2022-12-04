package club.aborigen.groupstudents.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "students",
        indices = {@Index(value = {"name"},unique = true)},
        foreignKeys = {@ForeignKey(entity = GroupEntity.class,
                parentColumns = "id",
                childColumns = "groupId",
                onDelete = ForeignKey.CASCADE) }
)
public class StudentEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "groupId")
    public long groupId;
}


