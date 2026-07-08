package pl.pwola.recommendation.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::fromProduct)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        return ProductResponse.fromProduct(product);
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product(
                request.getName(),
                request.getDescription(),
                request.getMainCategory(),
                request.getSubCategory(),
                request.getProductType(),
                request.getColor(),
                request.getTargetGender(),
                request.getWaterproof(),
                request.getSeason(),
                request.getPrice(),
                request.getRating(),
                request.getReviewsCount(),
                request.getUrl()
        );

        Product savedProduct = productRepository.save(product);

        return ProductResponse.fromProduct(savedProduct);
    }
}