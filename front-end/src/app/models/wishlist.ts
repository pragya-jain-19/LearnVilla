import { Course } from "./course";
import { Student } from "./student";

export interface Wishlist {
    wishlistId?: number,
    student?: Student,
    courses?: Course[],
    price?: number
}
