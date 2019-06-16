import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFineManagement } from 'app/shared/model/fine-management.model';

@Component({
    selector: 'jhi-fine-management-detail',
    templateUrl: './fine-management-detail.component.html'
})
export class FineManagementDetailComponent implements OnInit {
    fineManagement: IFineManagement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fineManagement }) => {
            this.fineManagement = fineManagement;
        });
    }

    previousState() {
        window.history.back();
    }
}
