package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.Feedback;
import com.gmail.kaminski.viktar.onlinemarket.service.model.FeedbackDTO;

public interface FeedbackConverter {
    Feedback toFeedback(FeedbackDTO feedbackDTO);
    FeedbackDTO toFeedbackDTO(Feedback feedback);
}
