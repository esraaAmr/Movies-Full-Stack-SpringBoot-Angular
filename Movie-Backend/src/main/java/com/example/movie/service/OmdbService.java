package com.example.movie.service;

import com.example.movie.error.exception.OmdbMovieNotFoundException;
import com.example.movie.error.exception.OmdbServiceException;
import com.example.movie.mapper.OmdbMapper;
import com.example.movie.model.dto.OmdbResponse;
import com.example.movie.model.dto.OmdbSearchResponse;
import com.example.movie.model.dto.OmdbSearchResult;
import com.example.movie.model.entity.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
public class OmdbService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String apiUrl;
    private final OmdbMapper omdbMapper;

    public OmdbService(@Value("${omdb.api.key}") String apiKey,
                       @Value("${omdb.api.url}") String apiUrl,
                       OmdbMapper omdbMapper) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl.endsWith("/") ? apiUrl : apiUrl + "/";
        this.omdbMapper = omdbMapper;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Search OMDb by imdb id and return full details mapped to OmdbResponse.
     */
    public OmdbResponse searchMovieByImdbId(String imdbId) {
        try {
            String url = apiUrl + "?apikey=" + apiKey + "&i=" + URLEncoder.encode(imdbId, StandardCharsets.UTF_8) + "&plot=full";
            OmdbResponse response = restTemplate.getForObject(url, OmdbResponse.class);

            if (response == null) {
                throw new OmdbServiceException("OMDb service returned null response for imdbId: " + imdbId);
            }

            if (!"True".equalsIgnoreCase(response.getResponse())) {
                String errorMessage = response.getError() != null ?
                        response.getError() : "Unknown OMDb error";
                throw new OmdbMovieNotFoundException("OMDb error for imdbId " + imdbId + ": " + errorMessage);
            }

            return response;

        } catch (RestClientException e) {
            throw new OmdbServiceException("Failed to connect to OMDb service: " + e.getMessage());
        }
    }

    /**
     * Search OMDb by keyword (s=) and return list of search results.
     */
    public List<OmdbSearchResult> searchMoviesListFromOmdb(String keyword) {
        try {
            String url = apiUrl + "?apikey=" + apiKey + "&s=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            OmdbSearchResponse response = restTemplate.getForObject(url, OmdbSearchResponse.class);

            if (response == null) {
                throw new OmdbServiceException("OMDb service returned null response for keyword: " + keyword);
            }

            if (!"True".equalsIgnoreCase(response.getResponse())) {
                String errorMessage = response.getError() != null ?
                        response.getError() : "No movies found for keyword: " + keyword;
                throw new OmdbMovieNotFoundException("OMDb search error: " + errorMessage);
            }

            return response.getSearch() != null ? response.getSearch() : Collections.emptyList();

        } catch (RestClientException e) {
            throw new OmdbServiceException("Failed to connect to OMDb service: " + e.getMessage());
        }
    }

    /**
     * Convert a detailed OmdbResponse to your Movie entity using mapper.
     */
    public Movie convertOmdbResponseToMovie(OmdbResponse omdbResponse) {
        try {
            return omdbMapper.toEntity(omdbResponse);
        } catch (Exception e) {
            throw new OmdbServiceException("Failed to convert OMDb response to Movie: " + e.getMessage());
        }
    }
}