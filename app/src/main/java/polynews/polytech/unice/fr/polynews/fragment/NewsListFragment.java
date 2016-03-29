package polynews.polytech.unice.fr.polynews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import polynews.polytech.unice.fr.polynews.R;
import polynews.polytech.unice.fr.polynews.adapter.NewsCustomAdapter;
import polynews.polytech.unice.fr.polynews.database.NewsDBHelper;
import polynews.polytech.unice.fr.polynews.model.News;

/**
 * @see <a href="http://developer.android.com/reference/android/app/Fragment.html#onCreate(android.os.Bundle)">
 *      Fragment
 *      </a>
 *      <a href="http://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra">
 *      Difference and uses of onCreate(), onCreateView() and onActivityCreated() in fragments
 *      </a>
 * @version 15/03/16.
 */
public class NewsListFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewsListFragment newInstance(int sectionNumber) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NewsDBHelper dbHelper = new NewsDBHelper(getActivity());
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        List<News> newsList = dbHelper.selectRecords();
        NewsCustomAdapter adapter = new NewsCustomAdapter(getActivity(), newsList);

        GridView grid = (GridView) getView().findViewById(R.id.gridView);
        grid.setAdapter(adapter);
    }
}
