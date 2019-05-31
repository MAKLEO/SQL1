package manish.khullar.databasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText t1, t2, t3;
    Button b1, b2, b3, b4, b5, b6;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connecttoobject();
        operateingodmood();

        db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,marks VARCHAR);");

    }

    private void operateingodmood() {
        b1.setOnClickListener(this);//ADD
        b2.setOnClickListener(this);//DELETE
        b3.setOnClickListener(this);//MODIFY
        b4.setOnClickListener(this);//VIEW
        b5.setOnClickListener(this);//VIEW ALL
        b6.setOnClickListener(this);//ABOUT
    }

    private void connecttoobject() {
        t1 = findViewById(R.id.ename);
        t2 = findViewById(R.id.emarks);
        t3 = findViewById(R.id.erollno);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Toast.makeText(this, "ADD", Toast.LENGTH_SHORT);
                if(t3.getText().toString().trim().length()==0||
                        t1.getText().toString().trim().length()==0||
                        t2.getText().toString().trim().length()==0)
                {
                    Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                    showMessage("Error", "Please enter all values");
                    return;
                }
                db.execSQL("INSERT INTO student VALUES('"+t3.getText()+"','"+t1.getText()+
                        "','"+t2.getText()+"');");
                showMessage("Success", "Record added");
                clearText();
                break;
            case R.id.button2:
                Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT);
                if(t3.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+t3.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM student WHERE rollno='"+t3.getText()+"'");
                    showMessage("Success", "Record Deleted");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
                break;
            case R.id.button3:
                Toast.makeText(this, "MODIFY", Toast.LENGTH_SHORT);
                if(t3.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c2=db.rawQuery("SELECT * FROM student WHERE rollno='"+t3.getText()+"'", null);
                if(c2.moveToFirst())
                {
                    db.execSQL("UPDATE student SET name='"+t1.getText()+"',marks='"+t2.getText()+
                            "' WHERE rollno='"+t3.getText()+"'");
                    showMessage("Success", "Record Modified");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
                break;
            case R.id.button4:
                Toast.makeText(this, "VIEW", Toast.LENGTH_SHORT);
                if(t3.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c3=db.rawQuery("SELECT * FROM student WHERE rollno='"+t3.getText()+"'", null);
                if(c3.moveToFirst())
                {
                    t1.setText(c3.getString(1));
                    t2.setText(c3.getString(2));
                }
                else {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }
                break;
            case R.id.button5:
                Toast.makeText(this, "VIEWALL", Toast.LENGTH_SHORT);
                Cursor c4=db.rawQuery("SELECT * FROM student", null);
                if(c4.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c4.moveToNext())
                {
                    buffer.append("Rollno: "+c4.getString(0)+"\n");
                    buffer.append("Name: "+c4.getString(1)+"\n");
                    buffer.append("Marks: "+c4.getString(2)+"\n\n");
                }
                showMessage("Student Details", buffer.toString());
                break;
            case R.id.button6:
                Toast.makeText(this, "ABOUT", Toast.LENGTH_SHORT);
                showMessage("Student record application","Developed by MANISH KHULLAR");

                break;
        }
    }

    private void clearText() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
