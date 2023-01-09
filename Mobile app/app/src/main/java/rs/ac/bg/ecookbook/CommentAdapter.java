package rs.ac.bg.ecookbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rs.ac.bg.ecookbook.databinding.ViewHolderCommentBinding;
import rs.ac.bg.ecookbook.models.CommentModel;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<CommentModel> comments;

    public List<CommentModel> getComments(){
        return comments;
    }

    public CommentAdapter(List<CommentModel> comments) {
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
        CommentModel comment = comments.get(position);
        ViewHolderCommentBinding binding = holder.binding;

        binding.commentAuthor.setText(comment.getAuthor());
        binding.commentTime.setText(comment.getDate() + " " + comment.getTime());
        binding.comment.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder implements ServiceSetter {

        public ViewHolderCommentBinding binding;
        private Context context;

        public CommentViewHolder(@NonNull ViewHolderCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.userImage.setOnClickListener(this::startUserActivity);

            binding.commentAuthor.setOnClickListener(this::startUserActivity);
        }

        private void startUserActivity(View v){
            context = v.getContext();
            Service.getInstance().findUser(this, binding.commentAuthor.getText().toString());
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
