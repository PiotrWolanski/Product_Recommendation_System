package pl.pwola.recommendation.recommendation;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public List<RecommendationResponse> recommendProducts(
            @Valid @RequestBody RecommendationRequest request
    ) {
        return recommendationService.recommendProducts(request);
    }
}