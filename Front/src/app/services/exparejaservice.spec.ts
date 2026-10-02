import { TestBed } from '@angular/core/testing';
import { Exparejaservice } from './exparejaservice';

describe('Exparejaservice', () => {
  let service: Exparejaservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Exparejaservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
