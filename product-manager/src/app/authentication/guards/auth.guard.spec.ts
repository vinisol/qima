import { TestBed } from '@angular/core/testing';
import { AuthGuard } from './auth.guard';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let authServiceMock: { isAuthenticated: jest.Mock };
  let routerMock: { navigate: jest.Mock };

  beforeEach(() => {
    authServiceMock = {
      isAuthenticated: jest.fn(),
    };

    routerMock = {
      navigate: jest.fn(),
    };

    TestBed.configureTestingModule({
      providers: [
        AuthGuard,
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock },
      ],
    });

    guard = TestBed.inject(AuthGuard);
  });

  it('should return true if user is authenticated', () => {
    authServiceMock.isAuthenticated.mockReturnValue(true);
    expect(guard.canActivate()).toBe(true);
  });

  it('should navigate to /login if not authenticated', () => {
    authServiceMock.isAuthenticated.mockReturnValue(false);
    const result = guard.canActivate();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
    expect(result).toBe(false);
  });
});
