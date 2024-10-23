package com.fever.search.application.rest;


import com.fever.search.application.response.BaseEventConverter;
import com.fever.search.application.response.EventSummaryDTO;
import com.fever.search.application.response.SearchEventsResponseDTO;
import com.fever.search.domain.BaseEvent;
import com.fever.search.domain.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;
import java.util.List;


@RestController
public class SearchEventsController {

    private EventService eventService;

    private BaseEventConverter eventConverter;

    public SearchEventsController(EventService eventService, BaseEventConverter eventConverter) {
        this.eventService = eventService;
        this.eventConverter = eventConverter;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEvents(@RequestParam(name = "starts_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startsAt, @RequestParam(name = "ends_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endsAt) {
        List<BaseEvent> events = eventService.findEventsByStartAndEndDate(startsAt, endsAt);
        List<EventSummaryDTO> eventSummaryList = events.stream().map(event -> eventConverter.convert(event)).toList();
        SearchEventsResponseDTO response = new SearchEventsResponseDTO(eventSummaryList);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleMissingParameter(HttpServletRequest request, Throwable ex) {
        SearchEventsResponseDTO response = new SearchEventsResponseDTO(new SearchEventsResponseDTO.SearchError("001", ex.getMessage()));
        return ResponseEntity.badRequest().body(response);
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleArgumentTypeMismatch(HttpServletRequest request, Throwable ex) {
        SearchEventsResponseDTO response = new SearchEventsResponseDTO(new SearchEventsResponseDTO.SearchError("002", "Argument type mismatch"));
        return ResponseEntity.badRequest().body(response);
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleUnknownException(HttpServletRequest request, Throwable ex) {
        SearchEventsResponseDTO response = new SearchEventsResponseDTO(new SearchEventsResponseDTO.SearchError("003", ex.getMessage()));
        return ResponseEntity.internalServerError().body(response);
    }
}
