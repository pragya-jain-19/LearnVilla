import { Course } from "./course";

export interface Category {
    categoryId: string,
    categoryName?: string,
    courses?: Course[]
}

