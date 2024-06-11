import { HttpInterceptorFn } from '@angular/common/http';

export const tokenInterceptorInterceptor: HttpInterceptorFn = (req, next) => {

  // console.log(req.url)
  // if(req.url.includes('/learnvilla/api/login')){
  //   next(req);
  // }
  
  let token = sessionStorage.getItem("token");
    const tokenHeader = req.clone({
      setHeaders: {
        Authorization: "Bearer " + token
      }
    })
    return next(tokenHeader);

};
