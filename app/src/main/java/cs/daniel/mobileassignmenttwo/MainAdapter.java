package cs.daniel.mobileassignmenttwo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

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
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int bindPosition, @NonNull Employee model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.position.setText(model.getPosition());
        holder.country.setText(model.getCountry());

        // Pop up update panel once update button is clicked.

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200)
                        .create();

                // Grab data for edit text

                View dialogPlusHolderView = dialogPlus.getHolderView();

                EditText name = dialogPlusHolderView.findViewById(R.id.etName);
                EditText email = dialogPlusHolderView.findViewById(R.id.etEmail);
                EditText position = dialogPlusHolderView.findViewById(R.id.etPosition);
                EditText country = dialogPlusHolderView.findViewById(R.id.etCountry);

                Button btnUpdate = dialogPlusHolderView.findViewById(R.id.btnUpdate);

                name.setText(model.getName());
                email.setText(model.getEmail());
                position.setText(model.getPosition());
                country.setText(model.getCountry());

                dialogPlus.show();

                // Set onClickListener for update button

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("email", email.getText().toString());
                        map.put("position", position.getText().toString());
                        map.put("country", country.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("employees")
                                .child(getRef(bindPosition).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(),
                                                "Updated successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(),
                                                "Failed to update. Please try again.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        // Set onClickListener for delete button

        // Set alert dialog for user

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Would you really like to delete?");
                builder.setMessage("This can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("employees")
                                .child(getRef(bindPosition).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(),
                                "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
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
