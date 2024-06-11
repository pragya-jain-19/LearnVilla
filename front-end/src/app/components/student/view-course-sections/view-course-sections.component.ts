import { Component } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { Course } from '../../../models/course';
import { CourseService } from '../../../services/course.service';
import { SectionService } from '../../../services/section.service';
import { StudentNavComponent } from '../student-nav/student-nav.component';

@Component({
  selector: 'app-view-course-sections',
  standalone: true,
  imports: [StudentNavComponent, RouterLink, RouterLinkActive],
  templateUrl: './view-course-sections.component.html',
  styleUrl: './view-course-sections.component.css'
})
export class ViewCourseSectionsComponent {
  courseId : any;
  course : Course = {} as Course;
  sections : any[] = [];
  errors : string[] = [];
  view : boolean =false;
  constructor(private route:ActivatedRoute, private courseService : CourseService, private sectionService: SectionService) {}

  ngOnInit() {
    this.courseId = this.route.snapshot.paramMap.get("courseId");
    this.viewCourseById();
  }

  viewCourseById() {
    this.courseService.viewCourseById(this.courseId).subscribe({
      next : (data :any) =>{
        this.course = data;
      }
    });
  }

  viewAllSections() {
    this.sectionService.viewAllSectionsByCourseId(this.courseId).subscribe({
      next : (data:any) => {
        this.sections = data;
        this.view = true;
      },
      error : (err) => {
        Swal.fire({
          icon: "error",
          title: "Something went wrong!",
          text: err.error.message
        });
      }
    })
  }

}
