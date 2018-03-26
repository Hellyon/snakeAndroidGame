package views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import snake.snake.R;
import tools.DatabaseHandler;

/**
 * FireBaseFragment
 *
 * @author Ilyace Benjelloun
 */
public class FireBaseFragment extends Fragment {
    private final DatabaseHandler databaseHandler;

    public FireBaseFragment() {
        databaseHandler = new DatabaseHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.score_tab_fragment, container, false);
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }
}
