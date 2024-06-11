import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoursesBySearchComponent } from './courses-by-search.component';

describe('CoursesBySearchComponent', () => {
  let component: CoursesBySearchComponent;
  let fixture: ComponentFixture<CoursesBySearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CoursesBySearchComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CoursesBySearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
