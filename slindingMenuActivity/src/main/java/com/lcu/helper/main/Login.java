package com.lcu.helper.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.demo.Conf;
import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaUser;
import com.baidu.frontia.api.FrontiaAuthorization;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaAuthorizationListener.AuthorizationListener;
import com.baidu.frontia.api.FrontiaAuthorizationListener.UserInfoListener;
import com.jeremyfeinstein.slidingmenu.example.R;
import com.oiangie.lcuhelper.JavaTool.CallWebservice;
import com.oiangie.lcuhelper.JavaTool.LocationApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	private EditText account,password;
	private Button login,register,otherID;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	private int value = 0;
	private Button menuButton1,menuButton2,menuButton3,menuButton4;
	private FrontiaAuthorization mAuthorization;
	private String UserName = "111";
	private String Userurl = "111";
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		Frontia.init(this.getApplicationContext(), Conf.APIKEY);
		mAuthorization = Frontia.getAuthorization();

		
		account = (EditText) findViewById(R.id.account);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);
		otherID = (Button) findViewById(R.id.otherID);
		
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				startActivity(new Intent(Login.this,Register_User.class));
			}
		});
		
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				doThread();
			}
		});
		
		
		otherID.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				
				//�ٶȵ�������½
				View view = getLayoutInflater().inflate(R.layout.login_chose, null);
				final Dialog dialog = new Dialog(Login.this, R.style.transparentFrameWindowStyle);
				dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
				menuButton1 = (Button) dialog.findViewById(R.id.menubut1);
				menuButton1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO �Զ����ɵķ������
						startBaidu();
						dialog.dismiss();
					}
				});
				menuButton2 = (Button) dialog.findViewById(R.id.menubut2);
				menuButton2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO �Զ����ɵķ������
						startQQZone();
						dialog.dismiss();
					}
				});
				menuButton3 = (Button) dialog.findViewById(R.id.menubut3);
				menuButton3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO �Զ����ɵķ������
						startRenren();
						dialog.dismiss();
					}
				});
				menuButton4 = (Button) dialog.findViewById(R.id.menubut4);
				menuButton4.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO �Զ����ɵķ������
						dialog.dismiss();
					}
				});
				Window window = dialog.getWindow();
				window.setWindowAnimations(R.style.main_menu_animstyle);
				WindowManager.LayoutParams wl = window.getAttributes();
				wl.x = 0;
				wl.y = getWindowManager().getDefaultDisplay().getHeight();
				wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
				wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
				dialog.onWindowAttributesChanged(wl);
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();
		
			}
				
		});
		
	}
	
	
	public void doThread() {
		
		String userstudentid = account.getText().toString().trim();
		String userpwd=password.getText().toString().trim();
		if(userstudentid.equals("") || userpwd.equals("")){
			Toast.makeText(Login.this, "������ѧ�ź�����",Toast.LENGTH_SHORT).show();
			return;
		}
		
		 String url = "Service1.asmx";
		 String methodName="login";
		 HashMap<String, Object> params = new HashMap<String, Object>();		 
		 params.put("StudentID",userstudentid);
		 params.put("UserPassword",userpwd);		
		 CallWebservice callWeb=new CallWebservice(handler);
		 callWeb.doStart(url, methodName, params);
		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		 
		 
		
	}
	
	
	//�����߳�
		@SuppressLint("HandlerLeak")
		private Handler handler=new Handler()
	    {
	        @Override
			public void handleMessage(Message m)
	        {
	        	 ArrayList<String> datas = m.getData().getStringArrayList("data");       	 
	        	 login(datas);
	        	
	        };        
	    };
	
	    
	    private void login( ArrayList<String> datas){
	    	
	    	
			
	    	if(datas.size()!=0){
	    		Toast.makeText(this,"��½�ɹ�",Toast.LENGTH_SHORT).show();
	    		
	    		LocationApplication app = (LocationApplication) getApplicationContext();
	    		app.setISin(1);
	    		app.setusername(datas.get(1));
	    		sp=getSharedPreferences("login_information",Context.MODE_PRIVATE);
	        	editor=sp.edit();
	    		editor.putString("StudentID", datas.get(0));
	    		editor.putString("UserName", datas.get(1));
	    		editor.putString("UserCollege", datas.get(2));
	    		editor.putString("UserPassword", datas.get(3));
	    		editor.commit();
	    		Goback();
	    	}
	    	else if(datas.size()==0)
	    	{Toast.makeText(this, "��½ʧ�ܣ���������ȷ���û��������룡",Toast.LENGTH_SHORT).show();
	    	}
	    }
	    
	    
	    protected void startBaidu() {
			ArrayList<String> scope = new ArrayList<String>();
	    	scope.add("basic");
	    	scope.add("netdisk");
			mAuthorization.authorize(this,FrontiaAuthorization.MediaType.BAIDU.toString(), scope, new AuthorizationListener() {
						@Override
						public void onSuccess(FrontiaUser result) {
							Log.e("�ٶȵ�½����", "onSuccess");
							Toast.makeText(Login.this, "��½�ɹ���",Toast.LENGTH_SHORT).show();
							//��ȡ��Ϣ  �洢��Ƭ������
							userinfo(MediaType.BAIDU.toString());
							LocationApplication app = (LocationApplication) getApplicationContext();
							app.setISin(1);
							Login.this.finish();
						}
						@Override
						public void onFailure(int errCode, String errMsg) {
							Log.e("�ٶȵ�½����", "errCode:" + errCode+ ", errMsg:" + errMsg);
							Toast.makeText(Login.this, "��½ʧ�ܣ�",Toast.LENGTH_SHORT).show();
							
						}
						@Override
						public void onCancel() {
							Log.e("�ٶȵ�½����", "onCancel");
						}
						});
			}
	    private void startQQZone() {
			mAuthorization.authorize(this, FrontiaAuthorization.MediaType.QZONE.toString(),
					new AuthorizationListener() {

						@Override
						public void onSuccess(FrontiaUser result) {
	                            Log.e("qq��½����","onSuccess");
	                            Toast.makeText(Login.this, "��½�ɹ���",Toast.LENGTH_SHORT).show();
								//��ȡ��Ϣ  �洢��Ƭ������
								userinfo(MediaType.QZONE.toString());
								LocationApplication app = (LocationApplication) getApplicationContext();
								app.setISin(1);
								Login.this.finish();

						}

						@Override
						public void onFailure(int errorCode, String errorMessage) {
								Log.e("qq��½����", "errCode:" + errorCode+ ", errMsg:" + errorMessage);
								Toast.makeText(Login.this, "��½ʧ�ܣ�",Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onCancel() {
								Log.e("qq��½����", "onCancel");
						}

					});
		}

		private void startRenren() {
			mAuthorization.authorize(this, FrontiaAuthorization.MediaType.RENREN.toString(),
					new AuthorizationListener() {

						@Override
						public void onSuccess(FrontiaUser result) {
							Log.e("���˵�½����","onSuccess");
							Toast.makeText(Login.this, "��½�ɹ���",Toast.LENGTH_SHORT).show();
							//��ȡ��Ϣ  �洢��Ƭ������
							userinfo(MediaType.RENREN.toString());
							LocationApplication app = (LocationApplication) getApplicationContext();
							app.setISin(1);
							Login.this.finish();

							
						}

						@Override
						public void onFailure(int errorCode, String errorMessage) {
							Log.e("���˵�½����", "errCode:" + errorCode+ ", errMsg:" + errorMessage);
							Toast.makeText(Login.this, "��½ʧ�ܣ�",Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onCancel() {
								Log.e("���˵�½����", "onCancel");
						}

					});
		}
		
		private void userinfo(String accessToken) {
			mAuthorization.getUserInfo(accessToken, new UserInfoListener() {

				@Override
				public void onSuccess(FrontiaUser.FrontiaUserDetail result) {
					 UserName = result.getName(); 
					 Userurl = result.getHeadUrl(); 
					 LocationApplication app = (LocationApplication) getApplicationContext();
					 app.setuserurl(Userurl);
					 app.setusername(UserName);
					 Log.e("����", UserName);
					 Log.e("����", Userurl);
					 
				}

				@Override
				public void onFailure(int errCode, String errMsg) {
					Log.e("��ȡ������Ϣ", "errCode:" + errCode+ ", errMsg:" + errMsg);
				}
				
			});
		}	
	    
		
		
		protected void Goback(){
	    	 
			//��Ҫ��ȫ�ֱ���������   �������ʼ��
//    	 	Intent it=new Intent();
//			Bundle bundle=getIntent().getExtras();
//			value=bundle.getInt("value");
//			
//    		if(value==0){
//    			//����menu
//    			it.setClass(Login.this, MainActivity.class);
//    			it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
//    			bundle = new Bundle();
//    			startActivity(it);
//    	        Login.this.finish();
//    	        }
//    		else if(value==1){
//    			//���Է�����������
//    			it.setClass(Login.this, Search_helper_release.class);
//    			it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
//    			startActivity(it);
//    	        Login.this.finish();
//    		}
//    		else if(value==2){
//    			it.setClass(Login.this, DormRepair.class);    			
//    			startActivity(it);
//    	        Login.this.finish();
//    		}
//    		else if(value==3){
//    			it.setClass(Login.this,com.oiangie.slidingmenufragment.MainActivity.class);  
//    			it.putExtra("address", "");
//    			startActivity(it);
//    	        Login.this.finish();
//    		}else if(value==4){
//    			it.setClass(Login.this, com.oiangie.slidingmenufragment.MainActivity.class);    			
//    			startActivity(it);
//    	        Login.this.finish();
//    		}
    	 
    	 
     } 
}
