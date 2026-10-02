import { TestBed } from '@angular/core/testing';
import { Relacionservice } from './relacionservice';

describe('Relacionservice', () => {
  let service: Relacionservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Relacionservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
