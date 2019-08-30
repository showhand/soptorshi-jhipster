import { Route } from '@angular/router';
import { HomeComponent } from 'app/home';
import { JhiLoginExtendedComponent } from 'app/shared/login/login-extended.component';

export const LOGIN_ROUTE: Route = {
    path: 'login',
    component: JhiLoginExtendedComponent,
    data: {
        authorities: [],
        pageTitle: 'Login to Soptorshi'
    }
};
