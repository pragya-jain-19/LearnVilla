import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AppConstants } from '../constants/app.constants';
import { Section } from '../models/section';

@Injectable({
  providedIn: 'root'
})
export class SectionService {
  constructor(private http: HttpClient) { }

  addSection(section: any) {
    return this.http.post<Section>(environment.rootUrl + AppConstants.ADD_SECTION_API_URL, section);
  }

  updateSection(section: any) {
    return this.http.put<Section>(environment.rootUrl + AppConstants.UPDATE_SECTION_API_URL, section);
  }

  viewAllSections() {
    return this.http.get<Section[]>(environment.rootUrl + AppConstants.VIEW_ALL_SECTIONS_API_URL);
  }

  viewAllSectionsByCourseId(courseId: string) {
    return this.http.get<Section[]>(environment.rootUrl + AppConstants.VIEW_ALL_SECTIONS_BY_COURSE_ID_API_URL + `${courseId}`);
  }

  viewSectionById(sectionId: number) {
    return this.http.get<Section>(environment.rootUrl + AppConstants.VIEW_SECTION_BY_ID_API_URL + `${sectionId}`);
  }

  deleteSection(sectionId: number) {
    return this.http.delete<string>(environment.rootUrl + AppConstants.DELETE_SECTION_API_URL + `${sectionId}`, { responseType: "text" as "json" });
  }
}
