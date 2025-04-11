import { authInterceptorFn } from './auth.interceptor';
import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';

jest.mock('@angular/core', () => ({
  ...jest.requireActual('@angular/core'),
  inject: jest.fn(),
}));

describe('authInterceptorFn', () => {
  const mockAuthService = {
    getToken: jest.fn(),
  };

  beforeEach(() => {
    (inject as jest.Mock).mockReturnValue(mockAuthService);
  });

  it('should add Authorization header if token exists', async () => {
    mockAuthService.getToken.mockReturnValue('test-token');
    const req = new HttpRequest('GET', '/api/data');
    const next: HttpHandlerFn = jest.fn().mockImplementation((req) => req);

    const result = await authInterceptorFn(req, next);

    expect(result.headers.get('Authorization')).toBe('Bearer test-token');
  });

  it('should not modify request if token is absent', async () => {
    mockAuthService.getToken.mockReturnValue(null);
    const req = new HttpRequest('GET', '/api/data');
    const next: HttpHandlerFn = jest.fn().mockImplementation((req) => req);

    const result = await authInterceptorFn(req, next);

    expect(result.headers.has('Authorization')).toBe(false);
  });
});
