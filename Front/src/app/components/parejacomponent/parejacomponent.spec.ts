import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Parejacomponent } from './parejacomponent';

describe('Parejacomponent', () => {
  let component: Parejacomponent;
  let fixture: ComponentFixture<Parejacomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Parejacomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Parejacomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
