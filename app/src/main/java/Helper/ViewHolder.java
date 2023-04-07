package Helper;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

//Map View from hero_item_view.xml to this ViewHolder Object
public class ViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView description;
    ImageView image;
    Button btn;

    public ViewHolder (View heroItemView) {
        super(heroItemView);
        name = heroItemView.findViewById(R.id.name);
        //description = heroItemView.findViewById(R.id.description);
        image = heroItemView.findViewById(R.id.detail_image);
        btn = heroItemView.findViewById(R.id.more_info);
    }
}
