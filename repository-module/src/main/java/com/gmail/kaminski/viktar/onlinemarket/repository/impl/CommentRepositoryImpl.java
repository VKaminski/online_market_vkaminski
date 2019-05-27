package com.gmail.kaminski.viktar.onlinemarket.repository.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.CommentRepository;
import com.gmail.kaminski.viktar.onlinemarket.repository.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {

}
