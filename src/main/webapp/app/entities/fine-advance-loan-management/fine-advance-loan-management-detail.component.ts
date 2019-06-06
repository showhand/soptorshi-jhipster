import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';

@Component({
    selector: 'jhi-fine-advance-loan-management-detail',
    templateUrl: './fine-advance-loan-management-detail.component.html'
})
export class FineAdvanceLoanManagementDetailComponent implements OnInit {
    fineAdvanceLoanManagement: IFineAdvanceLoanManagement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fineAdvanceLoanManagement }) => {
            this.fineAdvanceLoanManagement = fineAdvanceLoanManagement;
        });
    }

    previousState() {
        window.history.back();
    }
}
