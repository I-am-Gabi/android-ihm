package polynews.polytech.unice.fr.polynews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import polynews.polytech.unice.fr.polynews.R;
import polynews.polytech.unice.fr.polynews.activity.DownloadPictures;
import polynews.polytech.unice.fr.polynews.model.News;

/**
 * @see <a href="http://www.vogella.com/tutorials/AndroidListView/article.html">Using lists in Android (ListView) - Tutorial</a>
 * @see <a href="http://developer.android.com/guide/topics/resources/accessing-resources.html">Accessing Resources in Code</a>
 * @version 28/03/16.
 */
public class NewsCustomAdapter extends ArrayAdapter<News> {
    public NewsCustomAdapter(Context context, List<News> objects) {
        super(context, -1, objects);
    }

    /**
     * The ListView instance calls the getView() method on the adapter for each data element.
     * In this method the adapter creates the row layout and maps the data to the views in the layout.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        if (convertView == null)
            convertView = inflater.inflate(R.layout.fragment_new, null);

        // current new
        News news = getItem(position);

        // image new
        ImageView imageView = (ImageView)convertView.findViewById(R.id.icon);
        // imageView.setImageResource(R.drawable.icon);
        DownloadPictures task = new DownloadPictures(imageView, getContext());
        task.execute(news.getMedia_path());

        // content new
        TextView contentView = (TextView)convertView.findViewById(R.id.contentNew);
        contentView.setText(news.getContent());

        // title new
        TextView titleView = (TextView)convertView.findViewById(R.id.titleNew);
        titleView.setText(news.getTitle());

        return convertView;
    }
}
