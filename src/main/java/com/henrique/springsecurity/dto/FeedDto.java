package com.henrique.springsecurity.dto;

import java.util.List;

public record FeedDto(List<FeedItemDto> feedItems, int pageNumber, int pageSize, int totalPage, long totalElements) {
}
