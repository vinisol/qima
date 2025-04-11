import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { ProductService } from './product.service';
import { environment } from '../../../environments/environment';

describe('ProductService', () => {
  let service: ProductService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProductService],
    });
    service = TestBed.inject(ProductService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it('should fetch products with query params', () => {
    const mockResponse = {
      content: [],
      totalPages: 1,
      totalElements: 0,
      size: 10,
      number: 0,
    };
    service.getProducts(1, 5, 'name,desc').subscribe((res) => {
      expect(res).toEqual(mockResponse);
    });

    const req = http.expectOne(
      `${environment.productApiUrl}/products?page=1&size=5&sort=name,desc`
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should create a product', () => {
    const mockProduct = { name: 'A' } as any;
    service.createProduct(mockProduct).subscribe();

    const req = http.expectOne(`${environment.productApiUrl}/products`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockProduct);
  });

  it('should update a product', () => {
    const updated = { name: 'B' } as any;
    service.updateProduct(1, updated).subscribe();

    const req = http.expectOne(`${environment.productApiUrl}/products/1`);
    expect(req.request.method).toBe('PUT');
  });

  it('should delete a product', () => {
    service.deleteProduct(99).subscribe();
    const req = http.expectOne(`${environment.productApiUrl}/products/99`);
    expect(req.request.method).toBe('DELETE');
  });

  it('should fetch categories', () => {
    service.getCategories().subscribe();
    const req = http.expectOne(`${environment.productApiUrl}/categories`);
    expect(req.request.method).toBe('GET');
  });
});
