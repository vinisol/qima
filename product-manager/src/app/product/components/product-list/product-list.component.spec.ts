import { render, screen, fireEvent } from '@testing-library/angular';
import { ProductListComponent } from './product-list.component';
import { ProductService } from '../../services/product.service';
import { AuthService } from '../../../authentication/services/auth.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';

describe('ProductListComponent', () => {
  const mockProductService = {
    getProducts: jest.fn(),
    deleteProduct: jest.fn(),
  };

  const mockAuthService = {
    logout: jest.fn(),
  };

  const mockRouter = {
    navigate: jest.fn(),
  };

  const mockProducts = {
    content: [{ id: 1, name: 'Test Product' }],
    totalPages: 2,
    totalElements: 1,
    size: 10,
    number: 0,
  };

  beforeEach(() => jest.clearAllMocks());

  it('should fetch products on init', async () => {
    mockProductService.getProducts.mockReturnValue(of(mockProducts));

    await render(ProductListComponent, {
      componentProviders: [
        { provide: ProductService, useValue: mockProductService },
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
      ],
    });

    expect(mockProductService.getProducts).toHaveBeenCalled();
    expect(screen.getByText(/test product/i)).toBeTruthy();
  });

  it('should navigate to add product', async () => {
    mockProductService.getProducts.mockReturnValue(of(mockProducts));

    await render(ProductListComponent, {
      componentProviders: [
        { provide: ProductService, useValue: mockProductService },
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
      ],
    });

    fireEvent.click(screen.getByRole('button', { name: /add/i }));
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/products/new']);
  });

  it('should delete a product after confirmation', async () => {
    mockProductService.getProducts.mockReturnValue(of(mockProducts));
    mockProductService.deleteProduct.mockReturnValue(of({}));
    window.confirm = jest.fn().mockReturnValue(true);

    await render(ProductListComponent, {
      componentProviders: [
        { provide: ProductService, useValue: mockProductService },
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
      ],
    });

    const deleteButton = screen.getByTitle('Delete product');
    fireEvent.click(deleteButton);

    expect(mockProductService.deleteProduct).toHaveBeenCalledWith(1);
  });

  it('should logout and redirect', async () => {
    mockProductService.getProducts.mockReturnValue(of(mockProducts));

    await render(ProductListComponent, {
      componentProviders: [
        { provide: ProductService, useValue: mockProductService },
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
      ],
    });

    fireEvent.click(screen.getByRole('button', { name: /logout/i }));
    expect(mockAuthService.logout).toHaveBeenCalled();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/auth/login']);
  });
});
