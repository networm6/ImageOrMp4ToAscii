package com.myasciiphoto.image.gjl;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.content.DialogInterface;
import android.net.Uri;
import android.media.MediaMetadataRetriever;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import android.widget.Button;
import android.kz.Base;
import android.kz.Toastkeeper;
import android.app.AlertDialog;
import android.graphics.BitmapFactory;
public class MainActivity extends Base 
{
	
	private void showResponse(final Button op,final String response){
        runOnUiThread(new Runnable() {
				@Override
				public void run() {


					try {
						op.setText(new String(response.getBytes(),"UTF-8"));

					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();

					}
				}
			});
	}
	
	
	Bitmap bitmap;
	String filepath;
	ImageView imageView;
	String in;
	int result;
	EditText te;
	List<Bitmap> bvie;
	MediaMetadataRetriever a;
	int po,ao;
	String iit;
	private void performJcodec(final View v) {
		showResponse((Button)v,"开始合成视频");
		
		new Thread("合成视频"){
			public void run()
			{
				try {
						SequenceEncoderMp4 se   = null;


					File out = new File(getExternalCacheDir().getPath(), "jcodec_enc.mp4");
					se = new SequenceEncoderMp4(out);
                    int a = 0;
					for(Bitmap tmp:bvie){
						
						showResponse((Button)v,"开始合成视频"+a+"/"+(bvie.size()));
						a++;
						
						bitmap = CommonUtil.cr(iit,tmp, MainActivity.this);
						se.encodeImage(bitmap);
					}

					se.finish();
					sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(out)));
					
					showResponse((Button)v,"成功，文件在/sd/Android/data/com.myasciiphoto.image.gjl/cache");
				} catch (IOException e) {
					showResponse((Button)v,"失败"+e.getMessage());
					
				}
			}
		}.start();
	}
	public void vio(final View v){
	 iit=te.getText().toString();
		a.setDataSource(in);
		po=(Integer.parseInt(a.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION)))/5;
		new Thread("加载视频"){
			public void run()
			{
				while(true){
					bvie.add(zh(ao));
					showResponse((Button)v,ao+"/"+po);
					ao=ao+1;
					if(ao==po){
						performJcodec(v);
						break;
					}
				}
			}}.start();
	}
	public Bitmap zh(int time){
		return(a.getFrameAtTime(time*5000,MediaMetadataRetriever.OPTION_CLOSEST));
	}
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        
		inlayout(R.layout.main);
        super.onCreate(savedInstanceState);
		Bar(R.id.top);
		
		
		imageView=findViewById(R.id.mainImageView1);
		te=findViewById(R.id.mainEditText1);
		a=new MediaMetadataRetriever();
		bvie=new ArrayList<>();
		AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
		builder.setIcon(R.mipmap.ic_launcher);//设置小图标
		builder.setTitle("温馨提示");
		builder.setMessage("点击<复制>按钮，可以使图片变成可以复制的文字。在使用所有功能前，务必先选择图片。显示图片后，本软件内支持单指移动，双指缩放。");

		builder.setPositiveButton("明白了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
		//添加监听事件
		builder.setNegativeButton("联系作者", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=330771794";//uin是发送过去的qq号码
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					
                }
            });//设置对话框按钮
		builder.show();
		
		
		}
		
		
		
		
		
	
	public void doPick(View view) {
		
		
		new Thread(){ 		
		public void run() 		
		{ 			filepath = CommonUtil.amendRotatePhoto(in, MainActivity.this);
			String it=te.getText().toString();
			bitmap = CommonUtil.createAsciiPic(it,filepath, MainActivity.this);
			runOnUiThread(new Runnable() {
							  @Override
							  public void run() {
								  imageView.setImageBitmap(bitmap);
								  }
								  });
		
		} 		
		}.start();	 		
	}
	public void doPick2(View view) {
			
		
		new Thread(){ 		
			public void run() 		
			{ 			filepath = CommonUtil.amendRotatePhoto(in, MainActivity.this);
				String iit=te.getText().toString();
				//bitmap = CommonUtil.createAsciiPicColor(iit,filepath, MainActivity.this);
				bitmap=Utils.getTextBitmap(BitmapFactory.decodeFile(filepath),iit,19);
                runOnUiThread(new Runnable() {
						@Override
						public void run() {
							imageView.setImageBitmap(bitmap);
						}
					});

			} 		
		}.start();	 		
		
		
	}
	public void save(View view) {
        CommonUtil.saveBitmap2file(bitmap, MainActivity.this);
    }
	public void chose(View v){
		Intent it = new Intent(Intent.ACTION_PICK);
		//it.setType("image/*");
		it.setType("*/*");
		
		toa("记得添加文字",Toastkeeper.GRAVITY_CENTER);
		startActivityForResult(it, 1000);
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1000 && resultCode == RESULT_OK){
			in = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
		}
	}
	
	public void ttt(View v){
		Intent a=new Intent(this,two.class);
		
		filepath = CommonUtil.amendRotatePhoto(in, MainActivity.this);
		String it=te.getText().toString();
		a.putExtra("1", it);
		a.putExtra("2", filepath);
		
		startActivity(a);
	}
	
	
	
	void toa(String in,int ty){
		Toastkeeper.getInstance()
			.createBuilder(this)
			.setMessage(in)
			.setGravity(ty)
			.show();
	}
	
	
	
}

