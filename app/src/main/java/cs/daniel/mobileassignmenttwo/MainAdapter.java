package cs.daniel.mobileassignmenttwo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

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

    // Bind data using class

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Employee model) {
        holder.country.setText(model.getCountry());
        holder.email.setText(model.getEmail());
        holder.name.setText(model.getName());
        holder.position.setText(model.getPosition());

    // Set onClickListener for update button

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200)
                        .create();

                 dialogPlus.show();
            }
        });
    }

    // Define IDs

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView country, email, name, position;
        Button btnUpdate, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            country = (TextView)itemView.findViewById(R.id.tvCountry);
            email = (TextView)itemView.findViewById(R.id.tvEmail);
            name = (TextView)itemView.findViewById(R.id.tvName);
            position = (TextView)itemView.findViewById(R.id.tvPosition);

            btnUpdate = (Button)itemView.findViewById(R.id.btnUpdate);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);
        }
    }
}
