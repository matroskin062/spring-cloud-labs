package com.restservice.restservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.restservice.restservice.Model.Post;
import com.restservice.restservice.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@Service
public class PostService{
    @Autowired
    private PostRepository postRepository;


    public Iterable<Post> getAll() {
        return postRepository.findByPresenceFlagTrue();
    }

    public Post getObjectById(Integer id) throws Exception{
        Optional<Post> post = postRepository.findByIdAndPresenceFlagTrue(id);
        if (!post.isPresent())
            throw new Exception("No item with this id");

        return post.get();

    }

    public Post saveObject(Post newObject) throws Exception {
        if(newObject.getTitle() == null){
            throw new Exception("Title can't be null");
        }else if(newObject.getAuthor() == null){
            throw new Exception("Author can't be null");
        }else{
            return postRepository.save(newObject);
        }
    }

    public Boolean deleteObject(int id) throws Exception {
        try{
            Post newObject = getObjectById(id);

            newObject.setPresenceFlag(false);

            postRepository.save(newObject);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public Post updateObject(Post newObject, int id) throws Exception {
        if(newObject.getTitle() == null){
            throw new Exception("You should to input title");
        }else if(newObject.getAuthor() == null){
            throw new Exception("You should to input author");
        }else {

            try {
                Post updatedPost = getObjectById(id);

                updatedPost.setTitle(newObject.getTitle());
                updatedPost.setAuthor(newObject.getAuthor());
                updatedPost.setRaiting(newObject.getRaiting());

                postRepository.save(updatedPost);

                return updatedPost;
            } catch (Exception e) {
                throw new Exception("Item was not updated");
            }
        }
    }
}
