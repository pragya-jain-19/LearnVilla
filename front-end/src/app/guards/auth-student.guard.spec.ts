import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { authStudentGuard } from './auth-student.guard';

describe('authStudentGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => authStudentGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
