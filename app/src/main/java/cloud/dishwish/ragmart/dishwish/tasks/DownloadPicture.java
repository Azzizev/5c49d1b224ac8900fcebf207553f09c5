package cloud.dishwish.ragmart.dishwish.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cloud.dishwish.ragmart.dishwish.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class DownloadPicture extends AsyncTask<String, Void, Bitmap>{

    CircleImageView bmImage;

    public DownloadPicture(CircleImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {

        try {
            if(urls[0] != "null") {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                //Drawable d = new BitmapDrawable(getResources(),myBitmap);
                //bmImage.setBackground(d);

                return myBitmap;
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    protected void onPostExecute(Bitmap result) {
        if(result != null)
            bmImage.setImageBitmap(result);
        else
            bmImage.setImageResource(R.drawable.icon_eye);
    }
}
