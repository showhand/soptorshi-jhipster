import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { VERSION } from 'app/app.constants';
import { AccountService, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { SidebarService } from 'app/layouts/sidebar/sidebar.service';
import { NavbarComponent } from 'app/layouts';

@Component({
    selector: 'jhi-navbar-extended',
    templateUrl: './navbar-extended.component.html',
    styleUrls: ['navbar.css']
})
export class NavbarExtendedComponent extends NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;

    constructor(
        public loginService: LoginService,
        public accountService: AccountService,
        public loginModalService: LoginModalService,
        public profileService: ProfileService,
        public router: Router,
        public sidebarService: SidebarService
    ) {
        super(loginService, accountService, loginModalService, profileService);
    }

    ngOnInit() {
        this.sidebarService.toggleSidenav = true;
        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
    }

    toggleNavbar() {
        this.sidebarService.toggleSidenav = !this.sidebarService.toggleSidenav;
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['/login']);
    }

    login() {
        this.router.navigate(['/login']);
    }
}
