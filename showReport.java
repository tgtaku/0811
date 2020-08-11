package com.example.sampleapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class showReport extends AppCompatActivity {

    public String urlLocal = "http:/10.20.170.52/sample/sample1.php";
    public String url = "http:/10.20.170.52";
    public static ArrayList<String> projectName = new ArrayList<>();
    public static ArrayList<String> date = new ArrayList<>();
    public static ArrayList<String> recorder = new ArrayList<>();
    public static ArrayList<String> fileName = new ArrayList<>();
    public static ArrayList<String> path = new ArrayList<>();
    public static ArrayList<String> comments = new ArrayList<>();
    public GradientDrawable drawable1, drawable2;
    public ScrollView Main;
    public LinearLayout linearLayout_Main;
    public String imagePath;
    public int i = 0;
    public int j = 0;
    //public static String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_report);
        //public void exportReport(){}
        //背景の定義①
        drawable1 = new GradientDrawable();
        drawable1.setStroke(3, Color.parseColor("#000000"));
        drawable1.setCornerRadius(3);
        drawable1.setColor(Color.parseColor("#c0c0c0"));

        //背景の定義②
        drawable2 = new GradientDrawable();
        drawable2.setStroke(3, Color.parseColor("#000000"));
        drawable2.setCornerRadius(3);

        Main = new ScrollView(this);
        setContentView(Main);
        //画面全体のレイアウト設定
        linearLayout_Main = new LinearLayout(this);
        //垂直方向に設定
        linearLayout_Main.setOrientation(LinearLayout.VERTICAL);

        // linearLayout_MainをContentViewにセット
        //setContentView(linearLayout_Main);

        //プロジェクト名のレイアウト設定
        LinearLayout linearLayout_Project = new LinearLayout(this);
        //水平方向に設定
        linearLayout_Project.setOrientation(LinearLayout.HORIZONTAL);
        //Weightの総数を決定
        linearLayout_Project.setWeightSum(10);
        //Text情報を入力
        TextView textView = new TextView(this);
        String str = "案件名";
        textView.setText(str);
        textView.setGravity(Gravity.CENTER);
        textView.setBackground(drawable1);
        //幅、高さ
        linearLayout_Project.addView(textView,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 7));
        TextView textView1 = new TextView(this);
        String str1 = "sampleProject";
        textView1.setText(str1);
        textView1.setGravity(Gravity.CENTER);
        textView1.setBackground(drawable2);
        //幅、高さ
        linearLayout_Project.addView(textView1,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 3));

        linearLayout_Main.addView(linearLayout_Project, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        //作成者のレイアウト設定
        //プロジェクト名のレイアウト設定
        LinearLayout linearLayout_User = new LinearLayout(this);
        //水平方向に設定
        linearLayout_User.setOrientation(LinearLayout.HORIZONTAL);
        //Weightの総数を決定
        linearLayout_User.setWeightSum(10);
        //Text情報を入力
        TextView textView2 = new TextView(this);
        String str2 = "作成者";
        textView2.setText(str2);
        textView2.setGravity(Gravity.CENTER);
        textView2.setBackground(drawable1);
        //幅、高さ
        linearLayout_User.addView(textView2,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 7));
        TextView textView3 = new TextView(this);
        String str3 = "sampleUser";
        textView3.setText(str3);
        textView3.setGravity(Gravity.CENTER);
        textView3.setBackground(drawable2);
        //幅、高さ
        linearLayout_User.addView(textView3,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 3));

        linearLayout_Main.addView(linearLayout_User, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        //作成日のレイアウト設定
        //プロジェクト名のレイアウト設定
        LinearLayout linearLayout_Date = new LinearLayout(this);
        //水平方向に設定
        linearLayout_Date.setOrientation(LinearLayout.HORIZONTAL);
        //Weightの総数を決定
        linearLayout_Date.setWeightSum(10);
        //Text情報を入力
        TextView textView4 = new TextView(this);
        String str4 = "作成日";
        textView4.setText(str4);
        textView4.setGravity(Gravity.CENTER);
        textView4.setBackground(drawable1);
        //幅、高さ
        linearLayout_Date.addView(textView4,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 7));
        TextView textView5 = new TextView(this);
        String str5 = "sampleDate";
        textView5.setText(str5);
        textView5.setGravity(Gravity.CENTER);
        textView5.setBackground(drawable2);
        //幅、高さ
        linearLayout_Date.addView(textView5,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 3));

        linearLayout_Main.addView(linearLayout_Date, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        //No、工事情報、写真のレイアウト
        LinearLayout linearLayout_Label = new LinearLayout(this);
        //水平方向に設定
        linearLayout_Label.setOrientation(LinearLayout.HORIZONTAL);
        //Weightの総数を決定
        linearLayout_Label.setWeightSum(20);
        //Text情報を入力
        TextView textView6 = new TextView(this);
        String str6 = "No.";
        textView6.setText(str6);
        textView6.setGravity(Gravity.CENTER);
        textView6.setBackground(drawable1);
        //textView6.setWidth(0);
        //幅、高さ
        linearLayout_Label.addView(textView6,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 9));
        TextView textView7 = new TextView(this);
        String str7 = "工事情報";
        textView7.setText(str7);
        textView7.setGravity(Gravity.CENTER);
        textView7.setBackground(drawable1);
        //textView7.setWidth(0);
        //幅、高さ
        linearLayout_Label.addView(textView7,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 5));

        TextView textView8 = new TextView(this);
        String str8 = "写真";
        textView8.setText(str8);
        textView8.setGravity(Gravity.CENTER);
        textView8.setBackground(drawable1);
        //textView.setWidth(0);
        //幅、高さ
        linearLayout_Label.addView(textView8,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 6));

        linearLayout_Main.addView(linearLayout_Label, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        //Main.addView(linearLayout_Main);

        reportView rv = new reportView();
        rv.execute(urlLocal);
    }


    private class reportView extends AsyncTask<String, Void, ArrayList<Bitmap>> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public ArrayList<Bitmap> doInBackground(String... params) {
            //returnの定義
            //String result;
            //引数として接続先のURLを取得
            //System.out.println(params[0]);

            //http接続を行うHttpURLConnectionオブジェクトを宣言
            //finallyで解放するためにtry外で宣言
            HttpURLConnection con = null;
            //http接続のレスポンスデータとして取得するInputStreamオブジェクトを宣言（try外）
            InputStream is = null;
            //返却用の変数
            StringBuffer conResult = new StringBuffer();

            //http接続
            try {
                //URLオブジェクト作成
                URL url = new URL(params[0]);
                //System.out.println(url);
                //URLオブジェクトからHttpURLConnectionオブジェクトを取得
                con = (HttpURLConnection) url.openConnection();
                //HTTP接続メソッドを設定
                con.setRequestMethod("GET");
                //接続
                con.connect();
                final int httpStatus = con.getResponseCode();
                System.out.println(httpStatus);
                //HttpURLConnectionオブジェクトからレスポンスデータの取得
                is = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(is, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = bufReader.readLine();
                //1行ずつ読み込み
                while (line != null) {
                    conResult.append(line);
                    line = bufReader.readLine();
                }
                bufReader.close();
                inReader.close();
                is.close();
            } catch (MalformedURLException ex) {
            } catch (IOException ex) {
            } finally {
                //HttpURLConnectionオブジェクトがnull出ないならば解散
                if (con != null) {
                    con.disconnect();
                }
                //InputStreamオブジェクトがnull出ないならば解散
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                    }
                }
            }

            //事前情報の取得
            String regex_projectName = "\"projectName\":.+?\",";
            String regex_date = "\"date\":.+?\",";
            String regex_recorder = "\"recorder\":.+?\",";
            String regex_fileName = "\"fileName\":.+?\",";
            String regex_path = "\"path\":.+?G\"";
            String regex_comments = "\"comments\":.+?]";
            Pattern p_projectName = Pattern.compile(regex_projectName);
            checkProjectName(p_projectName, conResult.toString());
            //System.out.println(projectName);
            Pattern p_date = Pattern.compile(regex_date);
            checkDate(p_date, conResult.toString());
            //System.out.println(date);
            Pattern p_recorder = Pattern.compile(regex_recorder);
            checkRecorder(p_recorder, conResult.toString());
            //System.out.println(recorder);
            Pattern p_fileName = Pattern.compile(regex_fileName);
            checkFileName(p_fileName, conResult.toString());
            //System.out.println(fileName);
            Pattern p_path = Pattern.compile(regex_path);
            checkPath(p_path, conResult.toString());
            //System.out.println(path);
            //result = conResult.toString();
            Pattern p_comments = Pattern.compile(regex_comments);
            checkComments(p_comments, conResult.toString());
            System.out.println(comments);

            //画像の抽出
            ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
            Bitmap bmp = null;

            //System.out.println("getImage" + address);

            while (i < date.size()) {
                String address = url + path.get(i);
                System.out.println(address);
                HttpURLConnection urlConnection = null;

                try {
                    URL url = new URL(address);

                    // HttpURLConnection インスタンス生成
                    urlConnection = (HttpURLConnection) url.openConnection();

                    // タイムアウト設定
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setConnectTimeout(20000);

                    // リクエストメソッド
                    urlConnection.setRequestMethod("GET");

                    // リダイレクトを自動で許可しない設定
                    urlConnection.setInstanceFollowRedirects(false);

                    // ヘッダーの設定(複数設定可能)
                    //urlConnection.setRequestProperty("Accept-Language", "jp");

                    // 接続
                    urlConnection.connect();

                    int resp = urlConnection.getResponseCode();

                    switch (resp) {
                        case HttpURLConnection.HTTP_OK:
                            try (InputStream ips = urlConnection.getInputStream()) {
                                bmp = BitmapFactory.decodeStream(ips);
                                bitmapArrayList.add(bmp);
                                ips.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case HttpURLConnection.HTTP_UNAUTHORIZED:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    Log.d("debug", "downloadImage error");
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                i++;
            }

            return bitmapArrayList;
        }

        @Override
        public void onPostExecute(ArrayList<Bitmap> bitmapArrayList) {
            //phpから取得した内容の書き出し
            /*try {
                FileOutputStream outSt = openFileOutput("001.txt", MODE_PRIVATE);
                outSt.write(result.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            while (j < date.size()) {
                //super.onPostExecute(bitmap);
                //レポート用のLinearLayout
                LinearLayout linearLayout_Report = new LinearLayout(showReport.this);
                //水平方向に設定
                linearLayout_Report.setOrientation(LinearLayout.HORIZONTAL);
                //Weightの総数を決定
                linearLayout_Report.setWeightSum(20);
                //Noを入力
                TextView No = new TextView(showReport.this);
                No.setText(String.valueOf(j + 1));
                No.setGravity(Gravity.CENTER);
                No.setBackground(drawable1);
                linearLayout_Report.addView(No, new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 9));

                //工事情報
                LinearLayout linearLayout_ReportInfo = new LinearLayout(showReport.this);
                linearLayout_ReportInfo.setOrientation(LinearLayout.VERTICAL);

                TextView info1 = new TextView(showReport.this);
                String infoDate = "撮影日：" + date.get(j);
                info1.setText(infoDate);
                info1.setGravity(Gravity.LEFT);
                info1.setBackground(drawable2);

                TextView info2 = new TextView(showReport.this);
                String infoFileName = "ファイル名：" + fileName.get(j);
                info2.setText(infoFileName);
                info2.setGravity(Gravity.LEFT);
                info2.setBackground(drawable2);

                TextView info3 = new TextView(showReport.this);
                String infoUser = "撮影者：" + recorder.get(j);
                info3.setText(infoUser);
                info3.setGravity(Gravity.LEFT);
                info3.setBackground(drawable2);

                TextView com1 = new TextView(showReport.this);
                //String hint = "コメントを入力する";
                //com1.setHint(hint);
                String infoCom = "コメント：" + comments.get(j);
                com1.setText(infoCom);
                com1.setGravity(Gravity.LEFT);
                com1.setBackground(drawable2);
                //com1.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

                linearLayout_ReportInfo.addView(info1,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout_ReportInfo.addView(info2,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout_ReportInfo.addView(info3,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout_ReportInfo.addView(com1,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT));

                //工事情報の格納
                linearLayout_Report.addView(linearLayout_ReportInfo,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT, 5));

                //画像
                //imagePath = "/sdcard/DCIM/Camera/001.JPG";


                //Resources r = getResources();
                //Bitmap bmp1 = BitmapFactory.decodeFile(imagePath);
                ImageView pic = new ImageView(showReport.this);
                //pic.setW
                pic.setImageBitmap(bitmapArrayList.get(j));

                //pic.setImageResource(R.drawable.);
                pic.setBackground(drawable2);
                pic.setPadding(0, 10, 0, 5);
                //幅、高さ
                linearLayout_Report.addView(pic,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 6));

                linearLayout_Main.addView(linearLayout_Report, new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                j++;
            }

            Main.addView(linearLayout_Main, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }


    //正規表現でJSON形式から配列に当てはめる
    private static void checkProjectName(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            String pName = m.group();
            projectName.add(pName.substring(16, pName.length() - 2));
            //System.out.println(m.group());
        }
    }

    private static void checkDate(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            date.add(m.group().substring(9, m.group().length() - 2));
            //System.out.println(m.group());
        }
    }

    private static void checkRecorder(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            recorder.add(m.group().substring(13, m.group().length() - 2));
            //System.out.println(m.group());
        }
    }

    private static void checkFileName(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            fileName.add(m.group().substring(13, m.group().length() - 2));
            //System.out.println(m.group());
        }
    }

    private static void checkPath(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            path.add(m.group().substring(9, m.group().length() - 1));
            //System.out.println(m.group());
        }
    }

    private static void checkComments(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            comments.add(m.group().substring(13, m.group().length() -1));
            //System.out.println(m.group());
        }
        int count = 0;
        while (count < comments.size()){
            if(comments.get(count).contains("[")){
                String rep = comments.get(count).replace("[","");
                comments.set(count, rep) ;
                if(comments.get(count).contains("username")){
                    String lace = comments.get(count).replace("username","");
                    comments.set(count, lace);
                }
                count++;
            }
        }
    }
    /*@Override
    public void onBackPressed(){
        Main.removeViewInLayout(linearLayout_Main);
    }*/

}
