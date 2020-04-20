import { Component, OnInit } from '@angular/core';
import { ActivatedRouteSnapshot, NavigationEnd, NavigationError, Router } from '@angular/router';

import { Title } from '@angular/platform-browser';
import { AccountService } from 'app/core';
import { DeviceDetectorService } from 'ngx-device-detector';
/*import { SidebarService } from 'app/layouts/sidebar/sidebar.service';*/
import { Breadcrumb, BreadcrumbService } from 'angular-crumbs';
import { MenuItem } from 'primeng/api';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
    configuration: any;
    employeeManagement: any;
    holidayManagement: any;
    payrollManagement: any;
    procurementManagement: any;
    leaveManagement: any;
    attendanceManagement: any;
    inventoryManagement: any;
    isDesktop: boolean;
    breadcrumbs: MenuItem[];

    constructor(
        public titleService: Title,
        public router: Router,
        public accountService: AccountService,
        public deviceDetectorService: DeviceDetectorService,
        /*public sidebarService: SidebarService,*/
        public breadCrumService: BreadcrumbService
    ) {}

    public getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
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

        this.breadCrumService.breadcrumbChanged.subscribe(crumbs => {
            this.breadcrumbs = crumbs.map(c => this.toPrimeNgMenuItem(c));
        });
    }

    public toPrimeNgMenuItem(crumb: Breadcrumb) {
        return <MenuItem>{ label: crumb.displayName, url: `#${crumb.url}` };
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }
}
