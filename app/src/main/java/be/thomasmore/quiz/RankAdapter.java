package be.thomasmore.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RankAdapter extends ArrayAdapter<Result> {
    private final Context context;
    private final List<Result> values;

    public RankAdapter(Context context, List<Result> values) {
        super(context, R.layout.resultlistviewitem, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.resultlistviewitem, parent, false);

        final TextView textViewNaam = (TextView) rowView.findViewById(R.id.name);
        final TextView textViewScore = (TextView) rowView.findViewById(R.id.score);
        final TextView textViewCat = (TextView) rowView.findViewById(R.id.categorie);
        final TextView textViewDif = (TextView) rowView.findViewById(R.id.difficulty);

        textViewNaam.setText(values.get(position).getNaam());
        textViewCat.setText(values.get(position).getCategorie());
        textViewDif.setText(values.get(position).getDifficulty());
        textViewScore.setText(values.get(position).getScore() + "/"+values.get(position).getAantal());

        return rowView;
    }
}
