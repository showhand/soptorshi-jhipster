import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';

import { Title } from '@angular/platform-browser';
import { AccountService } from 'app/core';
import { DeviceDetectorService } from 'ngx-device-detector';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
    configuration: any;
    employeeManagement: any;
    holidayManagement: any;
    leaveManagement: any;
    isDesktop: boolean;

    constructor(
        private titleService: Title,
        private router: Router,
        private accountService: AccountService,
        private deviceDetectorService: DeviceDetectorService
    ) {}

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'soptorshiApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        this.isDesktop = this.deviceDetectorService.isDesktop();

        this.router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.titleService.setTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
            if (event instanceof NavigationError && event.error.status === 404) {
                this.router.navigate(['/404']);
            }
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }
}
