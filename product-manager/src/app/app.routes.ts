import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch: 'full',
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./authentication/routes').then((m) => m.AUTH_ROUTES),
  },
  {
    path: 'products',
    loadChildren: () =>
      import('./product/routes').then((m) => m.PRODUCT_ROUTES),
  },
];
