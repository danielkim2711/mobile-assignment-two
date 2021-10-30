package cs.daniel.mobileassignmenttwo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MainAdapter extends FirebaseRecyclerAdapter<Employee,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<Employee> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Employee model) {
        holder.country.setText(model.getCountry());
        holder.email.setText(model.getEmail());
        holder.name.setText(model.getName());
        holder.position.setText(model.getPosition());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView country, email, name, position;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            country = (TextView)itemView.findViewById(R.id.tvCountry);
            email = (TextView)itemView.findViewById(R.id.tvEmail);
            name = (TextView)itemView.findViewById(R.id.tvName);
            position = (TextView)itemView.findViewById(R.id.tvPosition);
        }
    }
}
