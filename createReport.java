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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class createReport extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_report);

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

        create cr = new create();
        cr.execute(urlLocal);
    }

    private class create extends AsyncTask<String, Void, ArrayList<Bitmap>> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public ArrayList<Bitmap> doInBackground(String... params) {
            //http接続を行うHttpURLConnectionオブジェクトを宣言
            //finallyで解放するためにtry外で宣言
            HttpURLConnection con = null;
            //http接続のレスポンスデータとして取得するInputStreamオブジェクトを宣言（try外）
            InputStream is = null;
            //返却用の変数
            StringBuffer conResult = new StringBuffer();
            //String sw = params[0];
            String sw = "http:/10.20.170.52/sample/createReport.php";
            String checkResult = "";
            switch (sw){
                case "http:/10.20.170.52/sample/createReport.php":
                    try {
                        String project_information = params[0];
                        String dates = "2020-05-13";
                        URL url = new URL(sw);
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        OutputStream outputStream = con.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        //POSTデータの編集
                        String post_data = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(dates, "UTF-8");// + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass_word, "UTF-8");
                        System.out.println(post_data);
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
                        InputStream inputStream = con.getInputStream();
                        String encoding = con.getContentEncoding();
                        if (null == encoding) {
                            encoding = "UTF-8";
                        }
                        InputStreamReader inReader = new InputStreamReader(inputStream, encoding);
                        BufferedReader bufferedReader = new BufferedReader(inReader);
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            conResult.append(line);
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                        inputStream.close();
                        con.disconnect();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("con" + conResult);
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
            System.out.println(projectName);
            Pattern p_date = Pattern.compile(regex_date);
            checkDate(p_date, conResult.toString());
            System.out.println(date);
            Pattern p_recorder = Pattern.compile(regex_recorder);
            checkRecorder(p_recorder, conResult.toString());
            System.out.println(recorder);
            Pattern p_fileName = Pattern.compile(regex_fileName);
            checkFileName(p_fileName, conResult.toString());
            System.out.println(fileName);
            Pattern p_path = Pattern.compile(regex_path);
            checkPath(p_path, conResult.toString());
            System.out.println(path);
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
            LinearLayout linearLayout_index = new LinearLayout(createReport.this);
            linearLayout_index.setOrientation(LinearLayout.VERTICAL);
            TextView title_projectName = new TextView(createReport.this);
            title_projectName.setGravity(Gravity.CENTER);
            title_projectName.setBackground(drawable1);
            title_projectName.setText(projectName.get(0));
            linearLayout_index.addView(title_projectName, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView title_date = new TextView(createReport.this);
            title_date.setGravity(Gravity.CENTER);
            title_date.setBackground(drawable1);
            final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            final Date nowDate = new Date(System.currentTimeMillis());
            title_date.setText(df.format(nowDate));
            linearLayout_index.addView(title_date, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView title_user = new TextView(createReport.this);
            title_user.setGravity(Gravity.CENTER);
            title_user.setBackground(drawable1);
            title_user.setText(MainActivity.username);
            linearLayout_index.addView(title_user, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            while (j < date.size()) {
                //super.onPostExecute(bitmap);
                //レポート用のLinearLayout
                LinearLayout linearLayout_Report = new LinearLayout(createReport.this);
                //水平方向に設定
                linearLayout_Report.setOrientation(LinearLayout.VERTICAL);
                //Noを入力
                TextView No = new TextView(createReport.this);
                No.setText("No: " + String.valueOf(j + 1));
                No.setGravity(Gravity.LEFT);
                No.setBackground(drawable1);
                linearLayout_Report.addView(No, new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                //工事情報
                //LinearLayout linearLayout_ReportInfo = new LinearLayout(createReport.this);
                //linearLayout_ReportInfo.setOrientation(LinearLayout.VERTICAL);

                TextView info1 = new TextView(createReport.this);
                String infoDate = "撮影日： " + date.get(j);
                info1.setText(infoDate);
                info1.setGravity(Gravity.LEFT);
                info1.setBackground(drawable2);

                final TextView info2 = new TextView(createReport.this);
                String infoFileName = "ファイル名：" + fileName.get(j);
                info2.setText(infoFileName);
                info2.setGravity(Gravity.LEFT);
                info2.setBackground(drawable2);

                TextView info3 = new TextView(createReport.this);
                String infoUser = "撮影者：" + recorder.get(j);
                info3.setText(infoUser);
                info3.setGravity(Gravity.LEFT);
                info3.setBackground(drawable2);

                final EditText com1 = new EditText(createReport.this);
                String hint = "コメントを入力する";
                com1.setHint(hint);
                //String infoCom = "コメント：" + comments.get(j);
                //com1.setText(infocom);
                com1.setGravity(Gravity.LEFT);
                com1.setBackground(drawable2);
                //com1.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

                Button exe = new Button(createReport.this);
                String text = "登録する";
                exe.setText(text);
                exe.setGravity(Gravity.CENTER);
                exe.setPadding(0, 10, 0, 10);
                exe.setBackground(drawable1);
                //com1.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                exe.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        String test1 = (String) info2.getText();
                        System.out.println(test1);

                    }
                });

                linearLayout_Report.addView(info1,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout_Report.addView(info2,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout_Report.addView(info3,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout_Report.addView(com1,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT));

                linearLayout_Report.addView(exe,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));


                //工事情報の格納
                /*linearLayout_Report.addView(linearLayout_ReportInfo,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT, 5));
*/
                //画像
                //imagePath = "/sdcard/DCIM/Camera/001.JPG";


                //Resources r = getResources();
                //Bitmap bmp1 = BitmapFactory.decodeFile(imagePath);
                ImageView pic = new ImageView(createReport.this);
                //pic.setW
                pic.setImageBitmap(bitmapArrayList.get(j));

                //pic.setImageResource(R.drawable.);
                pic.setBackground(drawable2);
                pic.setPadding(0, 10, 0, 10);
                //幅、高さ
                linearLayout_Report.addView(pic,
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));




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
            comments.add(m.group().substring(13, m.group().length() - 1));
            //System.out.println(m.group());
        }
        int count = 0;
        while (count < comments.size()) {
            if (comments.get(count).contains("[")) {
                String rep = comments.get(count).replace("[", "");
                comments.set(count, rep);
                if (comments.get(count).contains("username")) {
                    String lace = comments.get(count).replace("username", "");
                    comments.set(count, lace);
                }
                count++;
            }
        }
    }




}