import { Enrollment } from "./enrollment";
import { Wishlist } from "./wishlist";

export interface Student {
    studentId?: number,
    firstName?: string,
    lastName?: string,
    mobile?: string,
    email?: string,
    password?: string,
    confirmPassword?: string,
    enrollments?: Enrollment[],
    wishlist?: Wishlist,
    isActive?: boolean,
    role?: string
}