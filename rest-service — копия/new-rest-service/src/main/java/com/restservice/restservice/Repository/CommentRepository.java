package com.restservice.restservice.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.restservice.restservice.Model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>, Repo {
}
