import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Exparejacomponent } from './exparejacomponent';

describe('Exparejacomponent', () => {
  let component: Exparejacomponent;
  let fixture: ComponentFixture<Exparejacomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Exparejacomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Exparejacomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
