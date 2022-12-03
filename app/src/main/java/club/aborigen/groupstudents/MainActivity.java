package club.aborigen.groupstudents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import club.aborigen.groupstudents.database.DatabaseClient;
import club.aborigen.groupstudents.database.GroupEntity;
import club.aborigen.groupstudents.database.StudentEntity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initializer dbInit = new Initializer();
        dbInit.start();
    }

    class Initializer extends Thread { //Data provider
        @Override
        public void run() {
            super.run();
            //?? replace with unique fields and insert ignore
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().groupDao().deleteAll();
            // ?? replace with foreign keys and cascading delete
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().studentDao().deleteAll();

            GroupEntity group1 = new GroupEntity();
            group1.name = "Group A";
            long group1Id = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().groupDao().insertGroup(group1);

            StudentEntity student1 = new StudentEntity();
            student1.name = "Arshak";
            student1.groupId = group1Id;
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().studentDao().insertStudent(student1);

            StudentEntity student2 = new StudentEntity();
            student2.name = "Artavazd";
            student2.groupId = group1Id;
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().studentDao().insertStudent(student2);

            GroupEntity group2 = new GroupEntity();
            group2.name = "Group B";
            long group2Id = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().groupDao().insertGroup(group2);

            StudentEntity student3 = new StudentEntity();
            student3.name = "Bagrat";
            student3.groupId = group2Id;
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().studentDao().insertStudent(student3);

            StudentEntity student4 = new StudentEntity();
            student4.name = "Babken";
            student4.groupId = group2Id;
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().studentDao().insertStudent(student4);

            GroupEntity group3 = new GroupEntity();
            group3.name = "Group C";
            long group3Id = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().groupDao().insertGroup(group3);

            StudentEntity student5 = new StudentEntity();
            student5.name = "Colak";
            student5.groupId = group3Id;
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().studentDao().insertStudent(student5);

            StudentEntity student6 = new StudentEntity();
            student6.name = "Catur";
            student6.groupId = group3Id;
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().studentDao().insertStudent(student6);

            StudentEntity student7 = new StudentEntity();
            student7.name = "Mamikon";
            student7.groupId = group3Id;
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().studentDao().insertStudent(student7);

            runOnUiThread(() -> getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.list_container, GroupFragment.newInstance(), "GROUPS")
                    .commit());
        }
    }
}
