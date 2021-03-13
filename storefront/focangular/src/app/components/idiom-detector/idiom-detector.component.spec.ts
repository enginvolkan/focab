import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IdiomDetectorComponent } from './idiom-detector.component';

describe('IdiomDetectorComponent', () => {
  let component: IdiomDetectorComponent;
  let fixture: ComponentFixture<IdiomDetectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IdiomDetectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IdiomDetectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
