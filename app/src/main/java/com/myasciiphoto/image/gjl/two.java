package com.myasciiphoto.image.gjl;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Context;
import java.util.List;
import android.widget.LinearLayout;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.Spannable;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.graphics.Color;
import android.view.WindowManager;
import android.util.DisplayMetrics;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.os.Build;
import android.app.Activity;
import android.kz.SinkFullScreen;

public class two extends Activity
{

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		
		
		
		
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(0x08000000);
		getWindow().addFlags(1024);
		if(getActionBar()!=null)
			getActionBar().hide();
		if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else if (Build.VERSION.SDK_INT >= 19) {
			//for new api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
		if((getRequestedOrientation()==2?true:null)!=null){
			SinkFullScreen s= SinkFullScreen.INSTANCE;
			s.blockStatusCutout(getWindow());
		}else{
			SinkFullScreen s= SinkFullScreen.INSTANCE;
			s.extendStatusCutout(getWindow(),this);
		}
		Intent intent = getIntent();
	String in1=	intent.getStringExtra("1");
		String in2=intent.getStringExtra("2");
		StringBuilder top = top(in1,in2,this);
		setContentView(cc(top, this));
	}
	
	
	
	
	public EditText cc(StringBuilder contents, Context context) {
//        contents = new StringBuilder().append("")
        float scale = context.getResources().getDisplayMetrics().scaledDensity;

        EditText tv = new EditText(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
      
        tv.setText(contents);
        tv.setTextSize(scale * 2);
        tv.setTypeface(Typeface.MONOSPACE);
        tv.setGravity(Gravity.CENTER);
        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				   View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());


        tv.setBackgroundColor(Color.WHITE);

         return tv;
    }
	
	
	
	
	
	
	 StringBuilder top(String tt,final String path, Context context) {
        final String base = tt;// 字符串由复杂到简单
        StringBuilder text = new StringBuilder();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Bitmap image = BitmapFactory.decodeFile(path);  //读取图片
        int width0 = image.getWidth();
        int height0 = image.getHeight();
        int width1, height1;
        int scale = 7;
        if (width0 <= width / scale) {
            width1 = width0;
            height1 = height0;
        } else {
            width1 = width / scale;
            height1 = width1 * height0 / width0;
        }
        image = CommonUtil.scale(path, width1, height1);  //读取图片
        for (int y = 0; y < image.getHeight(); y += 2) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = image.getPixel(x, y);
                final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                final int index = Math.round(gray * (base.length() + 1) / 255);
                String s = index >= base.length() ? " " : String.valueOf(base.charAt(index));
                text.append(s);
            }
            text.append("\n");
        }
        return text;
    }
	
	
	
	
	
	
}




