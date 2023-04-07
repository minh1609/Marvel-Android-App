package Helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Detail;
import com.example.finalproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Hero;


//Purpose of this class: How to parse data from ArrayList<Hero> to view
public class HeroAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<Hero> listOfHero;

    public HeroAdapter(Context context, List<Hero> listOfHero) {
        this.context = context;
        this.listOfHero = listOfHero;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.hero_item_view, parent, false));
    }


    //Parse Hero object to ViewHolder object
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Hero hero = listOfHero.get(position);

        String imageURL = hero.thumbnail.path + "." + hero.thumbnail.extension;
        imageURL = imageURL.replace("http", "https");

        viewHolder.name.setText(hero.name );
        Picasso.get().load(imageURL).into(viewHolder.image);

        //Add action listener for Button to redirect to character detail page
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), Detail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("characterId", hero.id);
                view.getContext().startActivity(intent);
            }

            });
    }

    @Override
    public int getItemCount() {
        return listOfHero.size();
    }
}
