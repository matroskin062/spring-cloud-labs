package com.restservice.restservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.restservice.restservice.Model.Comment;
import com.restservice.restservice.Model.Post;
import com.restservice.restservice.Repository.CommentRepository;
import com.restservice.restservice.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;


    
    public Iterable<Comment> getAll() {
        return commentRepository.findByPresenceFlagTrue();
    }

    public Comment getObjectById(int id) throws Exception {
        Optional<Comment> comment = commentRepository.findByIdAndPresenceFlagTrue(id);
        if (!comment.isPresent())
            throw new Exception("No item with this id");
        if(!comment.get().isPresenceFlag())
            throw new Exception("No item with this id");

        return comment.get();
    }

    public Comment saveObject(Comment comment) throws Exception {
        if(comment.getUser_name() == null) throw new Exception("You should to input username");

        try {
            Post post = postRepository.findById(comment.getPost().getId()).get();

            Comment newComment = new Comment(post, comment.getUser_name(), comment.getRating());

            return commentRepository.save(newComment);
        }
        catch (Exception e) {
            throw new Exception("Item was not created");
        }
    }

    public Boolean deleteObject(int id) throws Exception {
        try{
            Comment newObject = getObjectById(id);

            newObject.setPresenceFlag(false);

            commentRepository.save(newObject);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    
    public Comment updateObject(Comment comment, int id) throws Exception {
        if(comment.getUser_name() == null) throw new Exception("You should to input username");
        try {
            Comment updatedComment = getObjectById(id);

            Post brand = postRepository.findById(comment.getPost().getId()).get();

            updatedComment.setPost(brand);
            updatedComment.setUser_name(comment.getUser_name());
            updatedComment.setRating(comment.getRating());

            commentRepository.save(updatedComment);

            return updatedComment;
        }
        catch (Exception e){
            throw new Exception("Item was not updated");
        }
    }
}
