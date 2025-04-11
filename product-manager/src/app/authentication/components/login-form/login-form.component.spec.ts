import { render, screen, fireEvent } from '@testing-library/angular';
import { LoginFormComponent } from './login-form.component';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('LoginFormComponent', () => {
  const mockAuthService = {
    login: jest.fn(),
    storeToken: jest.fn(),
  };

  const mockRouter = {
    navigate: jest.fn(),
  };

  beforeEach(() => jest.clearAllMocks());

  it('should render form with username and password inputs', async () => {
    await render(LoginFormComponent, {
      componentProviders: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
      ],
    });

    expect(screen.getByLabelText(/username/i)).toBeTruthy();
    expect(screen.getByLabelText(/password/i)).toBeTruthy();
    expect(screen.getByRole('button', { name: /login/i })).toBeTruthy();
  });

  it('should show error when form is invalid', async () => {
    await render(LoginFormComponent, {
      componentProviders: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
      ],
    });

    fireEvent.click(screen.getByRole('button', { name: /login/i }));
    expect(mockAuthService.login).not.toHaveBeenCalled();
  });

  it('should call login and navigate on success', async () => {
    mockAuthService.login.mockReturnValue(of({ token: 'abc123' }));

    await render(LoginFormComponent, {
      componentProviders: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
      ],
    });

    fireEvent.input(screen.getByLabelText(/username/i), {
      target: { value: 'user1' },
    });
    fireEvent.input(screen.getByLabelText(/password/i), {
      target: { value: 'pass1' },
    });

    fireEvent.click(screen.getByRole('button', { name: /login/i }));

    expect(mockAuthService.login).toHaveBeenCalledWith('user1', 'pass1');
    expect(mockAuthService.storeToken).toHaveBeenCalledWith('abc123');
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/products']);
  });

  it('should show error on login failure', async () => {
    mockAuthService.login.mockReturnValue(throwError(() => new Error()));

    const { fixture } = await render(LoginFormComponent, {
      componentProviders: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
      ],
    });

    fireEvent.input(screen.getByLabelText(/username/i), {
      target: { value: 'bad' },
    });
    fireEvent.input(screen.getByLabelText(/password/i), {
      target: { value: 'wrong' },
    });

    fireEvent.click(screen.getByRole('button', { name: /login/i }));
    fixture.detectChanges();

    expect(screen.getByText(/invalid username or password/i)).toBeTruthy();
  });
});
