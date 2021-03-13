import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieEpisodeDetailComponent } from './movie-episode-detail.component';

describe('MovieEpisodeDetailComponent', () => {
  let component: MovieEpisodeDetailComponent;
  let fixture: ComponentFixture<MovieEpisodeDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MovieEpisodeDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieEpisodeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
