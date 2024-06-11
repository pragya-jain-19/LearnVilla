import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokenInterceptorInterceptor } from './interceptors/token-interceptor.interceptor';
// import { tokenInterceptorInterceptor } from './interceptors/token-interceptor.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(),
    // provideHttpClient(),
    // This is for Http interceptors and HttpClient
    provideHttpClient(withInterceptors([tokenInterceptorInterceptor]))
  ]
};
