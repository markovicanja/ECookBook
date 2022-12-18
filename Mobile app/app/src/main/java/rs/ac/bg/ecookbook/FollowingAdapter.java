package rs.ac.bg.ecookbook;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rs.ac.bg.ecookbook.databinding.ViewHolderFollowingBinding;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder> {

    private final List<User> users;

    public FollowingAdapter(List<User> users) {
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
        User following = users.get(position);
        ViewHolderFollowingBinding binding = holder.binding;

        binding.username.setText(following.getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class FollowingViewHolder extends RecyclerView.ViewHolder {

        public ViewHolderFollowingBinding binding;

        public FollowingViewHolder(@NonNull ViewHolderFollowingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.username.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), UserActivity.class);
                v.getContext().startActivity(intent);
            });
        }
    }

}
