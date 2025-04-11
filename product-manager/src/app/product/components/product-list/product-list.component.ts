import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../../authentication/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-product-list',
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  currentPage = 0;
  totalPages = 0;
  pageSize = 10;
  sort = 'id,asc';

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts(): void {
    this.productService
      .getProducts(this.currentPage, this.pageSize, this.sort)
      .subscribe((response) => {
        this.products = response.content;
        this.totalPages = response.totalPages;
      });
  }

  goToAdd(): void {
    this.router.navigate(['/products/new']);
  }

  goToEdit(productId: number): void {
    this.router.navigate([`/products/${productId}/edit`]);
  }

  deleteProduct(productId: number): void {
    if (confirm('Are you sure you want to delete this product?')) {
      this.productService.deleteProduct(productId).subscribe(() => {
        this.fetchProducts();
      });
    }
  }

  sortBy(field: string): void {
    const [currentField, currentDir] = this.sort.split(',');
    const newDir =
      currentField === field && currentDir === 'asc' ? 'desc' : 'asc';
    this.sort = `${field},${newDir}`;
    this.fetchProducts();
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.fetchProducts();
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}
