import { Category } from "./category";
import { Enrollment } from "./enrollment";
import { Section } from "./section";

export interface Course {
    courseId: string,
    courseName?: string,
    description?: string,
    category?: Category,
    categoryId?: string,
    duration?: number,
    price?: number,
    studentsEnrolled?: number,
    rating?: number,
    courseImage?: string,
    language?: string,
    sections?: Section[],
    createdDate?: Date,
    updatedDate?: Date,
    enrollments?: Enrollment[],
    enrolledDate ?: Date
}
