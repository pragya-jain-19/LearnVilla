import { Course } from "./course";

export interface Section {
    sectionId?: number,
    course?: Course,
    courseId?: string,
    sectionNumber?: number,
    sectionName?: string,
    sectionContent?: string,
    videoUrl?: string,
    createdDate?: Date,
    updatedDate?: Date
}
