import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { environment } from '../../../environments/environment';
import { AuthResponse } from '../models/auth.model';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  const mockToken = 'mock-token';
  const mockResponse: AuthResponse = {
    token: mockToken,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);

    // Clear localStorage before each test
    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should send POST request on login()', () => {
    const username = 'testuser';
    const password = 'testpass';

    service.login(username, password).subscribe((res) => {
      expect(res).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.authApiUrl}/auth/login`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ username, password });
    req.flush(mockResponse);
  });

  it('should store and retrieve token', () => {
    service.storeToken(mockToken);
    expect(service.getToken()).toBe(mockToken);
  });

  it('should return true for isAuthenticated when token exists', () => {
    service.storeToken(mockToken);
    expect(service.isAuthenticated()).toBe(true);
  });

  it('should return false for isAuthenticated when token does not exist', () => {
    expect(service.isAuthenticated()).toBe(false);
  });

  it('should remove token on logout()', () => {
    service.storeToken(mockToken);
    service.logout();
    expect(service.getToken()).toBeNull();
  });
});
