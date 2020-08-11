package com.example.sampleapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class showPDF extends AppCompatActivity {

    String url = "http://";
    public static ArrayList<Bitmap> bitmapPDF = new ArrayList<>();
    private static int pageNum;
    private static TextView pdfPage;
    private static ImageView view;
    private int i = 1;
    private static String pageText;
    private final int CODE_MULTIPLE_IMG_GALLERY = 2;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_p_d_f);

        pdfPage = findViewById(R.id.page);

        //File sdcard = Environment.getExternalStorageDirectory(DOWNLOAD_SERVICE);
        File path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        System.out.println(MyPage.paramFileName);
        System.out.println(path);
        ParcelFileDescriptor fd = null;
        PdfRenderer renderer = null;
        PdfRenderer.Page page = null;
        //PdfRenderer.Page page = null;
        try {
            // SDカード直下からtest.pdfを読み込み、1ページ目を取得
            fd = ParcelFileDescriptor.open(new File(path, "files.pdf"), ParcelFileDescriptor.MODE_READ_ONLY);
            System.out.println(fd);
            renderer = new PdfRenderer(fd);
            pageNum = renderer.getPageCount();
            page = renderer.openPage(0);


            view = (ImageView) findViewById(R.id.pdfImage);
            int viewWidth = view.getWidth();
            int viewHeight = view.getHeight();
            float pdfWidth = page.getWidth();
            float pdfHeight = page.getHeight();
            Log.i("test", "viewWidth=" + viewWidth + ", viewHeight=" + viewHeight
                    + ", pdfWidth=" + pdfWidth + ", pdfHeight=" + pdfHeight);

            // 縦横比合うように計算
           /* float wRatio = viewWidth / pdfWidth;
            float hRatio = viewHeight / pdfHeight;
            if (wRatio <= hRatio) {
                viewHeight = (int) Math.ceil(pdfHeight * wRatio);
            } else {
                viewWidth = (int) Math.ceil(pdfWidth * hRatio);
            }
            Log.i("test", "drawWidth=" + viewWidth + ", drawHeight=" + viewHeight);
            // Bitmap生成して描画
            Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
            page.render(bitmap, new Rect(0, 0, viewWidth, viewHeight), null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
*/
            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
            bitmapPDF.add(Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888));
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            view.setImageBitmap(bitmap);
            pageText = i + "ページ / " + pageNum +"ページ";
            pdfPage.setText(pageText);
        } catch (FileNotFoundException e) {
            System.out.println("-------"+fd);
            System.out.println("--------ファイルなし-----------");
            //showPdf sp = new showPdf();
            //sp.execute(url);
            //e.printStackTrace();
            getPdf(view);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fd != null) {
                    fd.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (page != null) {
                page.close();
            }
            if (renderer != null) {
                renderer.close();
            }
        }

    }



    private class showPdf extends AsyncTask<String, Void, ArrayList<Bitmap>> {
        @Override
        public ArrayList<Bitmap> doInBackground(String... params){
            ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
            Bitmap bmp = null;
            return bitmapArrayList;

        }
        @Override
        public void onPostExecute(ArrayList<Bitmap> bitmapArrayList){
            System.out.println("################");

        }
    }

    public boolean onTouchEvent(MotionEvent event){
        //X軸の取得
        float pointX = event.getX();
        //Y軸の取得
        float pointY = event.getY();

        //取得した内容をログに表示
        Log.d("TouchEvent", "X:" + pointX + ",Y:" + pointY);
        return true;
    }


    /*
    NextPage
    @param myButton nextPage
    */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickNextPage(View myButton){
        if(i != pageNum){
            File path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            System.out.println(path);
            ParcelFileDescriptor fd = null;
            PdfRenderer renderer = null;
            PdfRenderer.Page page = null;
            try {
                fd = ParcelFileDescriptor.open(new File(path, "lowcarbon05.pdf"), ParcelFileDescriptor.MODE_READ_ONLY);
                renderer = new PdfRenderer(fd);
                page = renderer.openPage(i);

                view = (ImageView) findViewById(R.id.pdfImage);
                int viewWidth = view.getWidth();
                int viewHeight = view.getHeight();
                float pdfWidth = page.getWidth();
                float pdfHeight = page.getHeight();
                Log.i("test", "viewWidth=" + viewWidth + ", viewHeight=" + viewHeight
                        + ", pdfWidth=" + pdfWidth + ", pdfHeight=" + pdfHeight);
                Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                view.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fd != null) {
                        fd.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (page != null) {
                    page.close();
                }
                if (renderer != null) {
                    renderer.close();
                }
            }
            i++;
            pageText = i + "ページ / " + pageNum +"ページ";
            pdfPage.setText(pageText);
            //pdfPage.setText(i + "ページ");
        }
    }

    /*
    BackPage
    @param myButton backPage
    */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickBackPage(View myButton){
        if(i != 1){
            //String pathname = "http://10.20.170.52/sample/pdf/sampleProject1/lowcarbon05.pdf";
            //File path = new File()
            File path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            //System.out.println(path);
            ParcelFileDescriptor fd = null;
            PdfRenderer renderer = null;
            PdfRenderer.Page page = null;
            try {
                fd = ParcelFileDescriptor.open(new File(path,"lowcarbon05.pdf"), ParcelFileDescriptor.MODE_READ_ONLY);
                //fd = ParcelFileDescriptor.open(new File(pathname), ParcelFileDescriptor.MODE_READ_ONLY);
                renderer = new PdfRenderer(fd);
                pageNum = renderer.getPageCount();
                page = renderer.openPage(i-2);

                view = (ImageView) findViewById(R.id.pdfImage);
                int viewWidth = view.getWidth();
                int viewHeight = view.getHeight();
                float pdfWidth = page.getWidth();
                float pdfHeight = page.getHeight();
                Log.i("test", "viewWidth=" + viewWidth + ", viewHeight=" + viewHeight
                        + ", pdfWidth=" + pdfWidth + ", pdfHeight=" + pdfHeight);
                Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                view.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fd != null) {
                        fd.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (page != null) {
                    page.close();
                }
                if (renderer != null) {
                    renderer.close();
                }
            }
            i--;
            pageText = i + "ページ / " + pageNum +"ページ";
            pdfPage.setText(pageText);
            //pdfPage.setText(i + "ページ /" + pageNum +" ページ");
        }
    }

    public void cameraClick(View view){
        Uri _imageUri;
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //WRITE_EXTERNAL_STORAGEの許可を求めるダイアログを表示。その際、リクエストコードを2000に設定。
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 2000);
            return;
        }

        //日時データを「yyyyMMddHHmmss」の形式に整形するフォーマッタを生成。
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //現在の日時を取得。
        Date now = new Date(System.currentTimeMillis());
        //取得した日時データを「yyyyMMddHHmmss」形式に整形した文字列を生成。
        String nowStr = dateFormat.format(now);
        //ストレージに格納する画像のファイル名を生成。ファイル名の一意を確保するためにタイムスタンプの値を利用。
        String fileName = "UseCameraActivityPhoto_" + nowStr +".jpg";

        //ContentValuesオブジェクトを生成。
        ContentValues values = new ContentValues();
        //画像ファイル名を設定。
        values.put(MediaStore.Images.Media.TITLE, fileName);
        //画像ファイルの種類を設定。
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        //ContentResolverオブジェクトを生成。
        ContentResolver resolver = getContentResolver();
        //ContentResolverを使ってURIオブジェクトを生成。
        _imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Intentオブジェクトを生成。
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Extra情報として_imageUriを設定。
        intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri);
        //アクティビティを起動。
        startActivityForResult(intent, 200);
    }

    public void createReportClick(View view){
        //Intent intent = new Intent(this, createReport.class);
        //startActivity(intent);
        //dialogFragment dialog = new dialogFragment();
        //dialog.show(getSupportFragmentManager(), "dialogFragment");
        Intent intent = new Intent(this, selectReportType.class);
        startActivity(intent);
    }

    public void reportViewClick(View view){
        Intent intent = new Intent(this, showReport.class);
        startActivity(intent);
    }

    public void imageUploadClick(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "SelectMulti"),CODE_MULTIPLE_IMG_GALLERY);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == RESULT_OK){
            System.out.println("pic");


        }

        if (requestCode == CODE_MULTIPLE_IMG_GALLERY && resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();
            System.out.println(data);

            if(clipData != null){
                //img1.setImageURI(clipData.getItemAt(0).getUri());
                //img2.setImageURI(clipData.getItemAt(1).getUri());

                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    System.out.println(item);
                    Uri uri = item.getUri();
                    getPicPath(uri);
                    System.out.println(uri);
                    Log.e("MAS IMAGES", uri.toString());
                    System.out.println("入ってる");
                }
                System.out.println("なにも入ってない");
            }
        }
    }

    //Uri→path
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getPicPath(Uri uri){
        String id = DocumentsContract.getDocumentId(uri);
        String selection = "_id=?";
        String[] selectionArgs = new String[]{id.split(":")[1]};
        File file = null;
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.MediaColumns.DATA},selection,selectionArgs,null);
        if (cursor != null && cursor.moveToFirst()){
            file = new File(cursor.getString(0));
        }
        final File useFile = file;
        cursor.close();

        if(file != null) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    String content_type = getMimeType(useFile.getPath());
                    String file_path = useFile.getAbsolutePath();
                    //ここでファイルパスから取得、正規表現でファイル名を取って配列へ
                    System.out.println(useFile.getAbsolutePath());
                    //return file.getAbsolutePath();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody file_body = RequestBody.create(MediaType.parse(content_type), useFile);

                    RequestBody request_body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type", content_type)
                            .addFormDataPart("uploaded_file", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://10.20.170.52/sample/save_file.php")
                            .post(request_body)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();

                        if (!response.isSuccessful()) {
                            throw new IOException("Error : " + response);
                        }

                        //progress.dismiss();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }

        return null;
    }
    private String getMimeType(String path){
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    public void getPdf(View view){
        String title = MyPage.paramFileName;
        String dir = "http://192.168.3.4/sample/pdf/";
        //String url = dir + title;
        String url = dir + "lowcarbon05.pdf";

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //String tempTitle = title.replace("","_");
        request.setTitle(title);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title + ".pdf");
        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        request.setMimeType("application/pdf");
        request.allowScanningByMediaScanner();
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        downloadManager.enqueue(request);
    }



}