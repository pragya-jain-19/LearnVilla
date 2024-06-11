export const AppConstants = {

    // Authentication URLs
    LOGIN_API_URL: "/login",
    REGISTER_API_URL: "/register",

    // Category URLs
    ADD_CATEGORY_API_URL: "/categories/add-category",
    VIEW_ALL_CATEGORIES_API_URL: "/categories/view-all-categories",
    UPDATE_CATEGORY_API_URL: "/categories/update-category",
    DELETE_CATEGORY_API_URL: "/categories/delete-category/",

    // Course URLs
    ADD_COURSE_API_URL: "/courses/add-course",
    VIEW_ALL_COURSES_API_URL: "/courses/view-all-courses",
    VIEW_COURSE_BY_ID_API_URL: "/courses/view-course/course-id/",
    VIEW_COURSE_BY_NAME_API_URL: "/courses/view-course/course-name/",
    VIEW_COURSES_BY_CATEGORY_API_URL: "/courses/view-all-courses/category-id/",
    UPDATE_COURSE_API_URL: "/courses/update-course",
    DELETE_COURSE_API_URL: "/courses/delete-course/",
    VIEW_COURSES_BY_NAME_LIKE_API_URL: "/courses/view-all-courses/course-name-like/",

    // Section URLs
    ADD_SECTION_API_URL: "/sections/add-section",
    VIEW_ALL_SECTIONS_API_URL: "/sections/view-all-sections",
    VIEW_ALL_SECTIONS_BY_COURSE_ID_API_URL: "/sections/view-all-sections/course-id/",
    VIEW_ALL_SECTIONS_BY_COURSE_NAME_API_URL: "/sections/view-all-sections/course-name/",
    VIEW_SECTION_BY_ID_API_URL: "/sections/view-section/",
    UPDATE_SECTION_API_URL: "/sections/update-section",
    DELETE_SECTION_API_URL: "/sections/delete-section/",

    // Enrollment URLs
    ADD_ENROLLMENT_API_URL: "/enrollments/add-enrollment",
    VIEW_ALL_ENROLLMENTS_API_URL: "/enrollments/view-all-enrollments",
    UPDATE_ENROLLMENT_API_URL: "/enrollments/update-enrollment",
    COMPLETION_UPDATE_API_URL: "/enrollments/update-completion-status",
    VIEW_ALL_ENROLLMENTS_BY_STUDENT_ID_API_URL: "/enrollments/view-all-enrollments/student/",
    VIEW_ALL_ENROLLMENTS_BY_COURSE_ID_API_URL: "/enrollments/view-all-enrollments/course/",
    VIEW_ENROLLMENT_BY_ID_API_URL: "/enrollments/view-enrollment/",
    DELETE_ENROLLMENT_API_URL: "/enrollments/delete-enrollment/",

    // Student URLs
    VIEW_ALL_STUDENTS_API_URL: "/students/view-all-students",
    VIEW_STUDENT_BY_ID_API_URL: "/students/view-student/",
    UPDATE_STUDENT_API_URL: "/students/update-student",
    DELETE_STUDENT_API_URL: "/students/delete-student/",

    //Wishlist URLs
    INSERT_COURSE_INTO_WISHLIST_API_URL: "/wishlist/insert-course/",
    DELETE_COURSE_FROM_WISHLIST_API_URL: "/wishlist/delete-course/",
    VIEW_WISHLIST_BY_STUDENT_ID_API_URL: "/wishlist/view-wishlist/",
    DELETE_WISHLIST_BY_STUDENT_ID_API_URL: "/wishlist/delete-wishlist/",

    // Payment URLs
    ADD_PAYMENT_API_URL : "/payments/add-payment",
    VIEW_ALL_PAYMENTS_API_URL : "/payments/view-all-payments",
    VIEW_PAYMENTS_BY_STUDENT_ID_API_URL : "/payments/view-all-payments/student-id/",
    VIEW_PAYMENT_BY_PAYMENT_ID_API_URL : "/payments/view-payment/",
    UPDATE_PAYMENT_STATUS : "/payments/update-payment-status/"

}