import { Course } from "./course";
import { Student } from "./student";

export interface Payment {
    paymentId?: number,
    student?: Student,
    studentId?: number,
    courses?: Course[],
    courseIds?: string[],
    amount?: number,
    status?: boolean,
    paymentTime?: Date,
    statusUpdatedTime?: Date,
}
