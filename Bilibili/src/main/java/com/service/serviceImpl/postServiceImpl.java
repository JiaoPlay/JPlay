package com.service.serviceImpl;

import com.dao.*;
import com.pojo.GroupPost;
import com.pojo.Post;
import com.pojo.PostReport;
import com.pojo.UserPost;
import com.service.postService;

import java.util.List;

/**
 * Created by frank_xiang on 2016/7/6.
 */
public class postServiceImpl implements postService {
    private postDAO postDAO;
    private userDAO userDAO;
    private groupPostDAO groupPostDAO;
    private userPostDAO userPostDAO;
    private postReportDAO postReportDAO;
    @Override
    public void createPost(Post post) {
        postDAO.createPost(post);
    }

    @Override
    public void deletePost(int postId) {
        postDAO.deletePost(postId);
    }

    @Override
    public void updatePost(Post post) {
        postDAO.updatePost(post);
    }

    @Override
    public Post findPostById(int postId) {
        return postDAO.findPostById(postId);
    }

    @Override
    public int findMaxPostId() {
        return postDAO.findMaxPostId();
    }

    @Override
    public void postPublish(String username, int groupId, Post post) {
        int userId = userDAO.findUserByUsername(username).getUserId();
        createPost(post);
        GroupPost groupPost = new GroupPost();
        groupPost.setInterestGroupId(groupId);
        groupPost.setPostId(post.getPostId());
        groupPostDAO.createGroupPost(groupPost);
        UserPost userPost = new UserPost();
        userPost.setUserId(userId);
        userPost.setPostId(post.getPostId());
        userPost.setIsThumb(new Byte("0"));
        userPostDAO.createUserPost(userPost);
    }

    @Override
    public void reportPost(int postId, String username, String reason) {
        int userId = userDAO.findUserByUsername(username).getUserId();
        PostReport postReport = new PostReport();
        postReport.setPostId(postId);
        postReport.setUserId(userId);
        postReport.setReason(reason);
        postReportDAO.createPostReport(postReport);
    }

    @Override
    public void postThumbCount(int postId) {
        Post post = postDAO.findPostById(postId);
        post.setThumbCount(post.getThumbCount()+1);
        postDAO.updatePost(post);
    }

    @Override
    public List<Post> showPostsByGroupId(int groupId) {
        return postDAO.findPostsByGroupId(groupId);
    }


    public com.dao.postDAO getPostDAO() {
        return postDAO;
    }

    public void setPostDAO(com.dao.postDAO postDAO) {
        this.postDAO = postDAO;
    }

    public com.dao.userDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(com.dao.userDAO userDAO) {
        this.userDAO = userDAO;
    }

    public groupPostDAO getGroupPostDAO() {
        return groupPostDAO;
    }

    public void setGroupPostDAO(groupPostDAO groupPostDAO) {
        this.groupPostDAO = groupPostDAO;
    }

    public userPostDAO getUserPostDAO() {
        return userPostDAO;
    }

    public void setUserPostDAO(userPostDAO userPostDAO) {
        this.userPostDAO = userPostDAO;
    }

    public void setPostReportDAO(com.dao.postReportDAO postReportDAO) {
        this.postReportDAO = postReportDAO;
    }
}