/*
dddddddddddddddddddddddddddddddddddddddddddiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
ddddddddddddddddddiddddddddddddddddddiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
dddddddddddiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
dddddddiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiatetaiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiaeaseiiiiiiiiiiiiaomemeiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiitpsekkkkkkkiikiktpptiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikkiaeseikaeeeaaakkbsekikiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikaeoepyyyyyyssssssysteaakikiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikkaeboesyyppppppppppppppyysetoobeakiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikbesssyyyypppppppppppppppppppppppyyebiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikmpyyyppppppppppppppppppppppppppppppmkiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikmppppppyyyppppppppppppppppppppppppptkiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiktppppyyssssssssssssyyyyyyyppppppppybiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiieypyyseeeeeeemmmmmeeeeeeeeeesypppayeiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiieyseemmmmtttoooooooootttmmmeeyyppekiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiibeeeemmeeeeeemmmmmmeeeeeeemeeespptkiiiiiiiiiiikiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikiiateeeeeeeeeeeeeeeeeeemmeeemmeeesebkikkkkiiikkkkkkkkkkkkkkkkkkkkkkkkkkkkkkikkkkkkkkkk
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikkikkkaoepyssssseespppsesyyysssyyseeeeokkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkikiiiiiiiikkkid
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikkkkkkkkkkkkkkkkameeeseemmmmmeetmeemmeeeeeypyseeemtekkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkikkkkkkkkkkeoem
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikkkkkkkkkkkkkkkkkiateeeeeeeeeseobomsseeeeeeeeemeseseekkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkiiiiaebeboomyys
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikkkkkkkkkkkkiiiiiiibmemmmmeeemttttoteeeemmmmmtmmeseekkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkabtteypppppsppe
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiameeeeeeeessssssseeeeemmmmmeeetekkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkaeteyppppppppppppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikbmeeeeeeeeeeeeeeeeeesseeeeeeoakkiiiiiikkikkkkkkkkkkkkkkkkkkkkkkabepppppppppppppppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikoeeeesssseeeessyyysseeeeeeoaiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikkkaosypppppppyppppppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiibeeeeeeyyssseeeeeeeeeeeeembaiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiketspppppyyyyppppppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiemeeeeeeeeeeeeeeeeeeeeeeeetaiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiatyppyyyssssyyppppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiddameemmmeeeeeemmmeessssseeemobkiiiiiiiiiiiiiiiiiiiiiiiiiiiiikemyysseeeeeeesssyyy
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiidiemeeeemmmmmmmeeesseeeeeettepytekiiiiiiiiiiiiiiiiiiiiiiiiiikteysseeeemmmeeeeeee
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiddamesyssssssssssyyyeeessesypppppstaiiiiiiiiiiiiiiiiiiiiiiiikoeeeeeeeeeeeeeeeeee
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiidiaoeypyyyyyyyyyyyyyyyypppsesyppppppppppstkiiiiiiiiiiiiiiiiiiiiikbtmmmmeeeeeesssssss
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiddiiikeomeyppppppyyyyyyypppysssysseypppppppppppppppyebkiiiiiiiiiiiiiiiiiiikebtmmeeeeeeeeeeeee
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiaoeypppppppppppppyssyyssssyppppyypapappppppppppppppppppsoaidiiiiiiiiiiiiiiikabtmeeeeeeeeeeeee
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiktypppppppppppppppppppyssyyyssyppppppppppppppppppppppppppppppekdiiiiiiiiiiiiiiikkbeeeeeeeeeeeeee
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiaeppppppppppppppppppppppaaaaaaaaaappppppppppppppppppppppppppppppeadiiiiiiiiiiiiaomeesssssssssssss
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiaepppppppppppppppppppppppppapaaaaaaappppppppppppppppppppppppppppppseikkiiiiiiiiesppsesssssssssssss
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikmpppppppppppppppppppppppppaaaaaaappaappppppppppppppppppppppppppppphptkiiiiiiiiktpapsesssssssssssyp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiitpppppppppppppppppppppppppppaaaaaaaaaapapppppppppppppppppppppppppapppytkiiiiiiiampappssssssssssyypp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiibypppppppppppppppppppppppppppaaaaaaaaapaapppppppppppppppappppppppaapppppokiiiaomsppapppyssyyyyyppppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikepppppppppppppppppppppppppppppaaaaaappppapppppppppppppppppppppppppappppasoteypaapppppppppppppppppppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiitpppppppppppappppppaaaapaaapppaaaaaappppppaapapppppppaaaaapppppppppppppppppppppppppappppppaaaaaaapppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikspppppppppapeeemeptbepapyeypaapysypaaaaaaaappaaaaaaaaaaaaaaaapppaaaaaaaaaappppppppaaaaaaapppppppppppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiopppppppppasepaasepobpappsepaeoesppapettmeesypaaaaaaaaaaaaaaaaaaaaapaaaaaappppapppppaaaaaapppppppppppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiikepapppppahyepapppapboppppseaeosaappsboyhahaetyaymomypapaaaaaaaaaaaaaaaaaaapppppppppppppppapaaaaaappppp
iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiaeseppaaaptttyaayeppboimapo tttypaapbophpppppi spebphaaapoobbbbppapdiaaaaaappb             meeeeemtoobb
iiiiiiiiiiiiiid              iiiiiiiii   diiiiiiiiibykippppab ypeeppphpbd hb dtmbi ypppoophppps do eetpappappaas be         baapytbboobbea  kbbbbbbbbbbbbb
iiiiiiiiiiiiiiiiid iiiii  iiiiiiiiiiiiiid  diiiiiikk tmmikpb   ddd eappe aa  ddd ddi aaymepayd epapi apapaaaapk eaaao eayieaaaaaytbbooa  ibbbbbbbbbbbbbbbo
bbbbbbbbbbbbbbbbee abbbbi ebebbbbadae ieeebeei abebb papadidyaaaaa eapk  thdiood eea aaaask dtypadeeoo  itaaay oysyd toa kapaapsseeeemk otttobboessyyppppp
bbbbbbbbbbbbeddddd dddd      debbk ae dbbbbbbbd bbbb mmmidaae maaa epsiy oppsi  sppyieappampaapaadyyeyaaypapoi  tpapppk epddpapsssyyyye epppppppppppppppap
bbbbbbbbbbbebeeeek eebbbk ebbbbbe  bb dbbbbbbbe  bbo pppe aaae maa baaap omdbsa  d  taaaaapaapaaedpaaaaaaaaapy baapt  eam aappapaaaaaab yaaaappppppppppaaa
bbbbbbbbbbbbbbbbe dbbbbbk ebbbbba kbb dbbbbba ai abt paab aaaapaaa oaaap bt dosa  yb saaaapaaaaab paaaaaaaaaap eaapeaat   maaaaaaappppo sppppppaaapppppppy
bbbbbbbbbbbbbbbe debbbbbk ebbbbbkdbbb dbbbbbi ebbbot kakd paaaaaae eaaaa eea dtpe epb epppaaaaaao paaaaaaaaappdeaapo  tpapa baapaymakai btttoootttoooooooo
bbbbbbbbbbbbbed ibbbbbbbk abbbbbbbbbbk      deebbeom yaapyaaaai   baaaap appap   mppapappaaaaaaastpaaaaaaaaatoeaaaypaaaaaaapaaaaapeooootttttoooooooooooooo
bbbbbbbbbbbbbbebbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbtpaaaaaaaaaaaaaaaappaapyssyyssypaaappaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaapeotmmmttttttooobotoootmt
bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbepaaaaaaaaaaaaaaaaapetbeaeeaaaakaebmpappaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaappppppppppppppppppppppppppa
bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbtyaaaaaaaaaaaaaaaapmbbbteeeaeteemobeeebepaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbmpapaaaaaaaaaaaaapbabepppayoeospaappebakatpaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbboepaaaaaaaaaaaaaahsotyaaappaptabspaaaapeaiibsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaapppppaaappppppppppppyyysse
bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbtypaaaaaaaaaaaaaaapeyaaaappahyoetyppahaapoiiaeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaapettttttttttttooooooooooot

*/
