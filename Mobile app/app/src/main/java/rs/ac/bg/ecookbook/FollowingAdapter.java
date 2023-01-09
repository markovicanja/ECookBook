package rs.ac.bg.ecookbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rs.ac.bg.ecookbook.databinding.ViewHolderFollowingBinding;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder> {

    private final List<String> users;

    public FollowingAdapter(List<String> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderFollowingBinding viewHolderFollowingBinding = ViewHolderFollowingBinding.inflate(
                layoutInflater,
                parent,
                false);
        return new FollowingViewHolder(viewHolderFollowingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingViewHolder holder, int position) {
        String following = users.get(position);
        ViewHolderFollowingBinding binding = holder.binding;

        binding.username.setText(following);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class FollowingViewHolder extends RecyclerView.ViewHolder implements ServiceSetter {

        public ViewHolderFollowingBinding binding;
        private Context context;

        public FollowingViewHolder(@NonNull ViewHolderFollowingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.username.setOnClickListener(v -> {
                context = v.getContext();
                Service.getInstance().findUser(this, binding.username.getText().toString());
            });
        }

        @Override
        public void userFound(){
            Intent intent = new Intent(context, UserActivity.class);
            context.startActivity(intent);
        }

        @Override
        public Context getContext(){
            return context;
        }
    }

}