/*
                                                
                                                
                                                
                    ww          rwqqqqwer       
                     er wwwer  we       ewr     
                     rerrr  rewq         rqr    
              rwqwqqerr                   ww    
        ewqwe                             ww    
    eqqqqwereer                    rwwerwwe     
               rer                   ewqr       
                 rqqq          rqwer    qer     
                  wrr      eqqwe     reeqqqe    
                ww  eerweer                     
              ewwwqq r         r                
                                                
                                                
                                                
                                                
*/
/*
                                            66hdsPPOIIYTREWWWWWWWWWWWWWWETUUIPPadgjl66                                                                    
                                        66hsIUYYYYYYTTRREEEWEEEEEEEEEWWEERTTTTTTTYUIOPagl6                                                                
                                 666ldUEWWWWWWEEEEEERRRRRRRRRRRRRRRRRRRRREEEEEEEEEWWWWWEWWWUfl                                                            
                            666kfPTEWWWWWWEERRRRRTTTTTYYYYUUUUYYYYYYYYYTTTTTTRRRREEEEEWWWWWWQQWUsjj66                                                     
                           6gsUEQQWWEEERRTRRRTTTYYUUUUUIIIIIIIIIIUIUUUUUUUYYYYTTTTRRREEEEEEWWWQQQWEYOfk6  6                                               
                        6hIEEWWEEEEERRTTYYYUUIIIIOOOOOOOOOOOOOOOIIIIIIIIIIUUUUUUYYYYTTRRREEEEEEEWWWWWWETsl                                                
                    66hUQQQWWERRRRRTYYUUIIOOOPPaPPPPOOOOOOOOOOOOOOOIIIIIIIIIIIUUUUUUUUYYYTTRRRREEEEWWWWWWETfl6                                            
                  6gOTWWWWEERRTTTYUIOOOOPPPPPPPPPPPPPPOOOOOOOOOOOOOIIIIIIIIIIIIIIUUUUUUUYYYYTTTRRRERTEWWWWWWEIsj6                                         
                lPTEWWEEERRTTYUUIOPaaaaPPaaaaPPPPPPPPPPOOOOOOOOOOOOOIIIIIIIIIIIIIIUUUUUUUYYUUYTTTREEEREEEEEWWWWEUdl6                                      
         6   6hUWEEWEERRTYYUIOPPaaaaaaaaaaaaaaPPPPPPPPPOOOOOOOOOOOOIIIIIIIIIIIIIIIUUUUUUUUYUUIIIIOOYRRREEEEEEWWWQWWYf6                                    
          6lsYWWWERRRRTYUIPaasssaaaasaaaaaaaaPPPPPPPPPPOOOPOOOOOOOIIIIIIIIIIIIIUUUUUUUUUUUUUUPsfgjjklhdOTEEEEEWWWWWWEEsl                                  
        6hPREWWERRTTTYUOaassssssssssaaaaaaaaaaPPPPPPPPPPPPPOOOOOOOIIIIIIIIIIIIIIUUUUUUUUUYYUOsgjkkllllkjgsIYREWWWWWWWWWRPj                                
      6fRWWWWERTTYYUIOaadssssaasaaaaaaaaaaaaaaaPPPPPPPPPPPOOOOOOOOOIIIIIIIIIIIIIIIUUUUYYYYYIsgjkll66llllljfaITRREEEEEWWWWEUf6                             
    6dTWEWWERYUUUYYYUIOOOPPPPPaassssaaaaaaaaaPPPPPPPPPPPPPOOOOOOOOOIIIIIIIIIIIIIIIUUUUUYYUIsgjkkkl66llkkkjhaURRRRREEEEEEWWWWUa66                          
  6dUWQWWERYYYYTTTYYYYYTTTYYYUUIOOPPaaaaaaPPPPPPPPPPPOOOOOOOOOOOOOOOIIIIIIIIIIIIIUUUUUUYIsgjkkkkll6lkllkkjfITRRTTRRRREEEEWWWWQYs66                        
 hPEWWERETTRTTTRRRRRRERRRRRRRTTYYUUUIIOOPPPPPPaPPPPPPPOOOOOOOOOOOIOIOIIIIIIIIIIUIUUUUUUOdjllkjklllkkklkkjfaUTTTTTTRRRREEEEWWWWQWTd6                       
ORQQWEERTTREEEEEEWWWEEREEEEERRTTTYYYUUUIOOPaaaPPPPPPPOOOOOOOOPOOOOIIIIIIIIIIIUUUUUUUUUIPfklkkll6lklllkjgdPOIYTTTTTTRRRRREEEWWWWWQQUj6                     
QQQQWWWEEEERRRTYUUYREWWWWWERRRTTTYYUIIIIIOOOOIIIIIIIIIIIIIUUUUUUUUUUUUUUIIIUIIUUUUUUUUUIahjlllllll6hsOUTTTTTTTTTTTTTRRRRRREWWWWWEWQWPk                    
QQQQQWRUOsdghjkkjfOREWWWEERTTTTTYUIIIIIUUYYYTTTTRRRRRRRRRRRRRRTTTTTTTTYYYYUUUUUUUUUYYYUYOdhllllllljaTTTTTTYTTTTTTTTRTTRRRREEEEWWWWEWETak                  
l66l66666      6gTEEEERTTTTTTYYYYYYYUUYYTTRREEEEWWWWWWWWWWWEEEEEEEERRRRRTTTTTYYYYYYYYYYYIdhklll6lkgOTTTYYYYYTTTTTTTTRRRRRRRREEEEEWWWWWWTa6  6             
             6hUWWWEERRTYYYYYTTTTTTRRRREEEEEWWWWWWQQQWQWQQQQWWWWWEEEEEEERERRRRRRRRRRRTTRTOfkllllljaUTTTYTYYTTTTTTTTTTRRRRRRRRREEEEWWWWWWWEIfllkk6666666   
           6gIEWEERRTTYYYTTTTTTTRREEEEWEERYYTRWQQEUOasaaOOOIUYTREEEEWWWWWWWWWEWWEEEEERREEEYPsgggdPYRRTTTTYYTTTTTTTTTTTRRRRRRRRREEEEEEWWEEWWWWEEEWWWERYUOas
         6jURWWERRTYTTYTRRRRRREEWWWQWRUPsdsaOYREIdk666lll66lkjhhhhggdPITREEEWWWWWWWWWWEEEEWWWRTTEWEERRRRRRTTTTTTTRRRRRRRRRRRRRRRREEEEEWEEWWWWWWWQWWWWWWWWE
     6  6PRWWERRTUYTYTREEEERREWWTPj666khfssaPIYTsk666666l66ll666lllllkjgfaPPPPPPPPOIUTWWQQWWWWWWWEWEEEEEERRRRRRRRRRRRRRRRRRRRRRREEEEEEWWWWWWWWWWWWWWWQWWWW
      6gOEWWWERTTRRREWWWWWRTUPg6  666kfsPUTTRREEIf666lllkl666l666lkjhgfsaPPPPPaaaaaaaPOUYTREEWQQQQWWWWWEEEEEEEEEEEEEEEERRERRRRRREEEEEEEEWWWWWWWWWWWWWWWWWQ
    6kPRQWWWWEWWWWWWWERIsfk66      66sTEWWWWQQQQWTahllllll666lhsOIUTEWWWWERRRRRTRRRTUPfhjkjgdsaPOITEWQQQWWWWWWWWWWWWEEEEEEEEEEEEEEEEEEEEEEWWWWWWWEEEWWQWQQ
    fEWQQWWWWWWQQQTPhl66            6ldRWQQWQQQQQWEOglll666lfYRWQQQQQQQQQQWEEWWQQQEYdjlllklll66lkjhjjhfaUEQQQQQQQWEWWWWEEEEEEEEEEEEEEEEEEEWEWWWWWEWWQEYPsd
  6YEWWWQWWWWEROf66                     sRWWQWERRYUOfl66lkjPTEWWWERTPgk666lllll666lkllllllllllklll6lkkjkklljhdaITEEWWWWWWWWWWWEEEEEEEEEEEEEEWWWWWWQTaj6   
 lgREWQWETUafk6                         fUEERshlllkl666lljfaPPPsgk666666666666666666l66llkllllklkkkkllkkkklllkll6ljfsPIYEEWWWWWEEEEEEEEEEEEEWWWWWWQUh6    
 6hTERIdjl6                             dYRRYgl6666lll6lllkl666666666666666l666666666lkllllllllllllllllllllllkllllklllkhdIEWWWWEEEEEEEEEEEEEWWWWWWQUj6    
   66                                   dTRRRPgl666llll666666666666666666666666666l6666666llllllllllllllllkkkllkkkkkkjklksTWWWWWEEEEEEEEEEEEEWWWWWWYf6    
                                       6PRERREIhl66666666666kdPPfj666666666666666666llllllllllllllklllkkkkkkkkkkkkjkkkkljPEWWWWEEEEEEEEEEEEEEEWWWWWRYj    
                                      6hUEWWWQWRdk66l66666kdUQQQWYd666666666666666666666llllllllllllllkkkkkkkkklkkjjkkkkhIQWWWWWEEEEEEEEEEEEEWWWWWWERg    
                                      kaYEWQQQQQEP6l666666kYWQQQQQTfl666666666666666666666llllllllllllklkkkkkkllkkjjkkkkhIQWWWEEEEEEEEEEEEEEEEWWWWWEEg    
                             6666666lhaUTEWWQQQQQUjl666666jTQQQQQQEUg66666666666666666l666lllllllllklllllkkkkkkkkkkkkkkkhIQWWWEEEEEEEEEEEEEEEEWWWWWEEg    
                        66khgfsaPPPPaPOOUYREWQQQWOl6666666kYQQQQQQTdl66666666666666666l666llll6lllllllllkkklkkklkkkkkkkkhIWWWWEEEEEEEEEEEEEEEEWWWWWERh    
                       jsaOIIIOPaaaasPOOIUYRWQQWIf6666l6666gIQQQQUd66666666666666666666666llllllllklllkllllkkkklkkjjkkkkhIWWWWWEEEEEEEEEEEEEEEWWWWWEEg    
                   66gOUUIIIOPsdfddsssaPOIUUUIIOgk6666l666666gdfj66666666666666666666666666lllllllllllkkkkkkkkkkkkkjjkkjgIWQWWEEWWEREEREEEWEWWWWWWWWWf6   
                  lgOYYIIUIPdgjhggggggghggsaPOOOaf66666666666666666666666666666666666666666ll6llllllllllkkkkkkkkkkkkkkkkgIWWEEWWRREWWEWEEWWEEWWWWRTYRf6   
                6ksUUUUIafjl66lllkjjkkkkkkhfsPOUUPgl66666666666666666666666666666666666666666lllllllllllkkkkkkkkkkkkkkllhIWWWWYPgfsPYEWWWWWWWETIOPaPYf6   
               6kaIIIIOh66     6666666llkkkkjfaIUUUgl666666666666666666666666666666666666666666lllllllllkkkkkkkkkkkkkjklkPEEWWPflllkjjgsOIOagklkjgsOTd6   
               hPIIIOf66           666lkkkklljfPIYYOf666666666666666666666666666666666666666llllllllllllllkkkkklkkkkjkkkksTWQQEP6ll666666666666lgOTREf6   
              6fIIIIdk6              66lkkkllkjgPUYUPk66666666666666666666666666666666666666llllllllllllkkkkkkkkkkkkkkkkjdUEQQWYdhllkk66666ljfaUEQWRTg    
             6fsPOPaj66              66llkkkkkkksIYIPgk666666666666666666666666666666666666lllllllllllllklllkkkkkkkkkkkkkdUEWWWWWYshk66ljdPURWWWWQQROk    
             6saaPPsj66              66lllkklkkkdPUIPfk666666666666666666666666666666666666lllllllllllllkllkkkkkkkkkjjkkjdUEWWWWEEERTRRREWWWWWWWWWQYd6    
             6saaaasgk66           666lllllllkkkfPIUOdj6666666666666666666666666666666666666llllllllllllkkkkkkkkkkjjkkkkjdYEWWEEREEEEEEEEEEEEEWWWWQUk6    
             6faPassfhl666      666666lllllkkkkkdPUUPgk6666666666666666666666666ll66lllllllllllllllllllkkkkkkkkkkkkjjkkkjdYWWWEEEEEERREEEEEWEEWWWWQI6     
             6ldOPasgjl666666666666666llllkkklkjaIUUPgkl66666666666666666666666666llllllllllllllllllkkkkkkkkkkkkkkkkkkkkjdYWQWEERRRRRREEEEEEREWWQWEa6     
               gOOOPsgll66l6l6l6lllll66lkkkklkhdIYYUajl666l6666666666666666666666llll666llllllllllllllkkkkkkkkkkkkkkjkkkjsURWWWEREEEEEEEWWWWEWQQQWPl      
               ldIIIOsgk6llllllllllllllkkkkkkhfPUYYPfl6666l66l66666666666lllllllllllllllllllllllllllllkkkkkkkjkkkjkkjjjkjsYWWWEEEEEEEEEEWWWWWWWWQEg       
                6aIIIIOdjk666llll66lklllkkkjgsPUUUPgl6ll6lll6ll6666666666666666lllllllllllllllllllkkkkkkkkkkkkkkjjkjjkjjjdYWWWEEREEEEEEWWWWWEWWWWOk       
                6gaOIIUIPshk6666lllllllllkgsOIIIOajl6llkkkl666llll66666lllllll66l66lll6lllllllllllkkklkkkkkkkkkkkkjjkkjhjdYEWWEEREEEEEEWWWWWEEWWI6        
                6kdIIIUUIIOPsdghjjjjjhgfsPIIUIIIdh6lllllllllllllll6l66lllllllllllllllllllllllkkkkkkkkkkkkkkkkkkkkkjjjjjjkaREWEEEEEEEEEEWWWWEWWWOh         
                66dYUIIIIIIIIOPassssaPOIIUUIPsgjklllklllkl6llllll6llllllllllllllllllllllkkkklkkkkkkkkkkkkkkkkkkkjjjjjhjkjOWWWEEEEEEEEEEWWWWWQEYk          
                 6fTUIIOOOPOOOOOOOOOOIUUOPdgjkkllllll6llkl6lllll66llllllllllllllllllllkllkkkkkkkkkkkkkkkkjjkkkkkjjjjjjkjsYWWWEEEEEEEEEEWWWWWQIj6          
                6 fTYYUIPdfgffdsaaPPadgjkkkkllkkkllllklllllllkllllllkkllllllllllllkllllllkkkkkkkkkkkkkkjjjjjjkkkkjhjjkjfYEWWWWEEEEEWWWWWWWWWIg            
                 6jPTRTYPgjkjjjjjjkkkkkkkklkkkkklllkkkllklllllkklkklllkllllllllkkllllllkkkkkkkkkkkkkkkkkjjjjjjkkkkjjkkgPRWWQWWWWWEEWWWWWWWTs6             
                  6jIRRRIfjkkkkkkkkkkkkkkkkkkkkkkkkllllllkllllkklkkkllkkkllllllkkkklllkkkkkkkkkkkkkkjkkjjkjjjkjkkkjkkjORWWEWWWWWWWWWWWWWYdl6              
                   6ORRETOglkjjkkjkkkkkkkkkkkkkkkkkkkkkkkllllkkkkkkkklkkkjjkkllkkjklkkkkkkkkkkkkkkkkkkkkkjjjjjjkkkkkdUEWWWWWWEWWWWWWERYf66   66           
                   6hPERERahjkkjjkkkkkkjkkkkkkkkkkkkkkkkkkkkklkkkllkkkllkdsdhklkkkklkkjkkkkkkkkkkkkkkkjjjjjjjjjjjjhaYWWWWWQQWEWWWEYIsh           6        
                    6hTEERYOhjkkkkjjjjjjjjkkkkkkkkkkkkkkkkkkklkkkkkkkllkgIYUgkkkkkkklkkkkkkkkkjkkkjjjjjjjjjjjjjjhgaTEEWWQQWWWWYOfgl                       
                     6jIEEEROdkkjjkkjjjjkkkkkkkkkkkkkkkkkkkkkklkkkkjkllgPTRTglkkkkkjlkjkkkkkjjjjkjjjjkjjjjjjjjjhjdITEEEEWWWRsk66                          
                       hIWERTIfjjhjjkkkkkkkkjjjkkjkkkkkkkkjjklllkkkkllkaREETfkkkkkklkkjkkkkkkkkjjkkkkjjjjkkkkjjjhfaYRTEWEEO6                              
                        kOYRRRTOfhhjjjkkjjjkkkkkkjjkkkkkkkkkkklkkkkkkjgOEETIgkkkjhjkkkkkjkjjjjjjjjjjkkjjjjjjjhhghhgITREERal                               
                        66dUTRRRYIdhjjjjjjkjjjjkjjjjjjjkkkkkkklkkkklkfYRERIfjlkjjjjjjjkkjjjjjjjjjjjjjjjjjjhggggghhgPYREERs66                              
                         666PYEERTTIdghjjhjjjjjkjjjjjjjjjkkkkkhjlllkPTTRRYakkkjjjkkkjjjjjjjjjjjjjjjjjjjjhgffddfggggdOYREROj6              66              
                             kPYRRRRRUPdgjkjjjjjjjjjjjjjjjjjjjkljhsYREERYdhljjjjjjjjjjjjjjkkkjjjjjhgfsPIOadggffhggggsOTEWWYdjkkllkkkkll66                 
                              6ksUREEERTIPdgjjjjjjjjjjjjjjjjkkfsPYRRERTOdhkjjjkjjjjjjjjjjjjjjjhhhhgdaIYRTTUPdghhhhggsITREEEWEERRRRTRRTTUOPsfk6            
                                66ksREEEEERYIPdghhhhjjhhhhgdPUYTRRERYOdhkjjjjjkjjjjjjjhhhhhgfffffffsOUYTRRERUsghgfaIYRREEERRRRRRRRTTRRREEWEERYIdk6        
                                    6gIREEREWWERUPadfhhfsITRRTTTTUOdhllkjjkkkkkkkkjjjhhhhggfdfffdaOUYTTTTTERTRTYYYTTRERRRRRRTTTTTTTTTTRRRREEEEWWWYf6      
                                      66gaUREEEERRYTTRRRRERYYYIPdfghhhjjhhhhhhhjjhhhhhggffdsaaOUYTRRRRRRRRTTREEEEEERTYYUIIIIIIOOOOOOOOOOOIUYYTREEEWTaj6   
                                          6lgsIRRRRTTTTRRRRRTTYUOPPPaPPPOOOPPPPPOOIIUUYYYTTTTTRRRRETIdggfaIREREERTYUIOPasssdffffffddddddddsaaPPITRRREYOgk6
                                               66gOTERRRREEWWEEERRRRTTUUIUYYYTTRREEERREEEEWWWWEYOf6    66fYEEERTUOPaPPasffgggggggfffdddddffffffdPITTTREERY
                                                    6lgaIYYUUYTTRTRTTTTTTTTYYTTRRERRTTYYYYOdl6          fIEEERUOPassddffghhggggffffffffffsssddsdddaITRRRRR
                                                            66lkjgfdsssssssssssddgjl666               6dYEERYPaaPasfghhhhhhhhgggfffffddddddddddddddaOUTRRE
                                                                       666666                        ldEERTYIssdddfhjjjjhhhhgggffffdddddddddddssdddddaOUYY
                                                                                                    6aTERTYOasfggghjjjjhhgggggggfffffdddddddddssddsddsaPOO

																									*/
