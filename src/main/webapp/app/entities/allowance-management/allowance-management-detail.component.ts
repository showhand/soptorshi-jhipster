import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllowanceManagement } from 'app/shared/model/allowance-management.model';

@Component({
    selector: 'jhi-allowance-management-detail',
    templateUrl: './allowance-management-detail.component.html'
})
export class AllowanceManagementDetailComponent implements OnInit {
    allowanceManagement: IAllowanceManagement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ allowanceManagement }) => {
            this.allowanceManagement = allowanceManagement;
        });
    }

    previousState() {
        window.history.back();
    }
}
