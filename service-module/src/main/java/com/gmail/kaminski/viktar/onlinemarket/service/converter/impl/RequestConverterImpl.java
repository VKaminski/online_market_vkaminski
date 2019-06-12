package com.gmail.kaminski.viktar.onlinemarket.service.converter.impl;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.request.ArticlesRequest;
import com.gmail.kaminski.viktar.onlinemarket.service.converter.RequestConverter;
import com.gmail.kaminski.viktar.onlinemarket.service.model.util.ArticlesRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class RequestConverterImpl implements RequestConverter {
    @Override
    public ArticlesRequest toArticlesRequest(ArticlesRequestDTO requestDTO) {
        ArticlesRequest articlesRequest = new ArticlesRequest();
        articlesRequest.setTitle(requestDTO.getTitle());
        articlesRequest.setDateStart(requestDTO.getDateStart());
        articlesRequest.setDateStop(requestDTO.getDateStop());
        return articlesRequest;
    }
}
