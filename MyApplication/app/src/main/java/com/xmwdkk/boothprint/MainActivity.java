package com.xmwdkk.boothprint;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.xmwdkk.boothprint.base.AppInfo;
import com.xmwdkk.boothprint.bt.BluetoothActivity;
import com.xmwdkk.boothprint.print.PrintMsgEvent;
import com.xmwdkk.boothprint.print.PrintUtil;
import com.xmwdkk.boothprint.print.PrinterMsgType;
import com.xmwdkk.boothprint.util.ToastUtil;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/***
 *  Created by liugruirong on 2017/8/3.
 */
public class MainActivity extends BluetoothActivity implements View.OnClickListener {

     TextView tv_bluename;
     TextView tv_blueadress;
      boolean mBtEnable = true;
    int PERMISSION_REQUEST_COARSE_LOCATION=2;

    WebSocket mWebSocket;
    /**
     * bluetooth adapter
     */
    BluetoothAdapter mAdapter;
    Button  mCsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_bluename =findViewById(R.id.tv_bluename);
        tv_blueadress =findViewById(R.id.tv_blueadress);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        mCsBtn = findViewById(R.id.button_cs);
        mCsBtn.setOnClickListener(this);
        //6.0以上的手机要地理位置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        EventBus.getDefault().register(MainActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        BluetoothController.init(this);


    }


    void connectWs() {
        AsyncHttpClient.getDefaultInstance().websocket("http://www.vtuanba.com:80", "my-protocol", new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, final WebSocket webSocket) {
                mWebSocket = webSocket;
                if (ex != null) {
                    ex.printStackTrace();
                    return;
                }



                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    public void onStringAvailable(String s) {
                        try {
                            if(s != null && s.startsWith("\ufeff")) {
                                s = s.substring(1);
                            }

                            JSONObject jsondata = new JSONObject(s);
                            String insertid = jsondata.getString("insertId");
                            webSocket.send("{\"insertId\":\"" + insertid + "\"}");
                            Intent intent2 = new Intent(getApplicationContext(), BtService.class);
                            intent2.setAction(PrintUtil.ACTION_PRINT);
                            intent2.putExtra("value", s);
                            startService(intent2);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                webSocket.setDataCallback(new DataCallback() {
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList byteBufferList) {
                        System.out.println("I got some bytes!");
                        // note that this data has been read
                        byteBufferList.recycle();
                    }
                });
            }
        });

    }


    @Override
    public void btStatusChanged(Intent intent) {
        super.btStatusChanged(intent);
        BluetoothController.init(this);
        connectWs();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_cs:
                connectWs();
                Toast.makeText(this, "接受消息准备完毕", Toast.LENGTH_LONG);
                break;
            case R.id.button4:
             startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                break;
            case R.id.button5:
                if (TextUtils.isEmpty(AppInfo.btAddress)){
                    ToastUtil.showToast(MainActivity.this,"请连接蓝牙...");
                    startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                }else {
                    if ( mAdapter.getState()==BluetoothAdapter.STATE_OFF ){//蓝牙被关闭时强制打开
                         mAdapter.enable();
                        ToastUtil.showToast(MainActivity.this,"蓝牙被关闭请打开...");
                    }else {
                        ToastUtil.showToast(MainActivity.this,"打印测试...");
                        Intent intent = new Intent(getApplicationContext(), BtService.class);
                        intent.setAction(PrintUtil.ACTION_PRINT_TEST);
                        startService(intent);
                    }

                }
                break;
            case R.id.button6:
                if (TextUtils.isEmpty(AppInfo.btAddress)){
                    ToastUtil.showToast(MainActivity.this,"请连接蓝牙...");
                    startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                }else {
                    ToastUtil.showToast(MainActivity.this,"打印测试...");
                    Intent intent2 = new Intent(getApplicationContext(), BtService.class);
                    intent2.setAction(PrintUtil.ACTION_PRINT_TEST_TWO);
                    startService(intent2);

                }
                case R.id.button:
                if (TextUtils.isEmpty(AppInfo.btAddress)){
                    ToastUtil.showToast(MainActivity.this,"请连接蓝牙...");
                    startActivity(new Intent(MainActivity.this,SearchBluetoothActivity.class));
                }else {
                    ToastUtil.showToast(MainActivity.this,"打印图片...");
                    Intent intent2 = new Intent(getApplicationContext(), BtService.class);
                    intent2.setAction(PrintUtil.ACTION_PRINT_BITMAP);
                    startService(intent2);

                }
//                startActivity(new Intent(MainActivity.this,TextActivity.class));
                break;
        }

    }
    /**
     * handle printer message
     *
     * @param event print msg event
     */
    public void onEventMainThread(PrintMsgEvent event) {
        if (event.type == PrinterMsgType.MESSAGE_TOAST) {
            ToastUtil.showToast(MainActivity.this,event.msg);
        }
        else if(event.type == PrinterMsgType.MESSAGE_STATE_CHANGE) {
            if(event.msg.equals("已连接")) {
                mCsBtn.setEnabled(true);
                //connectWs();
            }
            else {
                if(mWebSocket != null) {
                    mWebSocket.close();
                    mWebSocket = null;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().register(MainActivity.this);
    }
}
