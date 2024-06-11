import { Course } from "./course";
import { Student } from "./student";

export interface Enrollment {
    enrollmentId?: number,
    student?: Student,
    studentId?: number,
    course?: Course,
    courseId?: string,
    enrolledDate?: Date,
    completedDate?: Date,
    progress?: number,
    isProgressLeft?: boolean
}
