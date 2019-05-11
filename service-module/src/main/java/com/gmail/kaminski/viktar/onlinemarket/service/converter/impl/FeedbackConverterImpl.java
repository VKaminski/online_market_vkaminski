package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Feedback;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.FeedbackConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.UserConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.FeedbackDTO;
import org.springframework.stereotype.Component;

@Component
public class FeedbackConverterImpl implements FeedbackConverter {
    private UserConverter userConverter;

    public FeedbackConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Feedback toFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        if (feedbackDTO.getId() != null) {
            feedback.setId(feedbackDTO.getId());
        }
        if (feedbackDTO.getUserDTO() != null) {
            feedback.setUser(userConverter.toUser(feedbackDTO.getUserDTO()));
        }
        if (feedbackDTO.getContent() != null) {
            feedback.setContent(feedbackDTO.getContent());
        }
        if (feedbackDTO.getDate() != null) {
            feedback.setDate(feedbackDTO.getDate());
        }
        if (feedbackDTO.getStatus() != null) {
            feedback.setStatus(feedbackDTO.getStatus());
        }
        return feedback;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public FeedbackDTO toFeedbackDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        if (feedback.getId() != null) {
            feedbackDTO.setId(feedbackDTO.getId());
        }
        if (feedback.getUser() != null) {
            feedbackDTO.setUserDTO(userConverter.toUserDTO(feedback.getUser()));
        }
        if (feedback.getContent() != null) {
            feedbackDTO.setContent(feedback.getContent());
        }
        if (feedback.getDate() != null) {
            feedbackDTO.setDate(feedback.getDate());
        }
        if (feedback.getStatus() != null) {
            feedbackDTO.setStatus(feedback.getStatus());
        }
        return feedbackDTO;
    }
}
