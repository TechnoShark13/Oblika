package licence.projet.oblika.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import licence.projet.oblika.R;

public class LevelSelect extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelselect);


        Button buttonGameActivity = findViewById(R.id.button1);
        buttonGameActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), GameActivity.class);
                activity2Intent.putExtra("levelname","level1");
                startActivity(activity2Intent);
                finish();
            }
        });

        Button buttonGameActivity2 = findViewById(R.id.button2);
        buttonGameActivity2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), GameActivity.class);
                activity2Intent.putExtra("levelname","level2");
                startActivity(activity2Intent);
                finish();
            }
        });

        Button buttonGameActivity3 = findViewById(R.id.button3);
        buttonGameActivity3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), GameActivity.class);
                activity2Intent.putExtra("levelname","level3");
                startActivity(activity2Intent);
                finish();
            }
        });
    }
}
