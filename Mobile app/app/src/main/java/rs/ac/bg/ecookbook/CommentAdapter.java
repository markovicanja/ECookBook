package rs.ac.bg.ecookbook;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rs.ac.bg.ecookbook.databinding.ViewHolderCommentBinding;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderCommentBinding viewHolderCommentBinding = ViewHolderCommentBinding.inflate(
                layoutInflater,
                parent,
                false);
        return new CommentViewHolder(viewHolderCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        ViewHolderCommentBinding binding = holder.binding;

        binding.commentAuthor.setText(comment.getAuthor());
        binding.commentTime.setText(comment.getDate() + " " + comment.getTime());
        binding.comment.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        public ViewHolderCommentBinding binding;

        public CommentViewHolder(@NonNull ViewHolderCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.userImage.setOnClickListener(v -> {
                // TODO - proslediti korisnika
                Intent intent = new Intent(v.getContext(), UserActivity.class);
                v.getContext().startActivity(intent);
            });

            binding.commentAuthor.setOnClickListener(v -> {
                // TODO - proslediti korisnika
                Intent intent = new Intent(v.getContext(), UserActivity.class);
                v.getContext().startActivity(intent);
            });
        }
    }

}
