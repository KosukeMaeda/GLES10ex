package jp.ac.titech.itpro.sdl.gles10ex;

import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private final static String TAG = "MainActivity";

    private GLSurfaceView glView;
    private SimpleRenderer renderer;

    private Cube cube;
    private Pyramid pyramid;

    SeekBar seekBarX;
    SeekBar seekBarY;
    SeekBar seekBarZ;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        glView = findViewById(R.id.gl_view);
        seekBarX = findViewById(R.id.seekbar_x);
        seekBarY = findViewById(R.id.seekbar_y);
        seekBarZ = findViewById(R.id.seekbar_z);
        seekBarX.setMax(360);
        seekBarY.setMax(360);
        seekBarZ.setMax(360);
        seekBarX.setOnSeekBarChangeListener(this);
        seekBarY.setOnSeekBarChangeListener(this);
        seekBarZ.setOnSeekBarChangeListener(this);

        renderer = new SimpleRenderer();
        cube = new Cube();
        pyramid = new Pyramid();
        renderer.setObj(cube, 0, 0, 0);
        glView.setRenderer(renderer);

        Switch sw = findViewById(R.id.switch1);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new IncreaseTask(), 0, 30);
                }else{
                    if(timer != null) timer.cancel();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        glView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        glView.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
        case R.id.item_cube:
            renderer.setObj(cube, 0, 0, 0);
            break;
        case R.id.item_pyramid:
            renderer.setObj(pyramid, 0, 0, 0);
            break;
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
        case R.id.seekbar_x:
            renderer.rotateObjX(progress);
            break;
        case R.id.seekbar_y:
            renderer.rotateObjY(progress);
            break;
        case R.id.seekbar_z:
            renderer.rotateObjZ(progress);
            break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private class IncreaseTask extends TimerTask{
        @Override
        public void run(){
            seekBarX.setProgress((seekBarX.getProgress() + 1) % 360);
            seekBarY.setProgress((seekBarY.getProgress() + 2) % 360);
            seekBarZ.setProgress((seekBarZ.getProgress() + 3) % 360);
        }
    }
}
