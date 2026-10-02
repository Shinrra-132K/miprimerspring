import { TestBed } from '@angular/core/testing';
import { Parejaservice } from './parejaservice';

describe('Parejaservice', () => {
  let service: Parejaservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Parejaservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
