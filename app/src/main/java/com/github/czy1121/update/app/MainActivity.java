package com.github.czy1121.update.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.czy1121.update.app.utils.UpdateAgent;
import com.github.czy1121.update.app.utils.UpdateInfo;
import com.github.czy1121.update.app.utils.UpdateManager;
import com.github.czy1121.update.app.utils.UpdateUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String mCheckUrl = "http://client.waimai.baidu.com/message/updatetag";

    String mUpdateUrl = "http://mobile.ac.qq.com/qqcomic_android.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        UpdateManager.setDebuggable (true);
        UpdateManager.setWifiOnly (false);
        UpdateManager.setUrl (mCheckUrl, "yyb");
        UpdateManager.check (this);
        check (false, true, false, false, true, 998);

        findViewById (R.id.check_update).setOnClickListener (this);
        findViewById (R.id.check_update_cant_ignore).setOnClickListener (this);
        findViewById (R.id.check_update_force).setOnClickListener (this);
        findViewById (R.id.check_update_no_newer).setOnClickListener (this);
        findViewById (R.id.check_update_silent).setOnClickListener (this);
        findViewById (R.id.clean).setOnClickListener (this);
    }

    void check(boolean isManual, final boolean hasUpdate, final boolean isForce, final boolean isSilent, final boolean isIgnorable, final int notifyId) {
        UpdateManager.create (this).setUrl (mCheckUrl).setManual (isManual).setNotifyId (notifyId).setParser (new UpdateAgent.InfoParser () {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                UpdateInfo info = new UpdateInfo ();
                info.hasUpdate = hasUpdate;
                info.updateContent = "• 情；\n• 图片编是魅力之王。";
                info.versionCode = 587;
                info.versionName = "v5.8.7";
                info.url = mUpdateUrl;
                info.md5 = "56cf48f10e4cf6043fbf53bbbc4009e3";
                info.size = 10149314;
                info.isForce = isForce;
                info.isIgnorable = isIgnorable;
                info.isSilent = isSilent;
                return info;
            }
        }).check ();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.check_update:
                check (true, true, false, false, true, 998);
                break;
            case R.id.check_update_cant_ignore:
                check (true, true, false, false, false, 998);
                break;
            case R.id.check_update_force:
                check (true, true, true, false, true, 998);
                break;
            case R.id.check_update_no_newer:
                check (true, false, false, false, true, 998);
                break;
            case R.id.check_update_silent:
                check (true, true, false, true, true, 998);
                break;
            case R.id.clean:
                UpdateUtil.clean (this);
                Toast.makeText (this, "cleared", Toast.LENGTH_LONG).show ();
                break;
        }
    }
}
