MyAlarmManager mam = new MyAlarmManager(this);
mam.addAlarm(); 

【MyAlarmManager.java】
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
    Log.v("MyAlarmManger","初期化完了");
    }
    
    public void addAlarm(/*今はなにもなしで*/){
    mAlarmSender = PendingIntent.getService(c, -1, new Intent(c, MyAlarmService.class), PendingIntent.FLAG_UPDATE_CURRENT);
    // アラーム時間設定
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(System.currentTimeMillis());
    cal.add(Calendar.MINUTE, 1);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Log.v("MyAlarmManagerログ",cal.getTimeInMillis()+"ms");
    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), mAlarmSender);
    Log.v("MyAlarmManagerログ","アラームセット完了");
    }
}

【MyAlarmService.java】
Serviceを継承してます。

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
    Log.v("MyAlarmServiceログ","Create");
    Thread thr = new Thread(null, mTask, "MyAlarmServiceThread");
    thr.start();
    Log.v("MyAlarmServiceログ","スレッド開始");
    }

    /**
     * アラームサービス
     */
    Runnable mTask = new Runnable() {
    public void run() {
        // ここでアラーム通知する前の処理など...

        Intent alarmBroadcast = new Intent();
        alarmBroadcast.setAction("MyAlarmAction");//独自のメッセージを送信します
        sendBroadcast(alarmBroadcast);
        Log.v("MyAlarmServiceログ","通知画面起動メッセージを送った");
        MyAlarmService.this.stopSelf();//サービスを止める
        Log.v("MyAlarmServiceログ","サービス停止");
    }
    };
}

【MyAlarmNotificationReceiver.java】
これは、サービスから送られて来たメッセージを受信する役割を担います。
今回はただ通知画面を起動するだけです。 

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAlarmNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    Log.v("レシーバログ", "action: " + intent.getAction());
    Intent notification = new Intent(context,
        AlarmNotification.class);
    //ここがないと画面を起動できません
    notification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(notification);
    }

}

【AlarmNotificationActivity.java】
これが実際に表示されるアラーム通知画面です。
onStartでアラーム音を再生しています。
onDestroyでリソースを解放します。

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
    Log.v("通知ログ", "create");
    // スリープ状態から復帰する
    wakelock = ((PowerManager) getSystemService(Context.POWER_SERVICE))
        .newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
            | PowerManager.ACQUIRE_CAUSES_WAKEUP
            | PowerManager.ON_AFTER_RELEASE, "disableLock");
    wakelock.acquire();

    // スクリーンロックを解除する
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

