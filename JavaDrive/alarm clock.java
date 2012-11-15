MyAlarmManager mam = new MyAlarmManager(this);
mam.addAlarm(); 

�yMyAlarmManager.java�z
import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAlarmManager {
    Context c;
    AlarmManager am;
    private PendingIntent mAlarmSender;
    
    public MyAlarmManager(Context c){
    this.c = c;
    am = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
    Log.v("MyAlarmManger","����������");
    }
    
    public void addAlarm(/*���͂Ȃɂ��Ȃ���*/){
    mAlarmSender = PendingIntent.getService(c, -1, new Intent(c, MyAlarmService.class), PendingIntent.FLAG_UPDATE_CURRENT);
    // �A���[�����Ԑݒ�
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(System.currentTimeMillis());
    cal.add(Calendar.MINUTE, 1);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Log.v("MyAlarmManager���O",cal.getTimeInMillis()+"ms");
    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), mAlarmSender);
    Log.v("MyAlarmManager���O","�A���[���Z�b�g����");
    }
}

�yMyAlarmService.java�z
Service���p�����Ă܂��B

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyAlarmService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    Log.v("MyAlarmService���O","Create");
    Thread thr = new Thread(null, mTask, "MyAlarmServiceThread");
    thr.start();
    Log.v("MyAlarmService���O","�X���b�h�J�n");
    }

    /**
     * �A���[���T�[�r�X
     */
    Runnable mTask = new Runnable() {
    public void run() {
        // �����ŃA���[���ʒm����O�̏����Ȃ�...

        Intent alarmBroadcast = new Intent();
        alarmBroadcast.setAction("MyAlarmAction");//�Ǝ��̃��b�Z�[�W�𑗐M���܂�
        sendBroadcast(alarmBroadcast);
        Log.v("MyAlarmService���O","�ʒm��ʋN�����b�Z�[�W�𑗂���");
        MyAlarmService.this.stopSelf();//�T�[�r�X���~�߂�
        Log.v("MyAlarmService���O","�T�[�r�X��~");
    }
    };
}

�yMyAlarmNotificationReceiver.java�z
����́A�T�[�r�X���瑗���ė������b�Z�[�W����M���������S���܂��B
����͂����ʒm��ʂ��N�����邾���ł��B 

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAlarmNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    Log.v("���V�[�o���O", "action: " + intent.getAction());
    Intent notification = new Intent(context,
        AlarmNotification.class);
    //�������Ȃ��Ɖ�ʂ��N���ł��܂���
    notification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(notification);
    }

}

�yAlarmNotificationActivity.java�z
���ꂪ���ۂɕ\�������A���[���ʒm��ʂł��B
onStart�ŃA���[�������Đ����Ă��܂��B
onDestroy�Ń��\�[�X��������܂��B

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class AlarmNotification extends Activity {
    private WakeLock wakelock;
    private KeyguardLock keylock;

    MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.alarm);
    Log.v("�ʒm���O", "create");
    // �X���[�v��Ԃ��畜�A����
    wakelock = ((PowerManager) getSystemService(Context.POWER_SERVICE))
        .newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
            | PowerManager.ACQUIRE_CAUSES_WAKEUP
            | PowerManager.ON_AFTER_RELEASE, "disableLock");
    wakelock.acquire();

    // �X�N���[�����b�N����������
    KeyguardManager keyguard = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
    keylock = keyguard.newKeyguardLock("disableLock");
    keylock.disableKeyguard();
    }

    @Override
    public void onStart() {
    super.onStart();
    if (mp == null)
        mp = MediaPlayer.create(this, R.raw.alarm);
    mp.start();
    }

    @Override
    public void onDestroy() {
    super.onDestroy();
    stopAndRelaese();
    }

    private void stopAndRelaese() {
    if (mp != null) {
        mp.stop();
        mp.release();
    }
    }
}

