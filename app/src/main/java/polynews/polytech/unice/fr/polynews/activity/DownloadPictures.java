package polynews.polytech.unice.fr.polynews.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @see <a href="http://developer.android.com/reference/android/os/AsyncTask.html">
 *     Reference Android AsyncTask
 *     </a>
 *     <a href="http://stackoverflow.com/questions/3090650/android-loading-an-image-from-the-web-with-asynctask">
 *     Android : Loading an image from the Web with Asynctask
 *     </a>
 *     <a href="http://www.vogella.com/tutorials/AndroidBackgroundProcessing/article.html">
 *     Android background processing with Handlers, AsyncTask and Loaders - Tutorial
 *     </a>
 * @version 29/03/16.
 */
public class DownloadPictures extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    Context context;

    public DownloadPictures(ImageView imageView, Context context) {
        super();
        this.imageView = imageView;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadImage(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        this.imageView.setImageBitmap(bitmap);
    }

    /**
     * Method to do download
     * @param urlFile web url to the file
     * @return a bitmap
     */
    protected Bitmap downloadImage(String urlFile) {
        InputStream input;
        HttpURLConnection connection;
        Bitmap image;
        try {
            URL url = new URL(urlFile);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(DownloadPictures.class.toString(), "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage());
            }

            input = new BufferedInputStream(connection.getInputStream());
            image = BitmapFactory.decodeStream(input);
            if (null != image)
                return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
