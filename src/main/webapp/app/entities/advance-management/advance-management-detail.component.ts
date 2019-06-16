import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdvanceManagement } from 'app/shared/model/advance-management.model';

@Component({
    selector: 'jhi-advance-management-detail',
    templateUrl: './advance-management-detail.component.html'
})
export class AdvanceManagementDetailComponent implements OnInit {
    advanceManagement: IAdvanceManagement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ advanceManagement }) => {
            this.advanceManagement = advanceManagement;
        });
    }

    previousState() {
        window.history.back();
    }
}
