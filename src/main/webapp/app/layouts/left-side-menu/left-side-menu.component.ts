import { Component, Input, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-left-side-menu',
    templateUrl: './left-side-menu.component.html',
    styles: []
})
export class LeftSideMenuComponent implements OnInit {
    configuration: any;
    employeeManagement: any;
    holidayManagement: any;
    payrollManagement: any;
    procurementManagement: any;
    leaveManagement: any;
    attendanceManagement: any;
    production: any;
    inventoryManagement: any;
    accountsConfig: any;
    vouchers: any;
    reports: any;
    commercialManagement: any;
    supplyChainManagement: any;

    @Input() leftMenuHidden: boolean;
    constructor() {
        this.leftMenuHidden = false;
    }

    ngOnInit() {}
}
