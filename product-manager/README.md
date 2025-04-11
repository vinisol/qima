# Angular Product Management App

This is a basic Product Management application built with Angular standalone components and lazy-loaded routes. The app includes authentication and basic CRUD operations for products and categories via external APIs.

It’s designed to be simple, modular, and easy to extend—but keep in mind, it’s still a work in progress and not yet production-ready.

## Getting Started

### Prerequisites

- Node.js (v16+ recommended)
- Angular CLI
- Backend services (authentication and product APIs) running locally on:
  - http://localhost:8081 (for auth)
  - http://localhost:8082 (for products & categories)

### Running locally

- Open a terminal

```
npm install
ng serve
```

- Open in the browser

```
http://localhost:4200
```

## Application Structure

Here’s a quick look at the core structure:

- main.ts: Boots the app with routing and an HTTP interceptor.
- app.routes.ts: Top-level route definitions.
- product/routes.ts: Lazy-loaded routes for product listing, creation, and editing.
- product.service.ts: Service handling product/category API calls.
- product-list.component.ts: Displays paginated and sortable product list.
- product-form.component.ts: Used for creating or editing products.
- auth.service.ts: Handles login/logout logic.
- environment.ts: API configuration for development.

## Important Notes

This project is not ready for production yet. Some critical features are still missing or minimal:

### What’s Missing

- Exception handling (User-friendly error feedback and proper error logs)
- Advanced form validation (Client-side)
- Logging and observability (No logging mechanism like Sentry, LogRocket, or custom logs)
- Role-based access control
- Environment configurations for staging or production
- Secure token storage (authentication details need secure handling)

### Suggestions for Production Readiness

- Add a global error handler and HTTP error interceptor
- Implement reactive feedback (toast/snackbars for success/failure)
- Enhance form validation UX with custom validators
- Secure sensitive environment variables
- Add robust authentication guard and access control
- Add E2E tests
- Implement logging and monitoring
- Optimize performance (lazy-loading strategies, change detection)
