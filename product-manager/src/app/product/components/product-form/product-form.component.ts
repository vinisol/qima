import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';
import { CommonModule } from '@angular/common';
import { Category } from '../../models/category.model';
import { C } from '@angular/cdk/keycodes';

@Component({
  standalone: true,
  selector: 'app-product-form',
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule],
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css'],
})
export class ProductFormComponent implements OnInit {
  form!: FormGroup;
  isEditMode = false;
  productId!: number;
  submitted = false;
  message = '';
  categories: Category[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadCategories();

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.productId = +idParam;
      this.loadProduct(this.productId);
    }
  }

  initForm(): void {
    this.form = this.fb.group({
      id: [{ value: '', disabled: true }],
      name: ['', [Validators.required, Validators.maxLength(60)]],
      description: ['', [Validators.required, Validators.maxLength(200)]],
      price: [
        null,
        [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)],
      ],
      available: [true, Validators.required],
      sku: ['', [Validators.required, Validators.maxLength(60)]],
      createdAt: [{ value: '', disabled: true }],
      updatedAt: [{ value: '', disabled: true }],
      categoryId: [null, Validators.required],
    });
  }

  loadProduct(id: number): void {
    this.productService.getProduct(id).subscribe({
      next: (product: Product) => {
        this.form.patchValue(product);
      },
      error: () => {
        this.message = 'Failed to load product';
      },
    });
  }

  loadCategories(): void {
    this.productService.getCategories().subscribe({
      next: (data: Category[]) => {
        this.categories = data;
      },
      error: () => {
        this.message = 'Failed to load categories';
      },
    });
  }

  onSave(): void {
    this.submitted = true;
    if (this.form.invalid) return;

    const product: Product = {
      ...this.form.getRawValue(),
    };

    if (this.isEditMode) {
      this.productService.updateProduct(this.productId, product).subscribe({
        next: () => {
          this.message = 'Product updated successfully!';
        },
        error: () => {
          this.message = 'Failed to update product';
        },
      });
    } else {
      this.productService.createProduct(product).subscribe({
        next: () => {
          this.message = 'Product created successfully!';
          this.form.reset();
          this.submitted = false;
        },
        error: () => {
          this.message = 'Failed to create product';
        },
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/products']);
  }
}
