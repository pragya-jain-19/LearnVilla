import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AppConstants } from '../constants/app.constants';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  addCategory(category: any) {
    return this.http.post<Category>(environment.rootUrl + AppConstants.ADD_CATEGORY_API_URL, category);
  }

  viewAllCategories() {
    return this.http.get<Category[]>(environment.rootUrl + AppConstants.VIEW_ALL_CATEGORIES_API_URL);
  }

  updateCategory(category: any) {
    return this.http.put<Category>(environment.rootUrl + AppConstants.UPDATE_CATEGORY_API_URL, category);
  }

  deleteCategory(categoryId: string) {
    return this.http.delete<string>(environment.rootUrl + AppConstants.DELETE_CATEGORY_API_URL + `${categoryId}`, { responseType: "text" as "json" });
  }

}
