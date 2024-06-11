import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCourseSectionsComponent } from './view-course-sections.component';

describe('ViewCourseSectionsComponent', () => {
  let component: ViewCourseSectionsComponent;
  let fixture: ComponentFixture<ViewCourseSectionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewCourseSectionsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewCourseSectionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
