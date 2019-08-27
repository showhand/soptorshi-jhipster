import { Route } from '@angular/router';

import { NavbarExtendedComponent } from './navbar-extended.component';

export const navbarRoute: Route = {
    path: '',
    component: NavbarExtendedComponent,
    outlet: 'navbar'
};
