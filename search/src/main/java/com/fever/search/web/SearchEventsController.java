package com.fever.search.web;


import com.fever.search.model.EventSummary;
import com.fever.search.model.SearchError;
import com.fever.search.model.SearchResponse;
import com.fever.search.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.List;
import java.util.Date;



@RestController
public class SearchEventsController {

    private SearchService searchService;

    public SearchEventsController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEvents(@RequestParam(name="starts_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startsAt,
                                          @RequestParam(name="ends_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endsAt) {
        List<EventSummary> eventSummaryList = searchService.searchEvents(startsAt, endsAt);
        SearchResponse response = new SearchResponse(eventSummaryList, null);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleMissingParameter(HttpServletRequest request, Throwable ex) {
        SearchResponse response = new SearchResponse(null, new SearchError("001", ex.getMessage()));
        return ResponseEntity.badRequest().body(response);
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleArgumentTypeMismatch(HttpServletRequest request, Throwable ex) {
        SearchResponse response = new SearchResponse(null, new SearchError("002", "Argument type mismatch"));
        return ResponseEntity.badRequest().body(response);
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleUnknownException(HttpServletRequest request, Throwable ex) {
        SearchResponse response = new SearchResponse(null, new SearchError("003", ex.getMessage()));
        return ResponseEntity.internalServerError().body(response);
    }
}
