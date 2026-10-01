import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Relacioncomponent } from './relacioncomponent';

describe('Relacioncomponent', () => {
  let component: Relacioncomponent;
  let fixture: ComponentFixture<Relacioncomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Relacioncomponent],
    }).compileComponents();

    fixture = TestBed.createComponent(Relacioncomponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
