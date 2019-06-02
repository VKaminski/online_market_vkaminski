package com.gmail.kaminski.viktar.onlinemarket.service.converter;

import com.gmail.kaminski.viktar.onlinemarket.repository.model.util.ArticlesRequest;
import com.gmail.kaminski.viktar.onlinemarket.service.model.util.ArticlesRequestDTO;

public interface RequestConverter {
    ArticlesRequest toArticlesRequest(ArticlesRequestDTO requestDTO);
}
