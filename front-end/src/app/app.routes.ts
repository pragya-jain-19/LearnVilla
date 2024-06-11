import { Routes } from '@angular/router';
import { AboutUsComponent } from './components/about-us/about-us.component';
import { AddCategoryComponent } from './components/admin/add-category/add-category.component';
import { AddCourseComponent } from './components/admin/add-course/add-course.component';
import { AddSectionComponent } from './components/admin/add-section/add-section.component';
import { CategoriesPageComponent } from './components/admin/categories-page/categories-page.component';
import { CoursesPageComponent } from './components/admin/courses-page/courses-page.component';
import { EnrollmentsPageComponent } from './components/admin/enrollments-page/enrollments-page.component';
import { PaymentsPageComponent } from './components/admin/payments-page/payments-page.component';
import { SectionsPageComponent } from './components/admin/sections-page/sections-page.component';
import { StudentsPageComponent } from './components/admin/students-page/students-page.component';
import { UpdateCategoryComponent } from './components/admin/update-category/update-category.component';
import { UpdateCourseComponent } from './components/admin/update-course/update-course.component';
import { UpdateSectionComponent } from './components/admin/update-section/update-section.component';
import { ViewPaymentComponent } from './components/admin/view-payment/view-payment.component';
import { CoursesByCategoryComponent } from './components/courses/courses-by-category/courses-by-category.component';
import { CourseDetailsComponent } from './components/courses/course-details/course-details.component';
import { ViewCoursesComponent } from './components/courses/view-courses/view-courses.component';
import { AddPaymentComponent } from './components/student/add-payment/add-payment.component';
import { MyEnrollmentsComponent } from './components/student/my-enrollments/my-enrollments.component';
import { MyPaymentsComponent } from './components/student/my-payments/my-payments.component';
import { ViewCourseSectionsComponent } from './components/student/view-course-sections/view-course-sections.component';
import { WishlistsPageComponent } from './components/student/wishlists-page/wishlists-page.component';
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { RegisterComponent } from './pages/register/register.component';
import { StudentDashboardComponent } from './pages/student-dashboard/student-dashboard.component';
import { CoursesBySearchComponent } from './components/courses/courses-by-search/courses-by-search.component';
import { MyProfileComponent } from './components/student/my-profile/my-profile.component';
import { AdminProfileComponent } from './components/admin/admin-profile/admin-profile.component';
import { authAdminGuard } from './guards/auth-admin.guard';
import { authStudentGuard } from './guards/auth-student.guard';

export const routes: Routes = [
    { path: "", component: HomeComponent, title: "LearnVilla" },
    { path: "home", component: HomeComponent, title: "LearnVilla" },
    { path: "about", component: AboutUsComponent, title: "About" },
    { path: "login", component: LoginComponent, title: "Login" },
    { path: "register", component: RegisterComponent, title: "Register" },
    { path: "admin", component: AdminDashboardComponent, title: "Admin Dashboard", canActivate: [authAdminGuard] },
    { path: "student", component: StudentDashboardComponent, title: "Student Dashboard", canActivate: [authStudentGuard] },
    { path: "categories-page", component: CategoriesPageComponent, title: "Categories", canActivate: [authAdminGuard] },
    { path: "categories/add-category", component: AddCategoryComponent, title: "Add Category", canActivate: [authAdminGuard] },
    { path: "categories/update-category/:categoryId", component: UpdateCategoryComponent, title: "Update Category", canActivate: [authAdminGuard] },
    { path: "courses-page", component: CoursesPageComponent, title: "Courses", canActivate: [authAdminGuard] },
    { path: "courses/add-course", component: AddCourseComponent, title: "Add Course", canActivate: [authAdminGuard] },
    { path: "courses/update-course/:courseId", component: UpdateCourseComponent, title: "Update Course", canActivate: [authAdminGuard] },
    { path: "students-page", component: StudentsPageComponent, title: "Students", canActivate: [authAdminGuard] },
    { path: "my-enrollments/:userId", component: MyEnrollmentsComponent, title: "My Enrollments", canActivate: [authStudentGuard] },
    { path: "wishlist/:userId", component: WishlistsPageComponent, title: "My Wishlist", canActivate: [authStudentGuard] },
    { path: "my-profile/:userId", component: MyProfileComponent, title: "My Profile", canActivate: [authStudentGuard] },
    { path: "admin-profile", component: AdminProfileComponent, title: "My Profile", canActivate: [authAdminGuard] },
    { path: "courses", component: ViewCoursesComponent, title: "Courses" },
    { path: "courses/category/:categoryId", component: CoursesByCategoryComponent, title: "Courses" },
    { path: "courses/nameLike/:name", component: CoursesBySearchComponent, title: "Courses" },
    { path: "courses/:courseId", component: CourseDetailsComponent, title: "Courses" },
    { path: "sections-page", component: SectionsPageComponent, title: "Sections", canActivate: [authAdminGuard] },
    { path: "sections/add-section", component: AddSectionComponent, title: "Add Section", canActivate: [authAdminGuard] },
    { path: "sections/update-section/:sectionId", component: UpdateSectionComponent, title: "Update Section", canActivate: [authAdminGuard] },
    { path: "enrollments-page", component: EnrollmentsPageComponent, title: "Enrollments", canActivate: [authAdminGuard] },
    { path: "view-course-sections/:courseId", component: ViewCourseSectionsComponent, title: "Course Sections", canActivate: [authStudentGuard] },
    { path: "payments-page", component: PaymentsPageComponent, title: "Payments", canActivate: [authAdminGuard] },
    { path: "payments/:paymentId", component: ViewPaymentComponent, title: "Payments", canActivate: [authAdminGuard] },
    { path: "my-payments/:studentId", component: MyPaymentsComponent, title: "My payments", canActivate: [authStudentGuard] },
    { path: "add-payment", component: AddPaymentComponent, title: "Add Payment", canActivate: [authStudentGuard] },
    { path: "**", component: NotFoundComponent, title: "Page Not FOund" }

];
